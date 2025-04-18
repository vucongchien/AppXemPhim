package com.example.appxemphim.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoModel {
    private String video_id;
    private String video_url;
    private long duration;
    private int view;
}
