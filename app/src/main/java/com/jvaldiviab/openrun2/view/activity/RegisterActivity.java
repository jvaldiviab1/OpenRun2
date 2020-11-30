package com.jvaldiviab.openrun2.view.activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.databinding.ActivityRegisterBinding;
import com.jvaldiviab.openrun2.util.UtilsValidate;
import com.jvaldiviab.openrun2.viewmodel.RegisterViewModel;

public class RegisterActivity extends FragmentActivity {

    private static final String TAG = "RegisterActivity";

    private ActivityRegisterBinding activityRegisterBinding;
    FirebaseUser user;
    FirebaseAuth mAuth;
    UsersPojo usersPojo = null;
    RegisterViewModel registerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterBinding = activityRegisterBinding.inflate(getLayoutInflater());
        View view = activityRegisterBinding.getRoot();
        setContentView(view);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        user = null;
        mAuth = FirebaseAuth.getInstance();
        activityRegisterBinding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = activityRegisterBinding.txtRegEmail.getText().toString();
                String pass = activityRegisterBinding.txtRegPass1.getText().toString();
                if (UtilsValidate.emailAndPass(email, pass))
                    registerViewModel.register(activityRegisterBinding.txtRegEmail.getText().toString(), activityRegisterBinding.txtRegPass1.getText().toString());
                else
                    Toast.makeText(RegisterActivity.this, "Correo invalido, o contraseña menor a 6 caracteres", Toast.LENGTH_LONG).show();
            }
        });

        registerViewModel.getLoginMutableData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    UsersPojo usersPojo = new UsersPojo(activityRegisterBinding.txtRegEmail.getText().toString(), activityRegisterBinding.txtRegPass1.getText().toString());
                    registerViewModel.addDatabase(usersPojo);
                }
            }
        });

        registerViewModel.getRegisterLiveData().observe(this, new Observer<UsersPojo>() {
            @Override
            public void onChanged(UsersPojo usersPojo) {
                if (usersPojo != null) {
                    startActivity(new Intent(RegisterActivity.this, BaseActivity.class));
                    finish();
                }
            }
        });

    }


}