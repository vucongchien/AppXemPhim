package com.example.appxemphim.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.ArrayList;
import java.util.List;




public class MovieViewModel extends ViewModel {
    private final MovieRepository repository=new MovieRepository();

    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _hotMovies = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _topRatedMovies = new MutableLiveData<>();
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _allMovies = new MutableLiveData<>();

    public LiveData<Resource<List<MovieOverviewModel>>> hotMovies = _hotMovies;
    public LiveData<Resource<List<MovieOverviewModel>>> topRatedMovies = _topRatedMovies;
    public LiveData<Resource<List<MovieOverviewModel>>> allMovies = _allMovies;

    public MovieViewModel() {

    }

    public void loadHotMovies() {
        _hotMovies.setValue(Resource.loading());
        repository.fetchHotMovies(_hotMovies);
    }

    public void loadTopRatedMovies() {
        _topRatedMovies.setValue(Resource.loading());
        repository.fetchTopRatedMovies(_topRatedMovies);
    }

    public void loadAllMovies() {
        _allMovies.setValue(Resource.loading());
        repository.fetchAllMovies(_allMovies);
    }

}