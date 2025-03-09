package com.example.appxemphim.object_data;

import java.sql.Time;

public class Hot_Movie {
    private int Hot_movie_id;
    private int Movie_id;
    private Time Time_start;
    private Time Time_end;
    private int View;

    public Hot_Movie(int hot_movie_id, int view, Time time_end, Time time_start, int movie_id) {
        Hot_movie_id = hot_movie_id;
        View = view;
        Time_end = time_end;
        Time_start = time_start;
        Movie_id = movie_id;
    }

    public int getHot_movie_id() {
        return Hot_movie_id;
    }

    public int getMovie_id() {
        return Movie_id;
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
