package com.example.appxemphim.object_data;

public class Genres {
    private int  genres_id;
    private  String  name;

    public Genres(int genres_id, String name) {
        this.genres_id = genres_id;
        this.name = name;
    }

    public int getGenres_id() {
        return genres_id;
    }

    public void setGenres_id(int genres_id) {
        this.genres_id = genres_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
