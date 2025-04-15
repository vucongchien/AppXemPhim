package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {
    private final MovieRepository repository = new MovieRepository();
    private final MutableLiveData<Resource<List<MovieUIModel>>> hotMovieList = new MutableLiveData<>(Resource.loading());
    private final MutableLiveData<Resource<List<MovieUIModel>>> topRatedMovieList = new MutableLiveData<>(Resource.loading());
    private final MutableLiveData<Resource<List<MovieUIModel>>> allMovies = new MutableLiveData<>(Resource.loading());

    public LiveData<Resource<List<MovieUIModel>>> getTopRatedMovieList() {
        return topRatedMovieList;
    }

    public LiveData<Resource<List<MovieUIModel>>> getHotMovieList() {
        return hotMovieList;
    }

    public LiveData<Resource<List<MovieUIModel>>> getAllMovies() {
        return allMovies;
    }
    public void loadHotData() {
        repository.fetchHotMovies(hotMovieList);
    }

    public void loadTopRatedData() {
        repository.fetchHotMovies(hotMovieList);
    }

    public void loadAllMovies() {
        allMovies.setValue(Resource.loading());
        try {
            List<MovieUIModel> dummyData = new ArrayList<>();
            dummyData.add(new MovieUIModel(1, "Dune: Part Two", "https://i.pinimg.com/736x/02/52/f0/0252f0c9ad53ee8256855bc11c681c52.jpg", "8.9", "1000", "2024", " - 3h00"));
            dummyData.add(new MovieUIModel(2, "Oppenheimer", "https://i.pinimg.com/736x/02/52/f0/0252f0c9ad53ee8256855bc11c681c52.jpg", "9.0", "1000", "2023 ", "- 3h00"));
            dummyData.add(new MovieUIModel(3, "Oppenheimer", "https://i.pinimg.com/736x/02/52/f0/0252f0c9ad53ee8256855bc11c681c52.jpg", "9.0", "1000", "2023 ", "- 3h00"));
            dummyData.add(new MovieUIModel(4, "Oppenheimer", "https://i.pinimg.com/736x/02/52/f0/0252f0c9ad53ee8256855bc11c681c52.jpg", "9.0", "1000", "2023 ", "- 3h00"));
            dummyData.add(new MovieUIModel(5, "Oppenheimer", "https://i.pinimg.com/736x/02/52/f0/0252f0c9ad53ee8256855bc11c681c52.jpg", "9.0", "1000", "2023 ", "- 3h00"));
            dummyData.add(new MovieUIModel(6, "Oppenheimer", "https://i.pinimg.com/736x/02/52/f0/0252f0c9ad53ee8256855bc11c681c52.jpg", "9.0", "1000", "2023 ", "- 3h00"));

            allMovies.postValue(Resource.success(dummyData));
        } catch (Exception e) {
            allMovies.postValue(Resource.error("Failed to load movies: " + e.getMessage()));
        }
    }
}