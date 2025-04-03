package com.example.appxemphim.objectDTO;

import com.example.appxemphim.object_data.Actor_Director;
import com.example.appxemphim.object_data.Genres;
import com.example.appxemphim.object_data.Movie;
import com.example.appxemphim.object_data.Video;

import java.util.ArrayList;

public class MovieDTO {
    private Movie movie;
    private ArrayList<Video> videos;
    private ArrayList<Genres> genres;
    private ArrayList<Actor_Director> actors;
    private ArrayList<Actor_Director> directors;

    public MovieDTO(Movie movie, ArrayList<Video> videos, ArrayList<Genres> genres, ArrayList<Actor_Director> actors, ArrayList<Actor_Director> directors) {
        this.movie = movie;
        this.videos = videos;
        this.genres = genres;
        this.actors = actors;
        this.directors = directors;
    }

    public MovieDTO() {
        videos = new ArrayList<>();
        genres = new ArrayList<>();
        actors = new ArrayList<>();
        directors = new ArrayList<>();
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
    }

    public ArrayList<Actor_Director> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Actor_Director> actors) {
        this.actors = actors;
    }

    public ArrayList<Actor_Director> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Actor_Director> directors) {
        this.directors = directors;
    }
}
