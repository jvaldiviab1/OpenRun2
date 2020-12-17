package com.jvaldiviab.openrun2.data.model;

public class RunPojo {
    private String mId;
    private String distance;
    private String time;
    private String avgPace;
    private String date;

    public RunPojo(String mId, String distance, String time, String avgPace, String date) {
        this.mId = mId;
        this.distance = distance;
        this.time = time;
        this.avgPace = avgPace;
        this.date = date;
    }

    public RunPojo(){
        this("", "", "", "", "");
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvgPace() {
        return avgPace;
    }

    public void setAvgPace(String avgPace) {
        this.avgPace = avgPace;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
;