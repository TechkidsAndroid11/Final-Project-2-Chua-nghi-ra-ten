package com.example.admins.hotelhunter.model;

/**
 * Created by Admins on 1/17/2018.
 */

public class ReviewModel {

    public String userName;
    public String date;
    public String review;
    public int ratting;


    public ReviewModel(String userName, String date, String review, int ratting) {
        this.userName = userName;
        this.date = date;
        this.review = review;
        this.ratting = ratting;


    }


    public ReviewModel() {

    }

    public ReviewModel(String format, String s, int numStars) {
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getReview() {
        return review;
    }

    public int getRatting() {
        return ratting;
    }
}

