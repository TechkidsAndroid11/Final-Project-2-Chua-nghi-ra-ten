package com.example.admins.hotelhunter.model;

/**
 * Created by Admins on 1/17/2018.
 */

public class ReviewModel {

    public String userName;
    public String date;
    public String review;
    public float ratting;


    public ReviewModel(String userName, String date, String review, float ratting) {
        this.userName = userName;
        this.date = date;
        this.review = review;
        this.ratting = ratting;


    }

    public float getRatting() {
        return ratting;
    }

    public ReviewModel() {

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

    @Override
    public String toString() {
        return "ReviewModel{" +
                "userName='" + userName + '\'' +
                ", date='" + date + '\'' +
                ", review='" + review + '\'' +
                ", ratting=" + ratting +
                '}';
    }
}

