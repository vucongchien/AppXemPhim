package com.example.appxemphim.Model.DTO;

import java.time.LocalDateTime;

public class EpisodeInfoDTO {
    private String movieId;
    private int seasonNumber;
    private int episodeNumber;
    private String episodeTitle;
    private LocalDateTime releaseTime;
    private int durationInMinutes;

    public EpisodeInfoDTO() {
    }

    public EpisodeInfoDTO(String movieId,int seasonNumber, int episodeNumber, String episodeTitle, LocalDateTime releaseTime, int durationInMinutes) {
        this.movieId=movieId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeTitle = episodeTitle;
        this.releaseTime = releaseTime;
        this.durationInMinutes = durationInMinutes;
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

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
