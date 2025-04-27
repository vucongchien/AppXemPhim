package com.example.appxemphim.Model;

import com.google.firebase.Timestamp;

public class ReviewModel {
    private String name;
    private String avatar;
    private int rating;
    private String description;
    private Timestamp created_at;


    public ReviewModel(String name, String avatar, int rating, String description, Timestamp created_at) {
        this.name = name;
        this.avatar = avatar;
        this.rating = rating;
        this.description = description;
        this.created_at = created_at;
    }

    public ReviewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
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
}
