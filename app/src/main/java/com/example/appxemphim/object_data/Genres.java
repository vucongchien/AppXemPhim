package com.example.appxemphim.object_data;

public class Genres {
    private String  genres_id;
    private  String  name;

    public Genres(String name, String genres_id) {
        this.name = name;
        this.genres_id = genres_id;
    }

    public String getGenres_id() {
        return genres_id;
    }

    public void setGenres_id(String genres_id) {
        this.genres_id = genres_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
