package com.example.appxemphim.object_data;

import java.sql.Time;
import java.util.Date;

public class Review {
    //private String Review_id;
    private String User_id;
//    private String Movie_id;
    private float Rating;
    private String Description;
    private Date Created_at;

    public Review(float rating, String description) {
        //User_id = user_id;
//        Movie_id = movie_id;
        Rating = rating;
        Description = description;
    }

    public Review() {
    }

//    public String getReview_id() {
//        return Review_id;
//    }
//
//    public void setReview_id(String review_id) {
//        Review_id = review_id;
//    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

//    public String getMovie_id() {
//        return Movie_id;
//    }
//
//    public void setMovie_id(String movie_id) {
//        Movie_id = movie_id;
//    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public Date getCreated_at() {
        return Created_at;
    }

    public void setCreated_at(Date created_at) {
        Created_at = created_at;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
