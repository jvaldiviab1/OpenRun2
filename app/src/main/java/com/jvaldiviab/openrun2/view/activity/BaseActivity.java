package com.jvaldiviab.openrun2.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.data.repository.MusicPlayerRepository;
import com.jvaldiviab.openrun2.databinding.ActivityBaseBinding;
import com.jvaldiviab.openrun2.util.KeepStateNavigation;
import com.jvaldiviab.openrun2.util.UtilsPermissions;
import com.jvaldiviab.openrun2.viewmodel.MainViewModel;

import java.util.HashMap;

import com.androidnetworking.AndroidNetworking;

/**
 * Activity principal donde se 'pintarán' los fragments
 * necesarios para el uso del aplicativo
 *
 * @author Valdivia Berrios, Juan Carlos
 * @version 1.0, 17/12/20
 */
public class BaseActivity extends AppCompatActivity {

    public static final String TAG_FRAGMENT_MUSIC_BAR = "tag_fragment_music_bar";
    public static final String TAG_MUSIC_PLAYER_VIEW_PAGER = "tag_music_player_view_pager";
    public static final String STORAGE_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String SERVICE_LEARNING = "SERVICE_LEARNING";
    private static final int REQUEST_READ_STORAGE = 10002;

    private MainViewModel viewModel;

    private HashMap<String, Boolean> permissionsStatus = new HashMap<>();

    ActivityBaseBinding binding;

    private FirebaseAuth mAuth;
    NavController navController;

    public static Intent newIntent(Context context) {
        return new Intent(context, BaseActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        UtilsPermissions.checkLocationPermission(BaseActivity.this);
        AndroidNetworking.initialize(getApplicationContext());
        keepState();
        checkStoragePermission();
    }

    private void keepState() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // get fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        // setup custom navigator
        KeepStateNavigation navigator = new KeepStateNavigation(this, navHostFragment.getChildFragmentManager(), R.id.nav_host_fragment);
        NavigatorProvider navigatorProvider= navController.getNavigatorProvider();
        navigatorProvider.addNavigator(navigator);

        // set navigation graph
        navController.setGraph(R.navigation.nav_graph);
        setupBottomNavMenu(navController);
    }
    private void setupBottomNavMenu(NavController navController) {
        if (navController != null) {
            NavigationUI.setupWithNavController(binding.bottomMenu, navController);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        fillPermissionStatus(permissions, grantResults);

        switch (requestCode) {
            case REQUEST_READ_STORAGE:
                if (permissionsStatus.get(STORAGE_PERMISSION)) {
                    MusicPlayerRepository.getInstance().loadMusics(getContentResolver());
                    startActivity(newIntent(this));
                    finish();
                }
                break;
            default:
                break;
        }


    }

    private void fillPermissionStatus(@NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++)
            permissionsStatus.put(permissions[i], grantResults[i] != -1);
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
        else
            MusicPlayerRepository.getInstance().loadMusics(getContentResolver());
    }

}