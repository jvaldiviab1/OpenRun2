package com.jvaldiviab.openrun2.data.model;

import com.jvaldiviab.openrun2.data.var.Constants;

public class UsersPojo {
    private String name;
    private String description;
    private String age;
    private String email;
    private String height;
    private String weight;
    private String targetWeight;
    private String bodyType;
    private String trainingType;
    private String photo;

    public UsersPojo(String name, String description, String age,String email, String height, String weight, String targetWeight, String bodyType, String trainingType) {
        this.name = name;
        this.description = description;
        this.age = age;
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.bodyType = bodyType;
        this.trainingType = trainingType;
        this.photo = Constants.URL_FOTO_POR_DEFECTO_USUARIOS;
    }
    public UsersPojo(String name, String email){
        this(name,"","", email, "", "", "", "", "");
    }

    public UsersPojo(){
        this("", "","", "", "", "","", "", "");
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
