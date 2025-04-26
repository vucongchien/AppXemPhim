package com.example.appxemphim.Model;

public class GenreModel {
    private String genres_id;
    private String name;

    public GenreModel() {
    }

    public GenreModel(String genres_id, String name) {
        this.genres_id = genres_id;
        this.name = name;
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

    @Override
    public String toString() {
        return "GenreModel{" +
                "genres_id='" + genres_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
