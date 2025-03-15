package com.example.appxemphim.object_data;

import java.sql.Time;

public class Hot_Movie {
    private String Hot_movie_id;
    private String Movie_id;
    private Time Time_start;
    private Time Time_end;
    private int View;

    public Hot_Movie(String movie_id, Time time_start, Time time_end, int view) {
        Movie_id = movie_id;
        Time_start = time_start;
        Time_end = time_end;
        View = 0;
    }

    public Hot_Movie(String hot_movie_id, String movie_id, Time time_start, Time time_end, int view) {
        Hot_movie_id = hot_movie_id;
        Movie_id = movie_id;
        Time_start = time_start;
        Time_end = time_end;
        View = view;
    }

    public String getHot_movie_id() {
        return Hot_movie_id;
    }

    public void setHot_movie_id(String hot_movie_id) {
        Hot_movie_id = hot_movie_id;
    }

    public String getMovie_id() {
        return Movie_id;
    }

    public void setMovie_id(String movie_id) {
        Movie_id = movie_id;
    }

    public Time getTime_start() {
        return Time_start;
    }

    public void setTime_start(Time time_start) {
        Time_start = time_start;
    }

    public Time getTime_end() {
        return Time_end;
    }

    public void setTime_end(Time time_end) {
        Time_end = time_end;
    }

    public int getView() {
        return View;
    }

    public void setView(int view) {
        View = view;
    }
}
