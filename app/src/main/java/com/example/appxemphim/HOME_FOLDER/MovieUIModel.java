package com.example.appxemphim.HOME_FOLDER;

public class MovieUIModel {
    private int movieId;
    private String title;
    private String posterUrl;
    private String rating;
    private String description;

    public MovieUIModel(int movieId, String title, String posterUrl, String rating, String description) {
        this.movieId = movieId;
        this.title = title;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.description = description;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
