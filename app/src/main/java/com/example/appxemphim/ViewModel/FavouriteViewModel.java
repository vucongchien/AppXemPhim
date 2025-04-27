package com.example.appxemphim.ViewModel;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Repository.FavouriteRepository;
import com.example.appxemphim.UI.Utils.Resource;

public class FavouriteViewModel extends ViewModel {
    private  final FavouriteRepository repository;
    public FavouriteViewModel(Context context){
        repository = new FavouriteRepository(context);
    }

    private final MutableLiveData<Resource<String>> _addmovieinfavourite = new MutableLiveData<>();

    public LiveData<Resource<String>> addmovieinfavourite = _addmovieinfavourite;

    public  void addfavourite(String movie_id){
        repository.addMovieInFavourite(movie_id,_addmovieinfavourite);
    }
}
