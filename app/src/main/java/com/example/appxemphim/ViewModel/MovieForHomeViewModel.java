package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class MovieForHomeViewModel extends ViewModel {
    private final MovieRepository repository;
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _popular = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _retro = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _only = new MutableLiveData<>();
    public LiveData<Resource<List<MovieOverviewModel>>> popular = _popular;
    public LiveData<Resource<List<MovieOverviewModel>>> retro = _retro;
    public LiveData<Resource<List<MovieOverviewModel>>> only = _only;

    public MovieForHomeViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    /**
     * load movies with filters; updates `result` LiveData
     */
    public void loadDataPopular() {
        _popular.setValue(Resource.loading());
        repository.searchMovies(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                _popular
        );
    }
    public void loadDataRetro() {
        _popular.setValue(Resource.loading());
        repository.searchMovies(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                _retro
        );
    }
    public void loadDataOnly() {
        _popular.setValue(Resource.loading());
        repository.searchMovies(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                _only
        );
    }
}
