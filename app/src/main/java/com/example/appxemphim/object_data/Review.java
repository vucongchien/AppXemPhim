package com.example.appxemphim.object_data;

import java.sql.Time;

public class Review {
    private String Review_id;
    private String User_id;
    private String Movie_id;
    private int Rating;
    private String Description;
    private Time Created_at;

    public Review(String user_id, String movie_id, int rating, String description, Time created_at) {
        User_id = user_id;
        Movie_id = movie_id;
        Rating = rating;
        Description = description;
        Created_at = created_at;
    }

    public Review(String review_id, String user_id, String movie_id, int rating, String description, Time created_at) {
        Review_id = review_id;
        User_id = user_id;
        Movie_id = movie_id;
        Rating = rating;
        Description = description;
        Created_at = created_at;
    }

    public String getReview_id() {
        return Review_id;
    }

    public void setReview_id(String review_id) {
        Review_id = review_id;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getMovie_id() {
        return Movie_id;
    }

    public void setMovie_id(String movie_id) {
        Movie_id = movie_id;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public Time getCreated_at() {
        return Created_at;
    }

    public void setCreated_at(Time created_at) {
        Created_at = created_at;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
