package com.example.appxemphim.object_data;

public class Movie_Genres {
    private int movive_Id;
    private int  genres_id;

    public Movie_Genres(int genres_id, int movive_Id) {
        this.genres_id = genres_id;
        this.movive_Id = movive_Id;
    }

    public int getMovive_Id() {
        return movive_Id;
    }

    public int getGenres_id() {
        return genres_id;
    }
}
