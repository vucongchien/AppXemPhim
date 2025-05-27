package com.example.appxemphim.Model;

public class HistoryWithMovie {
    private HistoryModel history;
    private MovieDetailModel movie;

    public HistoryWithMovie(HistoryModel history, MovieDetailModel movie) {
        this.history = history;
        this.movie = movie;
    }

    public HistoryModel getHistory() {
        return history;
    }

    public MovieDetailModel getMovie() {
        return movie;
    }
}
