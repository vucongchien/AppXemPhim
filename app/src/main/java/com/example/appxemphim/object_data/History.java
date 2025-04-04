package com.example.appxemphim.object_data;

import java.sql.Time;
import java.util.Date;

public class History {
//    private String History_id;
//    private String User_id;
    private String Video_id;
    private double Person_view;
    private Date Updated_at;
    // 1 use có 1 danh sách video đã xem vậy thì để user_id làm history_id


    public History( String video_id, double person_view, Date updated_at) {
//        User_id = user_id;
        Video_id = video_id;
        Person_view = person_view;
        Updated_at = updated_at;
    }

    public History() {
    }

//    public String getHistory_id() {
//        return History_id;
//    }
//
//    public void setHistory_id(String history_id) {
//        History_id = history_id;
//    }
//
//    public String getUser_id() {
//        return User_id;
//    }
//
//    public void setUser_id(String user_id) {
//        User_id = user_id;
//    }

    public String getVideo_id() {
        return Video_id;
    }

    public void setVideo_id(String video_id) {
        Video_id = video_id;
    }

    public Date getUpdated_at() {
        return Updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        Updated_at = updated_at;
    }

    public double getPerson_view() {
        return Person_view;
    }

    public void setPerson_view(double person_view) {
        Person_view = person_view;
    }
}
