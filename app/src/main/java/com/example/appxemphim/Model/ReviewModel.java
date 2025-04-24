package com.example.appxemphim.Model;

import com.google.firebase.Timestamp;

public class ReviewModel {
    private String user_id;
    private double rating;
    private String description;
    private Timestamp created_at;

    public ReviewModel() {
    }

    public ReviewModel(String user_id, double rating, String description, Timestamp created_at) {
        this.user_id = user_id;
        this.rating = rating;
        this.description = description;
        this.created_at = created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "ReviewModel{" +
                "user_id='" + user_id + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
