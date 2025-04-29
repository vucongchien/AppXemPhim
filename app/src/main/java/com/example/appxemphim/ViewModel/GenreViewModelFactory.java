package com.example.appxemphim.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appxemphim.Repository.GenreRepository;

public class GenreViewModelFactory implements ViewModelProvider.Factory {
    private final GenreRepository genreRepository;
    private GenreViewModel genreViewModel;

    public GenreViewModelFactory(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GenreViewModel.class)) {
            return (T) new GenreViewModel(genreRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
