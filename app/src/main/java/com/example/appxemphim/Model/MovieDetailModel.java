package com.example.appxemphim.Model;

import com.google.firebase.Timestamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailModel {
    private String movie_Id;
    private String title;
    private String description;
    private String poster_url;
    private String trailer_url;
    private double rating;
    private String nation;
    private Timestamp created_at;
    private List<VideoModel> videos;
    private List<ActorModel> actors;
    private List<DirectorModel> directors;
    private List<ReviewModel> reviews;
    private List<GenreModel> genres;
}
