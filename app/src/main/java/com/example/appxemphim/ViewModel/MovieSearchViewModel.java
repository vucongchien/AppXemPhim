package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class MovieSearchViewModel extends ViewModel {

    private final MovieRepository repository;
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _result = new MutableLiveData<>();
    public LiveData<Resource<List<MovieOverviewModel>>> result = _result;

    public MovieSearchViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    /**
     * Search movies with filters; updates `result` LiveData
     */
    public void loadDataSearch(
            String title,
            List<String> genres,
            List<Integer> years,
            List<String> nations,
            Double minRating,
            int page,
            int size) {
        _result.setValue(Resource.loading());
        repository.searchMovies(
                title,
                genres,
                years,
                nations,
                minRating,
                page,
                size,
                _result
        );
    }
}
