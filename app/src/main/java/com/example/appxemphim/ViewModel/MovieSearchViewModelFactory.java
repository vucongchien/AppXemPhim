package com.example.appxemphim.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appxemphim.Repository.MovieRepository;

public class MovieSearchViewModelFactory implements ViewModelProvider.Factory {
    private final MovieRepository movieRepository;

    public MovieSearchViewModelFactory(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieSearchViewModel.class)) {
            return (T) new MovieSearchViewModel(movieRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
