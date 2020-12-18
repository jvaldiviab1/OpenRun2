package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jvaldiviab.openrun2.data.repository.FireBaseRepository;
import com.jvaldiviab.openrun2.data.repository.MapsRepository;
import com.jvaldiviab.openrun2.data.repository.ProfileRepository;

public class MapViewModel extends AndroidViewModel {

    private MapsRepository mapsRepository;
    public MapViewModel(@NonNull Application application) {
        super(application);
        this.mapsRepository = new MapsRepository();
    }

    public void addHistory(float accumDistMeters, long startTimeMillis, long endTimeMillis){
        mapsRepository.addHistory( accumDistMeters,  startTimeMillis,  endTimeMillis);
    }
}
