package com.jvaldiviab.openrun2.data.model;

public class UsersPojo {
    private String name;
    private String email;
    private String height;
    private String weight;
    private String targetWeight;
    private String bodyType;
    private String trainingType;

    public UsersPojo(String name, String email, String height, String weight, String targetWeight, String bodyType, String trainingType) {
        this.name = name;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.bodyType = bodyType;
        this.trainingType = trainingType;
    }

    public UsersPojo(String name, String email){
        this(name, email, "", "", "", "", "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(String targetWeight) {
        this.targetWeight = targetWeight;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }
}
