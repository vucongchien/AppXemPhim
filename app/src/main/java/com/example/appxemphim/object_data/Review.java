package com.example.appxemphim.object_data;

import java.sql.Time;

public class Review {
    private int Review_id;
    private int User_id;
    private int Movie_id;
    private int Rating;
    private String Description;
    private Time Created_at;

    public Review(int review_id, int user_id, int movie_id, int rating, String description, Time created_at) {
        Review_id = review_id;
        User_id = user_id;
        Movie_id = movie_id;
        Rating = rating;
        Description = description;
        Created_at = created_at;
    }

    public int getReview_id() {
        return Review_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public int getMovie_id() {
        return Movie_id;
    }

    public int getRating() {
        return Rating;
    }

    public String getDescription() {
        return Description;
    }

    public Time getCreated_at() {
        return Created_at;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCreated_at(Time created_at) {
        Created_at = created_at;
    }
}
