package com.jvaldiviab.openrun2.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.R;
import com.jvaldiviab.openrun2.databinding.ActivityLoginBinding;
import com.jvaldiviab.openrun2.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;

    private Context mContext;
    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;

    private int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = mActivityLoginBinding.inflate(getLayoutInflater());
        View view = mActivityLoginBinding.getRoot();
        setContentView(view);

        mContext = LoginActivity.this;
        mAuth = FirebaseAuth.getInstance();
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mActivityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginViewModel.login(mActivityLoginBinding.txtEmail.getText().toString(), mActivityLoginBinding.txtPassword.getText().toString());
            }
        });

        mActivityLoginBinding.txtRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        mLoginViewModel.getLoginMutableData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    startActivity(new Intent(LoginActivity.this, DeleteActivity.class));
                    finish();
                } else {
                    Log.d(TAG, "onChanged: no login");
                }
            }
        });


    }
}