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
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _forYou = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _only = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _carousels = new MutableLiveData<>();
    public LiveData<Resource<List<MovieOverviewModel>>> popular = _popular;
    public LiveData<Resource<List<MovieOverviewModel>>> forYou = _forYou;
    public LiveData<Resource<List<MovieOverviewModel>>> only = _only;
    public LiveData<Resource<List<MovieOverviewModel>>> carousels = _carousels;

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
    public void loadDataForYou() {
        _forYou.setValue(Resource.loading());
        repository.searchMovies(
                null,
                null,
                null,
                null,
                null,
                0,
                10,
                _forYou
        );
    }
    public void loadDataOnly() {
        _only.setValue(Resource.loading());
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
    public void loadDataCarousels(List<String>genres,List<Integer>years){
        _carousels.setValue(Resource.loading());
        repository.searchMovies(
                null,
                genres,
                years,
                null,
                null,
                0,
                10,
                _carousels
        );
    }
}
