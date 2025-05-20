package com.example.appxemphim.ViewModel;

import androidx.lifecycle.Transformations;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.FilterData;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchViewModel extends ViewModel {

    private final MovieRepository repository;
    private final MutableLiveData<Resource<List<MovieOverviewModel>>> _result = new MutableLiveData<>();
    public LiveData<Resource<List<MovieOverviewModel>>> result = _result;

    private final MutableLiveData<String> _query     = new MutableLiveData<>();
    private final MutableLiveData<List<String>> _genres    = new MutableLiveData<>();
    private final MutableLiveData<List<String>> _years     = new MutableLiveData<>();
    private final MutableLiveData<List<String>> _nations   = new MutableLiveData<>();
    private final MutableLiveData<Double> _minRating       = new MutableLiveData<>();

    private final MediatorLiveData<FilterData> filterLiveData = new MediatorLiveData<>();
    public LiveData<FilterData> getFilterLiveData() {
        return filterLiveData;
    }



    public MovieSearchViewModel(MovieRepository repository) {
        this.repository = repository;

        // Gộp các nguồn dữ liệu lại
        filterLiveData.addSource(_query, v -> updateFilter());
        filterLiveData.addSource(_genres, v -> updateFilter());
        filterLiveData.addSource(_years, v -> updateFilter());
        filterLiveData.addSource(_nations, v -> updateFilter());
        filterLiveData.addSource(_minRating, v -> updateFilter());

    }

    private void updateFilter() {
        FilterData data = new FilterData(
                _query.getValue(),
                _genres.getValue(),
                _years.getValue(),
                _nations.getValue(),
                _minRating.getValue()
        );
        filterLiveData.setValue(data);

    }


    /**
     * Search movies with filters; updates `result` LiveData
     */
    public void loadDataSearch(
            String title,
            List<String> genres,
            List<Integer> years,
            List<String> nations,
            Double minRating,
            int page,
            int size) {
        _result.setValue(Resource.loading());
        repository.searchMovies(
                title,
                genres,
                years,
                nations,
                minRating,
                page,
                size,
                _result
        );
    }
    public void loadDataSearchWithFilter(FilterData filter) {
        _result.setValue(Resource.loading());
        repository.searchMovies(
                filter.getQuery(),
                filter.getGenres(),
                convertYears(filter.getYears()),
                filter.getNations(),
                filter.getMinRating(),
                0,
                20,
                _result
        );
    }


    // Getter để observe từ Activity/Fragment
    public LiveData<String> getQuery() {
        return _query;
    }
    public LiveData<List<String>> getGenres() {
        return _genres;
    }
    public LiveData<List<String>> getYears() {
        return _years;
    }
    public LiveData<List<String>> getNations() {
        return _nations;
    }
    public LiveData<Double> getMinRating() {
        return _minRating;
    }

    // Setter được dùng khi cập nhật filter từ UI
    public void setQuery(String query) {
        _query.setValue(query);
    }
    public void setGenres(List<String> genres) {
        _genres.setValue(genres);
    }
    public void setYears(List<String> years) {
        _years.setValue(years);
    }
    public void setNations(List<String> nations) {
        _nations.setValue(nations);
    }
    public void setMinRating(Double rating) {
        _minRating.setValue(rating);
    }

    private List<Integer> convertYears(List<String> yearStrings) {
        List<Integer> years = new ArrayList<>();
        if (yearStrings == null) {
            return years;
        }


        for (String s : yearStrings) {
            try {
                years.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }
        return years;
    }


}
