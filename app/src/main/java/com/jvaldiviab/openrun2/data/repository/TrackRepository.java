package com.jvaldiviab.openrun2.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.jvaldiviab.openrun2.data.model.TrackPojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrackRepository {

    private static TrackRepository instance;

    public static TrackRepository getInstance() {
        if (instance == null)
            instance = new TrackRepository();
        return instance;
    }

    private TrackRepository() {
        isShuffleMode.setValue(false);
        isRepeatingMode.setValue(false);
    }

    private List<TrackPojo> allTracks = new ArrayList<>();


    private List<TrackPojo> shuffleTracks;

    private MutableLiveData<Boolean> isShuffleMode = new MutableLiveData<>();
    private MutableLiveData<Boolean> isRepeatingMode = new MutableLiveData<>();

    public void setAllTracks(List<TrackPojo> allTracks) {
        this.allTracks = allTracks;
    }

    public List<TrackPojo> getAllTracks() {
        return allTracks;
    }

    public List<TrackPojo> getShuffleTracks() {
        return shuffleTracks;
    }

    public List<TrackPojo> getTracks(String search) {
        if (allTracks != null)
            return allTracks.stream().filter(track -> track.getTitle().toLowerCase().contains(search) || track.getAlbumName().toLowerCase().contains(search) || track.getArtist().toLowerCase().contains(search)).collect(Collectors.toList());
        return new ArrayList<>();
    }

    public int getTrackIndex(TrackPojo track) {
        List<TrackPojo> allItems = isShuffleMode.getValue() ? shuffleTracks : allTracks;
        for (int i = 0; i < allItems.size(); i++)
            if (allItems.get(i).getTrackId() == track.getTrackId())
                return i;
        return 0;
    }


    public TrackPojo getTrackById(long trackId) {
        for (TrackPojo track : allTracks)
            if (track.getTrackId() == trackId) return track;
        return null;
    }

    public void makeShuffle() {
        shuffleTracks = new ArrayList<>(allTracks);
        Collections.shuffle(shuffleTracks);
    }


    public void switchShuffle() {
        isShuffleMode.setValue(!isShuffleMode.getValue());
        if (isShuffleMode.getValue()) makeShuffle();
    }

    public void switchRepeating() {
        this.isRepeatingMode.setValue(!isRepeatingMode.getValue());
    }

    public MutableLiveData<Boolean> isRepeating() {
        return isRepeatingMode;
    }

    public MutableLiveData<Boolean> isShuffle() {
        return isShuffleMode;
    }

    public TrackPojo goPreviousTrack() {
        return null;
    }

    public TrackPojo goNextTrack() {
        return null;
    }

}
