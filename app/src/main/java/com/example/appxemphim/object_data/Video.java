package com.example.appxemphim.object_data;

import java.sql.Time;
import java.util.HashMap;

import android.media.MediaMetadataRetriever;
import java.io.IOException;

public class Video {
    private String video_id;

    private String video_url;
    private Time duration;
    private int view;

    public Video( String video_url, Time duration, int movie_id, int view) {
        this.video_url = video_url;
        this.duration = duration;
        this.view = view;
    }

    public Video(String video_url) {
        this.video_url = video_url;
    }

    public Video(String video_id, String video_url, int view, Time duration) {
        this.video_id = video_id;
        this.video_url = video_url;
        this.view = view;
        this.duration = duration;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(String videoUrl) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(videoUrl, new HashMap<>());
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            if (time != null) {
                duration = new Time(Long.parseLong(time));
            } else {
                duration = new Time(0);
            }
        } catch (Exception e) {
            duration = new Time(0);
        } finally {
            retriever.release();
        }
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


    public int getView() {
        return view;
    }

    public void setView() {
        this.view = this.view+1;
    }
}
