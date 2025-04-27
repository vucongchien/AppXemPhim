package com.example.appxemphim.Request;

import com.google.firebase.Timestamp;

public class ReviewRequest {
    private String user_id;
    private String movie_id;
    private int rating;
    private String description;

    public ReviewRequest(String description, int rating, String movie_id, String user_id) {
        this.description = description;
        this.rating = rating;
        this.movie_id = movie_id;
        this.user_id = user_id;
    }

    public ReviewRequest() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }
}
