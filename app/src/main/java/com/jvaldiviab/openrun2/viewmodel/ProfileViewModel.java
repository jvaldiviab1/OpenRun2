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
    UsersPojo usersPojo= new UsersPojo();

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

    public void updateUser(UsersPojo usersPojo) {
        this.usersPojo = usersPojo;
    }

    public String getPhoto() {
        return usersPojo.getPhoto();
    }

    public String getName() {
        return usersPojo.getName();
    }

    public String getDescription() {
        return usersPojo.getDescription();
    }

    public String getAge() {
        return usersPojo.getAge();
    }

    public String getCalories() {
        return usersPojo.getAge();
    }

    public String getEmail() {
        return usersPojo.getEmail();
    }

    public String getHeight() {
        return usersPojo.getHeight();
    }

    public String getWeight() {
        return usersPojo.getWeight();
    }

    public String getTargetWeight() {
        return usersPojo.getTargetWeight();
    }


    public String getBodyType() {
        return usersPojo.getBodyType();
    }

    public String getTrainingType() {
        return usersPojo.getTrainingType();
    }

}
