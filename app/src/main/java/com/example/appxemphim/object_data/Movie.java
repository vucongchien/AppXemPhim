package com.example.appxemphim.object_data;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Movie {
    private String movie_Id;
    private String title; //tên của bộ phim
    private String description; // mô tả của bộ phim
    private String poster_url; // link ảnh poster
    private String trailer_url;// link trailer của bộ phim
    private String nation; // bộ phim thuộc quốc gia nào
    private Date created_at; // thời gian tạo phim

    public Movie() {}
    public Movie(String title, String description, String poster_url, String trailer_url, String nation, Date created_at) {
        this.title = title;
        this.description = description;
        this.poster_url = poster_url;
        this.trailer_url = trailer_url;
        this.nation = nation;
        this.created_at = created_at;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
