package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.DTO.EpisodeInfoDTO;
import com.example.appxemphim.Repository.ShowTimeRepository;
import com.example.appxemphim.Request.ShowTimeRequest;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class ShowtimeViewModel extends ViewModel {
    private ShowTimeRepository repository;
    private MutableLiveData<Resource<List<EpisodeInfoDTO>>> _showTimesLiveData = new MutableLiveData<>();
    public LiveData<Resource<List<EpisodeInfoDTO>>> showTimesLiveData = _showTimesLiveData;



    public ShowtimeViewModel(ShowTimeRepository showTimeRepository){
        this.repository = showTimeRepository;
    }

    public LiveData<Resource<List<EpisodeInfoDTO>>> getShowTimesLiveData() {
        return showTimesLiveData;
    }

    public void fetchAllShowTimes() {
        repository.fetchShowTime(_showTimesLiveData);
    }

    public void fetchShowTimesByDay(int day) {
        repository.fetchShowTimeByDay(_showTimesLiveData, day);
    }

    public void createShowTime(ShowTimeRequest request) {
        repository.createShowTime(request);
        fetchAllShowTimes(); // gọi lại để cập nhật danh sách
    }

    public void updateShowTime(ShowTimeRequest request) {
        repository.updateShowTime(request);
        fetchAllShowTimes(); // gọi lại để cập nhật danh sách
    }

    public void deleteShowTime(String movieId) {
        repository.deleteShowTime(movieId);
        fetchAllShowTimes(); // gọi lại để cập nhật danh sách
    }
}
