package com.example.appxemphim.object_data;

public class Movie_Genres {
    private String movive_Id;
    private String genres_id;

    public Movie_Genres(String movive_Id, String genres_id) {
        this.movive_Id = movive_Id;
        this.genres_id = genres_id;
    }

    public Movie_Genres() {
    }

    public String getMovive_Id() {
        return movive_Id;
    }

    public void setMovive_Id(String movive_Id) {
        this.movive_Id = movive_Id;
    }

    public String getGenres_id() {
        return genres_id;
    }

    public void setGenres_id(String genres_id) {
        this.genres_id = genres_id;
    }
}
