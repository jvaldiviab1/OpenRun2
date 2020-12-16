package com.jvaldiviab.openrun2.view.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.databinding.FragmentMapBinding;
import com.jvaldiviab.openrun2.util.UtilsValidate;
import com.jvaldiviab.openrun2.view.activity.BaseActivity;

import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    private GoogleMap googleMap;
    private CameraPosition cameraPosition;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private final LatLng mDefaultLocation = new LatLng(-33, 151);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private boolean mLocationPermissionGranted = true;

    private Location mLastKnownLocation;
    private Location prevLocation;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

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


    /**
     * Runnable to update and display time of activity. Handler executes the runnable
     * every second.
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
     * Runnable to update distance, average pace and display during the activity. Handler
     * executes the runnable four(4) seconds.
     */
    private Runnable dataUpdates = new Runnable() {
        @Override
        public void run() {
            String distance = String.format(Locale.getDefault(), "Distance - %.2f mi",
                    UtilsValidate.convertMetersToMiles(accumDistMeters));
            binding.activeLayout.distanceTextView.setText(distance);

            float p = UtilsValidate.getAveragePace(accumDistMeters, timeInMilliseconds);
            String avgPace = String.format(Locale.getDefault(), "Average Pace - %s min/mi",
                    UtilsValidate.convertDecimalToMins(p));
            binding.activeLayout.avgPaceTextView.setText(avgPace);

            handler.postDelayed(this, FOUR_SECONDS);
        }
    };


    FragmentMapBinding binding;

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

        binding.startLayout.runStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceStartLocation();
                accumDistMeters = 0;
                binding.viewSwitcher.showNext();
                startTimeMillis = SystemClock.uptimeMillis();
                stopwatch.run();
                dataUpdates.run();
            }
        });

        binding.activeLayout.runStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(stopwatch);
                handler.removeCallbacks(dataUpdates);
                endTimeMillis = SystemClock.uptimeMillis();

                //addNewRunToDatabase(accumDistMeters, startTimeMillis, endTimeMillis);

                //journalActivity = new Intent(getApplicationContext(), JournalActivity.class);
                // startActivity(journalActivity);
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
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            LatLng currentLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Ubicación actual"));
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

    @Override
    public void onPolylineClick(Polyline polyline) {

    }


}