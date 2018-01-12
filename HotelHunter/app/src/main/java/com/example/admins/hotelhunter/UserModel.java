package com.example.admins.hotelhunter;

/**
 * Created by Admins on 1/11/2018.
 */

public class UserModel {
    private String name;
    private String uid;

    private String uri;

    public UserModel(String name, String uri) {
        this.name = name;


        this.uri = uri;

    }

    public UserModel() {
    }

    public UserModel(String name) {
        this.name = name;


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
