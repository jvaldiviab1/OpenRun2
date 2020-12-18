package com.jvaldiviab.openrun2.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.databinding.FragmentMapBinding;
import com.jvaldiviab.openrun2.util.UtilsValidate;
import com.jvaldiviab.openrun2.viewmodel.MapViewModel;

import java.util.ArrayList;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    private FragmentMapBinding binding;
    private MapViewModel viewModel;

    private GoogleMap googleMap;
    private CameraPosition cameraPosition;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final LatLng mDefaultLocation = new LatLng(-33, 151);
    private static final int DEFAULT_ZOOM = 15;

    private boolean mLocationPermissionGranted = true;

    private Location mLastKnownLocation;
    private Location prevLocation;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Variables for drawing polyline indicating path
    private static final int POLYLINE_STROKE_WIDTH_PX = 5; // Polyline thickness
    private ArrayList<LatLng> points = new ArrayList<>();  // List of all GPS points tracked

    // Handler (For repeating a runnable(s))
    private Handler handler = new Handler();

    // Variables for stopwatch
    private long timeInMilliseconds = 0L;  // Current time elapsed in milliseconds
    private long startTimeMillis = 0L;  // Start time of the activity in milliseconds
    private long endTimeMillis = 0L;  // End time of the activity in milliseconds
    private static final int ONE_SECOND = 1000; // milliseconds
    private static final int FOUR_SECONDS = 4000;  // milliseconds

    // Variables para acumular la distancia recorrida
    private float accumDistMeters = 0;  // Accumulated distance in meters

    PolylineOptions polylineOptions;

    /**
     * Runnable para actualizar y trazar polilínea. Handler ejecuta el ejecutable
     * cada cuatro (4) segundos.
     */
    private Runnable tracker = new Runnable() {
        @Override
        public void run() {
            getDeviceLocation();
            updateRoute(mLastKnownLocation);

            handler.postDelayed(this, FOUR_SECONDS);
        }
    };

    /**
     * Runnable para actualizar y mostrar el tiempo de actividad. Handler ejecuta el ejecutable
     * cada segundo.
     */
    private Runnable stopwatch = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTimeMillis;
            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            int hours = mins / 60;
            secs %= 60;
            mins %= 60;

            String time = "Time - " +
                    String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, mins, secs);
            binding.activeLayout.timeTextView.setText(time);

            handler.postDelayed(this, ONE_SECOND);
        }
    };


    /**
     * Runnable para actualizar la distancia, el ritmo medio y la visualización durante la actividad. Manipulador
     * ejecuta los cuatro (4) segundos ejecutables.
     */
    private Runnable dataUpdates = new Runnable() {
        @Override
        public void run() {
            String distance = String.format(Locale.getDefault(), "Distancia - %.2f mtrs",
                    UtilsValidate.convertMetersToMiles(accumDistMeters));
            binding.activeLayout.distanceTextView.setText(distance);

            float p = UtilsValidate.getAveragePace(accumDistMeters, timeInMilliseconds);
            String avgPace = String.format(Locale.getDefault(), "Ritmo promedio - %s min/mtrs",
                    UtilsValidate.convertDecimalToMins(p));
            binding.activeLayout.avgPaceTextView.setText(avgPace);

            handler.postDelayed(this, FOUR_SECONDS);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        binding = FragmentMapBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(getActivity()).get(MapViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        binding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                boolean rtr = false;
                switch (item.getItemId()) {
                    case R.id.maps_history:
                        Navigation.findNavController(getView()).navigate(R.id.action_fragmentProfile_to_history);
                        rtr = true;
                        break;
                }
                return rtr;
            }
        });

        binding.startLayout.runStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceStartLocation();
                accumDistMeters = 0;
                binding.viewSwitcher.showNext();
                startTimeMillis = SystemClock.uptimeMillis();
                stopwatch.run();
                dataUpdates.run();
                tracker.run();
            }
        });

        binding.activeLayout.runStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(stopwatch);
                handler.removeCallbacks(dataUpdates);
                endTimeMillis = SystemClock.uptimeMillis();

                viewModel.addHistory(accumDistMeters, startTimeMillis, endTimeMillis);

                resetVals();
                getDeviceStartLocation();

                binding.viewSwitcher.showPrevious();

            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (googleMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, googleMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceStartLocation();
    }

    private void updateLocationUI() {
        if (googleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                googleMap.setMyLocationEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceStartLocation() {
        /*
         * Obtenga la mejor y más reciente ubicación del dispositivo, que puede ser nula en raras ocasiones
         * casos en los que una ubicación no está disponible.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            prevLocation = mLastKnownLocation;

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();

                        } else {
                            Log.e(TAG, "Exception: %s", task.getException());
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void updateRoute(Location nextLocation) {
        if (googleMap != null) {
            points.add(new LatLng(nextLocation.getLatitude(), nextLocation.getLongitude()));

            polylineOptions = new PolylineOptions()
                    .color(Color.DKGRAY)
                    .width(POLYLINE_STROKE_WIDTH_PX)
                    .jointType(JointType.BEVEL)
                    .startCap(new RoundCap())
                    .endCap(new SquareCap())
                    .clickable(false);

            polylineOptions.addAll(points);
            googleMap.addPolyline(polylineOptions);

            accumDistMeters += prevLocation.distanceTo(nextLocation);
            prevLocation = nextLocation;
        }
    }

    public void resetVals() {
        accumDistMeters = 0;
        startTimeMillis = 0L;
        endTimeMillis = 0L;
        timeInMilliseconds = 0L;

    }


    @Override
    public void onPolylineClick(Polyline polyline) {

    }


}