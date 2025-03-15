package com.example.appxemphim.object_data;

import java.sql.Time;

public class Favourite_List {
    private String Favourite_list_id;
    private String User_id;
    private String Movie_id;
    private Time Time_add;

    public Favourite_List(String user_id, String movie_id, Time time_add) {
        User_id = user_id;
        Movie_id = movie_id;
        Time_add = time_add;
    }

    public Favourite_List(String favourite_list_id, String user_id, String movie_id, Time time_add) {
        Favourite_list_id = favourite_list_id;
        User_id = user_id;
        Movie_id = movie_id;
        Time_add = time_add;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getFavourite_list_id() {
        return Favourite_list_id;
    }

    public void setFavourite_list_id(String favourite_list_id) {
        Favourite_list_id = favourite_list_id;
    }

    public String getMovie_id() {
        return Movie_id;
    }

    public void setMovie_id(String movie_id) {
        Movie_id = movie_id;
    }

    public Time getTime_add() {
        return Time_add;
    }

    public void setTime_add(Time time_add) {
        Time_add = time_add;
    }
}
