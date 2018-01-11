package com.example.admins.hotelhunter;

/**
 * Created by Admins on 1/11/2018.
 */

public class UserModel {
    private String name;
    private String id;
    private String password;
    private String ConfirmPassword;

    public UserModel(String name, String id, String password, String confirmPassword) {
        this.name = name;
        this.id = id;
        this.password = password;
        ConfirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }
}
