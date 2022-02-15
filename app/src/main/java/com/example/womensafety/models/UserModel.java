package com.example.womensafety.models;

public class UserModel {

    String uid;
    String imageUrl;
    String name;
    String email;


    public UserModel() {
    }

    public UserModel(String uid, String imageUrl, String name, String email) {
        this.uid = uid;
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
