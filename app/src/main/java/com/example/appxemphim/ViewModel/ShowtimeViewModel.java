package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.DTO.EpisodeInfoDTO;
import com.example.appxemphim.Repository.ShowTimeRepository;
import com.example.appxemphim.Request.ShowTimeRequest;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowtimeViewModel extends ViewModel {
    private ShowTimeRepository repository;
    private MutableLiveData<Resource<List<EpisodeInfoDTO>>> _showTimesLiveData = new MutableLiveData<>();
    private final Map<Integer, MutableLiveData<Resource<List<EpisodeInfoDTO>>>> _weekdayShowtimes = new HashMap<>();
    public LiveData<Resource<List<EpisodeInfoDTO>>> showTimesLiveData = _showTimesLiveData;



    public ShowtimeViewModel(ShowTimeRepository showTimeRepository){
        this.repository = showTimeRepository;
    }

    public LiveData<Resource<List<EpisodeInfoDTO>>> getShowTimesByWeekday(int weekday) {
        if (weekday < 0 || weekday > 6) {
            throw new IllegalArgumentException("Weekday must be between 0 (Monday) and 6 (Sunday)");
        }

        if (!_weekdayShowtimes.containsKey(weekday)) {
            MutableLiveData<Resource<List<EpisodeInfoDTO>>> liveData = new MutableLiveData<>();
            _weekdayShowtimes.put(weekday, liveData);
            repository.fetchShowTimeByDay(liveData, weekday); // Gọi repo fetch dữ liệu
        }

        return _weekdayShowtimes.get(weekday);
    }

    public void fetchAllShowTimes() {
        repository.fetchShowTime(_showTimesLiveData);
    }
}
