package com.jvaldiviab.openrun2.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.R;

public class SplashActivity extends AppCompatActivity {

    private final int DURATION_SPLASH = 2000;
    private boolean Ingreso = false;
    private FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        mAuthState= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    Toast.makeText(SplashActivity.this,user.getDisplayName(),Toast.LENGTH_SHORT).show();
                }
            }
        };

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                if(!Ingreso) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, DURATION_SPLASH);
    }
    @Override
    protected void onStart() {
        super.onStart();
        try {
            mAuth.addAuthStateListener(mAuthState);
            if (mAuth.getCurrentUser() != null) {
                Ingreso=true;
                startActivity(new Intent(SplashActivity.this, BaseActivity.class));
                finish();
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthState!=null){
            mAuth.removeAuthStateListener(mAuthState);
        }
    }

}