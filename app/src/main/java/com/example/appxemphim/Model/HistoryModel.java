package com.example.appxemphim.Model;

import com.google.firebase.Timestamp;

public class HistoryModel {
    private String video_id;
    private double person_view;
    private Timestamp updated_at;

    public HistoryModel() {
    }

    public HistoryModel(String video_id, double person_view, Timestamp updated_at) {
        this.video_id = video_id;
        this.person_view = person_view;
        this.updated_at = updated_at;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public double getPerson_view() {
        return person_view;
    }

    public void setPerson_view(double person_view) {
        this.person_view = person_view;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "HistoryModel{" +
                "video_id='" + video_id + '\'' +
                ", person_view=" + person_view +
                ", updated_at=" + updated_at +
                '}';
    }
}
