package com.example.appxemphim.Model.DTO;

import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Utilities.TimeWrapper;
import com.google.gson.annotations.SerializedName;
import com.google.firebase.Timestamp;


public class FavoriteDTO {
    @SerializedName("movie")
    private MovieOverviewModel movie;
    @SerializedName("time_add")
    private TimeWrapper timeAdd;
    public MovieOverviewModel getMovie() {
        return movie;
    }
    public Timestamp getTimeAdd() {
        return timeAdd.toTimestamp();
    }
}