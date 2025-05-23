package com.example.appxemphim.Request;

public class HistoryRequest {
    private String video_id;

    private double Person_view;

    public HistoryRequest(String video_id, double Person_view) {
        this.video_id = video_id;
        this.Person_view = Person_view;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }
}
