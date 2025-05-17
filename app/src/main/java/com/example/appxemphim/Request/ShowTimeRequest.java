package com.example.appxemphim.Request;

import com.example.appxemphim.Model.DTO.EpisodeInfoDTO;

import java.util.List;

public class ShowTimeRequest {
    private String movieId;
    private List<EpisodeInfoDTO> showTimes; // Danh sách tập sắp chiếu
}
