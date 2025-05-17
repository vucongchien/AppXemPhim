package com.example.appxemphim.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appxemphim.Repository.ShowTimeRepository;

public class ShowtimeViewModelFactory implements ViewModelProvider.Factory {
    private final ShowTimeRepository repository;

    public ShowtimeViewModelFactory(ShowTimeRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShowtimeViewModel.class)) {
            return (T) new ShowtimeViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
