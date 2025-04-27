package com.example.appxemphim.Model;

import com.google.firebase.Timestamp;

import java.util.List;

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
    private List<GenreModel> genres;

    public MovieDetailModel() {
    }

    public MovieDetailModel(String movie_Id, String title, String description, String poster_url,
                          String trailer_url, double rating, String nation, Timestamp created_at,
                          List<VideoModel> videos, List<ActorModel> actors, List<DirectorModel> directors, List<GenreModel> genres) {
        this.movie_Id = movie_Id;
        this.title = title;
        this.description = description;
        this.poster_url = poster_url;
        this.trailer_url = trailer_url;
        this.rating = rating;
        this.nation = nation;
        this.created_at = created_at;
        this.videos = videos;
        this.actors = actors;
        this.directors = directors;
        this.genres = genres;
    }

    public String getMovie_Id() {
        return movie_Id;
    }

    public void setMovie_Id(String movie_Id) {
        this.movie_Id = movie_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public List<VideoModel> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoModel> videos) {
        this.videos = videos;
    }

    public List<ActorModel> getActors() {
        return actors;
    }

    public void setActors(List<ActorModel> actors) {
        this.actors = actors;
    }

    public List<DirectorModel> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorModel> directors) {
        this.directors = directors;
    }


    public List<GenreModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreModel> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "MovieDetailModel{" +
                "movie_Id='" + movie_Id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", poster_url='" + poster_url + '\'' +
                ", trailer_url='" + trailer_url + '\'' +
                ", rating=" + rating +
                ", nation='" + nation + '\'' +
                ", created_at=" + created_at +
                ", videos=" + videos +
                ", actors=" + actors +
                ", directors=" + directors +
                ", genres=" + genres +
                '}';
    }
}
