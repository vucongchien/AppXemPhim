package com.example.appxemphim.Response;

import com.example.appxemphim.Model.DTO.EpisodeInfoDTO;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowTimeResponse {
    @SerializedName("content")
    private List<EpisodeInfoDTO> content;

    @SerializedName("totalPages")
    private int totalPages;

    @SerializedName("totalElements")
    private int totalElements;

    @SerializedName("last")
    private boolean last;

    @SerializedName("first")
    private boolean first;

    @SerializedName("number")
    private int number;

    @SerializedName("size")
    private int size;

    @SerializedName("numberOfElements")
    private int numberOfElements;

    @SerializedName("empty")
    private boolean empty;

    public List<EpisodeInfoDTO> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public boolean isLast() {
        return last;
    }

    public boolean isFirst() {
        return first;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public boolean isEmpty() {
        return empty;
    }
}
