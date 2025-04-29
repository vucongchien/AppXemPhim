package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.GenreModel;
import com.example.appxemphim.Repository.GenreRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class GenreViewModel extends ViewModel {
    private final GenreRepository repository ;

    public GenreViewModel(GenreRepository repository) {
        this.repository = repository;
    }

    private final MutableLiveData<Resource<List<GenreModel>>> _genre =new MutableLiveData<>();
    public LiveData<Resource<List<GenreModel>>> genre = _genre;

    public void loadGenre() {
        _genre.setValue(Resource.loading());
        repository.fetchGenres(_genre);
    }
}
