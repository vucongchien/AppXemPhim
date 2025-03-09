package com.example.appxemphim.object_data;

import java.sql.Time;

public class Video {
    private int video_id;
    private String video_url;
    private Time duration;
    private int movie_id;
    private int view;

    public Video(int video_id, String video_url, Time duration, int movie_id, int view) {
        this.video_id = video_id;
        this.video_url = video_url;
        this.duration = duration;
        this.movie_id = movie_id;
        this.view = view;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getView() {
        return view;
    }

    public void setView() {
        this.view = this.view+1;
    }
}
