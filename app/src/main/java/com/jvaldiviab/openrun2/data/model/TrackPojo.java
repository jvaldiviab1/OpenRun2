package com.jvaldiviab.openrun2.data.model;

import android.net.Uri;

import java.util.Date;

public class TrackPojo {

    private long trackId;
    private String trackTitle;
    private String albumName;
    private String artistName;
    private Uri imagePath;
    // length to seconds
    private int trackLength;
    private Date lastModified;
    private int trackPlayCount;

    private int index;


    public TrackPojo(long trackId, String trackTitle, String albumName, String artistName, Uri imagePath, int trackLength, Date lastModified, int trackPlayCount) {
        this.trackId = trackId;
        this.trackTitle = trackTitle;
        this.albumName = albumName;
        this.artistName = artistName;
        this.imagePath = imagePath;
        this.trackLength = trackLength;
        this.lastModified = lastModified;
        this.trackPlayCount = trackPlayCount;
    }

    public TrackPojo(long trackId, String trackTitle, String albumName, String artistName, Uri imagePath, int trackLength, Date lastModified) {
        this(trackId, trackTitle, albumName, artistName, imagePath, trackLength, lastModified, 0);
    }


    public long getTrackId() {
        return trackId;
    }

    public String getTitle() {
        return trackTitle;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artistName;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public int getLength() {
        return trackLength;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public int getTrackPlayCount() {
        return trackPlayCount;
    }

    public void addCount() {
        trackPlayCount++;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
