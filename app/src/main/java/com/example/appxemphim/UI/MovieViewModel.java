package com.example.appxemphim.UI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private final MutableLiveData<Resource<List<MovieUIModel>>> movieList=new MutableLiveData<>(Resource.loading());
    public LiveData<Resource<List<MovieUIModel>>> getMovieList(){
        return movieList;
    }

    public void loadMovieData() {
        // Giả lập gọi API
        movieList.setValue(Resource.loading());
        try {
            List<MovieUIModel> dummyData = new ArrayList<>();
            dummyData.add(new MovieUIModel(1, "Dune: Part Two", "https://image.tmdb.org/t/p/w500/dune.jpg", "8.9", "2020    3h00"));
            dummyData.add(new MovieUIModel(2, "Oppenheimer", "https://image.tmdb.org/t/p/w500/oppenheimer.jpg", "9.0", "2020    3h00"));
            movieList.setValue(Resource.success(dummyData));
        } catch (Exception e) {
            movieList.setValue(Resource.error("Failed to load movies: " + e.getMessage()));
        }
    }
}
