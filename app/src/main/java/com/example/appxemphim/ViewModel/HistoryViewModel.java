package com.example.appxemphim.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.HistoryModel;
import com.example.appxemphim.Model.HistoryWithMovie;
import com.example.appxemphim.Repository.HistoryRepository;
import com.example.appxemphim.Request.HistoryRequest;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel {
    private final HistoryRepository historyRepository;

    public MutableLiveData<Resource<List<HistoryWithMovie>>> historyWithMovieLiveData = new MutableLiveData<>();
    public MutableLiveData<Resource<String>> addHistoryLiveData = new MutableLiveData<>();

    public MutableLiveData<Resource<List<HistoryModel>>> historyByVideoIdLiveData = new MutableLiveData<>();


    public HistoryViewModel(@NonNull Application application) {
        super(application);
        historyRepository = new HistoryRepository(application.getApplicationContext());
    }

    public void addHistory(HistoryRequest request) {
        historyRepository.addHistory(request, addHistoryLiveData);
    }

    public void loadHistoryWithMovies() {
        historyRepository.getHistoryWithMovies(historyWithMovieLiveData);
    }

    public MutableLiveData<Resource<List<HistoryModel>>> getHistoryByVideoId(String videoId) {
        MutableLiveData<Resource<List<HistoryModel>>> historyLiveData = new MutableLiveData<>();
        historyRepository.getHistoryByVideoId(videoId, historyLiveData);
        return historyLiveData;
    }

}
