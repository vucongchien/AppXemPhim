package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;

public class MovieDetailViewModel extends ViewModel {
    private final MovieRepository repository ;
    private final MutableLiveData<Resource<MovieDetailModel>> _movieDetail=new MutableLiveData<>();
    public LiveData<Resource<MovieDetailModel>> movieDetail=_movieDetail;

    public MovieDetailViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    public void loadMovieDetail(String id) {
        _movieDetail.setValue(Resource.loading());
        repository.fetchDetailMovieById(id,_movieDetail);
    }

}
