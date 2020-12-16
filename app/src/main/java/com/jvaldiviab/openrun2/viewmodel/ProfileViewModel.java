package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.data.repository.FireBaseRepositoryImpl;
import com.jvaldiviab.openrun2.data.repository.ProfileRepository;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository profileRepository;


    public ProfileViewModel(@NonNull Application application) {
        super(application);
        this.profileRepository = new ProfileRepository();
        System.out.println("HOLAHOLAHOLAHOLAHOLA");
    }

    public void updateProfile() {
        profileRepository.updateProfile();
    }

    public MutableLiveData<UsersPojo> getProfileLiveData() {
        return profileRepository.getProfileLiveData();
    }


}
