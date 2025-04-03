package com.example.appxemphim.object_data;

import static com.example.appxemphim.Utilities.GoogleDriveUtils.getVideoDuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.util.HashMap;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.widget.Toast;


import com.example.appxemphim.Utilities.GoogleDriveUtils;

import java.io.IOException;

public class Video {
    private String video_id;
    private String video_url;
    private long duration;
    private int view;


    public Video() {
    }

    public Video(String video_url) {
        this.video_url = video_url;
    }


    public long getDuration() {
        return duration;
    }


    public void setDuration(long duration) {
        this.duration=duration;
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

    public void setView(int view) {
        this.view = view;
    }
}
