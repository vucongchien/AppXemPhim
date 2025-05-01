package com.example.appxemphim.ViewModel;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Repository.FavouriteRepository;
import com.example.appxemphim.Repository.GenreRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class FavouriteViewModel extends ViewModel {
    private  final FavouriteRepository repository;

    public FavouriteViewModel(FavouriteRepository repository) {
        this.repository = repository;
    }


    private final MutableLiveData<Resource<String>> _addmovieinfavourite = new MutableLiveData<>();

    public LiveData<Resource<String>> addmovieinfavourite = _addmovieinfavourite;

    public  void addfavourite(String movie_id){
        repository.addMovieInFavourite(movie_id,_addmovieinfavourite);
    }

    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _favoriteList = new MutableLiveData<>();
    public LiveData<Resource<List<MovieOverviewModel>>> favoriteList=_favoriteList;
    public void loadData(){
        _favoriteList.setValue(Resource.loading());
        repository.fetchFavoriteList(_favoriteList);
    }




}

