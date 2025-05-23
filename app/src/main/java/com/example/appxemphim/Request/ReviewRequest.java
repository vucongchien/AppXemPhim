package com.example.appxemphim.Request;

import com.google.firebase.Timestamp;

public class ReviewRequest {
    private String movie_id;
    private int rating;

    public ReviewRequest( int rating, String movie_id) {

        this.rating = rating;
        this.movie_id = movie_id;

    }

    public ReviewRequest() {
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
