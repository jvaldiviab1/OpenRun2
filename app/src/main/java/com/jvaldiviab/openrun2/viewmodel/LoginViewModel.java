package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepositoryImpl;

public class LoginViewModel extends AndroidViewModel {

    private FireBaseRepository fireBaseRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.fireBaseRepository = new FireBaseRepositoryImpl(application.getBaseContext());
        System.out.println("SE PASO POR AQUI");
    }

    public void login(String email, String password) {
        fireBaseRepository.loginUser(email, password);
    }

    public MutableLiveData<FirebaseUser> getLoginMutableData() {
        return fireBaseRepository.getLoginFirebaseUser();
    }


}
