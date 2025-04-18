package com.example.appxemphim.Model;

import com.google.firebase.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewModel {
    private String user_id;
    private double rating;
    private String description;
    private Timestamp created_at;
}
