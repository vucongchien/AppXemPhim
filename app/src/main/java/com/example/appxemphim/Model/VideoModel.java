package com.example.appxemphim.Model;

public class VideoModel {
    private String video_id;
    private String video_url;
    private long duration;
    private int view;

    public VideoModel() {
    }

    public VideoModel(String video_id, String video_url, long duration, int view) {
        this.video_id = video_id;
        this.video_url = video_url;
        this.duration = duration;
        this.view = view;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "VideoModel{" +
                "video_id='" + video_id + '\'' +
                ", video_url='" + video_url + '\'' +
                ", duration=" + duration +
                ", view=" + view +
                '}';
    }
}
