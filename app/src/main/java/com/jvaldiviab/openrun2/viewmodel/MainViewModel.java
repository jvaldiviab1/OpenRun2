package com.jvaldiviab.openrun2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jvaldiviab.openrun2.data.repository.MusicPlayerRepository;

public class MainViewModel extends AndroidViewModel {

    private MusicPlayerRepository musicPlayer = MusicPlayerRepository.getInstance();

    public MainViewModel(@NonNull Application application){
        super(application);
    }

    public void loadMusic(){
        musicPlayer.loadMusics(getApplication().getContentResolver());
    }

    public MutableLiveData<Boolean> isPlayingMusic(){
        return musicPlayer.isPlaying();
    }

}
