package com.example.appxemphim.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.MovieOverviewModel;

import java.util.ArrayList;
import java.util.List;

public class FilterManager {
    private String typeFilter;
    private String categoryFilter;
    private String yearFilter;
    private List<MovieOverviewModel> allMovies = new ArrayList<>();
    private final MutableLiveData<List<MovieOverviewModel>> filteredMovies = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<MovieOverviewModel>> getFilteredMovies() {
        return filteredMovies;
    }

    public void setAllMovies(List<MovieOverviewModel> movies) {
        this.allMovies = movies != null ? new ArrayList<>(movies) : new ArrayList<>();
        applyFilters();
    }

    public void updateTypeFilter(String type) {
        this.typeFilter = type;
        applyFilters();
    }

    public void updateCategoryFilter(String category) {
        this.categoryFilter = category;
        applyFilters();
    }

    public void updateYearFilter(String year) {
        this.yearFilter = year;
        applyFilters();
    }

    private void applyFilters() {
//        List<MovieOverviewModel> result = new ArrayList<>(allMovies);
//
//        // Lọc theo category
//        if (categoryFilter != null && !categoryFilter.isEmpty()) {
////            result.removeIf(movie -> !movie.getCategory().equalsIgnoreCase(categoryFilter));
//        }
//
//        // Lọc theo year
//        if (yearFilter != null && !yearFilter.equals("Tất cả")) {
//            result.removeIf(movie -> !movie.getYear().trim().equalsIgnoreCase(yearFilter.trim()));
//        }
//
//        filteredMovies.setValue(result);
    }

    public boolean isAnyFilterApplied() {
        return (typeFilter != null && !typeFilter.isEmpty()) ||
                (categoryFilter != null && !categoryFilter.isEmpty()) ||
                (yearFilter != null && !yearFilter.equals("Tất cả"));
    }
}
