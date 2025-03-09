package com.example.appxemphim.object_data;

import java.sql.Time;

public class History {
    private int History_id;
    private int User_id;
    private int Video_id;
    private double Person_view;
    private Time Updated_at;

    public History(int history_id, int user_id, int video_id, double person_view, Time updated_at) {
        History_id = history_id;
        User_id = user_id;
        Video_id = video_id;
        Person_view = person_view;
        Updated_at = updated_at;
    }

    public int getVideo_id() {
        return Video_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public int getHistory_id() {
        return History_id;
    }

    public double getPerson_view() {
        return Person_view;
    }

    public Time getUpdated_at() {
        return Updated_at;
    }

    public void setPerson_view(double person_view) {
        Person_view = person_view;
    }

    public void setUpdated_at(Time updated_at) {
        Updated_at = updated_at;
    }
}
