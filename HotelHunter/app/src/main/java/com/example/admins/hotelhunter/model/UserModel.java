package com.example.admins.hotelhunter.model;

import java.util.List;

/**
 * Created by Admins on 1/11/2018.
 */

public class UserModel {
    public String name;
    public String uid;
    public List<ReviewModel> reviewModels;

    public String uri;



    public UserModel(String name, String uri) {
        this.name = name;


        this.uri = uri;

    }

    public UserModel(String name, String uid, List<ReviewModel> reviewModels, String uri) {
        this.name = name;
        this.uid = uid;
        this.reviewModels = reviewModels;
        this.uri = uri;
    }

    public UserModel() {
    }

    public UserModel(String name) {
        this.name = name;


    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", reviewModels=" + reviewModels +
                ", uri='" + uri + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
