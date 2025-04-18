package com.example.appxemphim.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

import javax.inject.Inject;

public class MovieSearchViewModel extends ViewModel {
    private final MovieRepository repository;
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _result = new MutableLiveData<>();
    public LiveData<Resource<List<MovieOverviewModel>>> result = _result;

    @Inject
    public MovieSearchViewModel(MovieRepository repo) {
        this.repository = repo;
    }

    public void loadDataSearch(@Nullable String title,
                       @Nullable List<String> genres,
                       @Nullable List<Integer> years,
                       @Nullable List<String> nations,
                       @Nullable Double minRating, int page,
                       int size) {
        _result.setValue(Resource.loading());
        repository.searchMovies(title,genres,years,nations,minRating,page,size, _result);
    }
}
