package com.jvaldiviab.openrun2.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.databinding.ActivityLoginBinding;
import com.jvaldiviab.openrun2.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthState;

    private Context mContext;
    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding binding;

    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mContext = LoginActivity.this;
        mAuth = FirebaseAuth.getInstance();
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginViewModel.login(binding.txtEmail.getText().toString(), binding.txtPassword.getText().toString());
            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        mLoginViewModel.getLoginMutableData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    startActivity(new Intent(LoginActivity.this, BaseActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "onChanged: no login");
                }
            }
        });


    }
    /*@Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthState);
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, BaseActivity.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthState!=null){
            mAuth.removeAuthStateListener(mAuthState);
        }
    }*/
}