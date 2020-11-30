package com.jvaldiviab.openrun2.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.databinding.ActivityDeleteBinding;
import com.jvaldiviab.openrun2.databinding.ActivityRegisterBinding;

public class BaseActivity extends AppCompatActivity {

    ActivityDeleteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDeleteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.bottomMenu.inflateMenu(R.menu.menu);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_music, R.id.navigation_profile, R.id.navigation_statistics,R.id.navigation_todos
        ).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomMenu,navController);
    }
}