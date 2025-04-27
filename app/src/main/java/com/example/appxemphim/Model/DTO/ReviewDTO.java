package com.example.appxemphim.Model.DTO;

import com.google.firebase.Timestamp;

public class ReviewDTO {
    private  String avata;
    private String name;
    private int rating;
    private String description;
    private Timestamp created_at;

    public ReviewDTO(String avata, String name, int rating, String description, Timestamp created_at) {
        this.avata = avata;
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.created_at = created_at;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
