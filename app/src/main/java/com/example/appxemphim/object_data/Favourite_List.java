package com.example.appxemphim.object_data;

import java.sql.Time;

public class Favourite_List {
    private int Favourite_list_id;
    private int User_id;
    private int Movie_id;
    private Time Time_add;

    public Favourite_List(int favourite_list_id, Time time_add, int user_id, int movie_id) {
        Favourite_list_id = favourite_list_id;
        Time_add = time_add;
        User_id = user_id;
        Movie_id = movie_id;
    }

    public int getFavourite_list_id() {
        return Favourite_list_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public int getMovie_id() {
        return Movie_id;
    }

    public Time getTime_add() {
        return Time_add;
    }

    public void setTime_add(Time time_add) {
        Time_add = time_add;
    }
}
