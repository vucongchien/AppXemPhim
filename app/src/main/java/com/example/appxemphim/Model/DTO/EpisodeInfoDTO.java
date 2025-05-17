package com.example.appxemphim.Model.DTO;

import androidx.annotation.Nullable;

import com.example.appxemphim.Model.MovieOverviewModel;

import java.time.LocalDateTime;
import java.util.Objects;

public class EpisodeInfoDTO {
    private String movieId;
    private String posterURL;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeTitle;
    private String releaseTime;
    private int durationInMinutes;

    public EpisodeInfoDTO() {
    }

    public EpisodeInfoDTO(String movieId, String posterURL, int seasonNumber, int episodeNumber, String episodeTitle, String releaseTime, int durationInMinutes) {
        this.movieId=movieId;
        this.posterURL = posterURL;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.releaseTime = releaseTime;
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EpisodeInfoDTO that = (EpisodeInfoDTO) obj;
        return movieId == that.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getReleaseTime() {
        return releaseTime;
    }
    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
