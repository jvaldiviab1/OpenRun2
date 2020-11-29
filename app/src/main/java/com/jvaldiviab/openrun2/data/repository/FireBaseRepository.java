package com.jvaldiviab.openrun2.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.data.model.UsersPojo;

public interface FireBaseRepository {

    public void loginUser(String email, String password);
    public void registerUser(String email, String password);
    public void addDatabase(UsersPojo usersPojo);

    public MutableLiveData<FirebaseUser> getLoginFirebaseUser();
    public MutableLiveData<UsersPojo> getRegisterLiveData();

}
