package com.example.appxemphim.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Repository.FavouriteRepository;

public class FavoriteViewModelFactory implements ViewModelProvider.Factory {
    private final FavouriteRepository favoriteRepository;
    private FavouriteViewModel favoriteViewModel;

    public FavoriteViewModelFactory(FavouriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavouriteViewModel.class)) {
            return (T) new FavouriteViewModel(favoriteRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
