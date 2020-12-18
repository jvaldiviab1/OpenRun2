package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.jvaldiviab.openrun2.data.model.UsersPojo;
import com.jvaldiviab.openrun2.data.repository.ProfileRepository;
import com.jvaldiviab.openrun2.view.adapter.AdapterProfile;

import java.util.ArrayList;
import java.util.List;

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
    public void updateProfileFirebase(UsersPojo userPojo) {
        profileRepository.updateProfilFirebase(userPojo);
    }

    public MutableLiveData<UsersPojo> getProfileLiveData() {
        return profileRepository.getProfileLiveData();
    }
    public void signOutProfile() {
        profileRepository.signOutProfile();
    }

    public void listarMedallas(RecyclerView RV) {
    try {
        List<Integer> lista = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            lista.add(i);
        }
        AdapterProfile adapterDatos = new AdapterProfile(lista, RV);
        RV.setAdapter(adapterDatos);
    }catch (Exception e){
        System.out.println(e.toString());}
    }
}
