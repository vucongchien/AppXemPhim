package com.example.appxemphim.object_data;

import java.sql.Time;
import java.util.Date;

public class Favourite_List {
    //private String User_id;
    private String Movie_id;
    private Date Time_add;

    public Favourite_List() {

    }

    public Favourite_List( String movie_id, Date time_add) {
        Movie_id = movie_id;
        Time_add = time_add;
    }

//    public String getUser_id() {
//        return User_id;
//    }
//
//    public void setUser_id(String user_id) {
//        User_id = user_id;
//    }


    public String getMovie_id() {
        return Movie_id;
    }

    public void setMovie_id(String movie_id) {
        Movie_id = movie_id;
    }

    public Date getTime_add() {
        return Time_add;
    }

    public void setTime_add(Date time_add) {
        Time_add = time_add;
    }

}
