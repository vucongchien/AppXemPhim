package com.example.appxemphim.Repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Network.MovieApiService;
import com.example.appxemphim.Network.RetrofitClient;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private final MovieApiService apiService;

    public MovieRepository() {
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    // --- Public methods ---

    public void fetchHotMovies(MutableLiveData<Resource<List<MovieOverviewModel>>> liveData) {
        fetchMovies(null, null, null, null, 0.0, liveData);
    }

    public void fetchTopRatedMovies(MutableLiveData<Resource<List<MovieOverviewModel>>> liveData) {
        fetchMovies(null, null, null, null, 0.0, liveData);
    }

    public void fetchAllMovies(MutableLiveData<Resource<List<MovieOverviewModel>>> liveData) {
        fetchMovies(null, null, null, null, 0.0, liveData);
    }

    public void searchMovies(
            @Nullable String title,
            @Nullable List<String> genres,
            @Nullable List<Integer> years,
            @Nullable List<String> nations,
            @Nullable Double minRating,
            MutableLiveData<Resource<List<MovieOverviewModel>>> liveData
    ) {
        fetchMovies(title, genres, years, nations, minRating, liveData);
    }

    public void fetchDetailMovieById(String id, MutableLiveData<Resource<MovieDetailModel>> liveData) {
        liveData.setValue(Resource.loading());

        apiService.getMovieById(id).enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetailModel> call, @NonNull Response<MovieDetailModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Resource.success(response.body()));
                } else {
                    liveData.postValue(Resource.error("Không thể tải chi tiết phim"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetailModel> call, @NonNull Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    // --- Private helper method ---

    private void fetchMovies(
            @Nullable String title,
            @Nullable List<String> genres,
            @Nullable List<Integer> years,
            @Nullable List<String> nations,
            @Nullable Double minRating,
            MutableLiveData<Resource<List<MovieOverviewModel>>> liveData
    ) {
        liveData.setValue(Resource.loading());

        Call<List<MovieOverviewModel>> call = apiService.getMovies(title, genres, years, nations, minRating);
        call.enqueue(new Callback<List<MovieOverviewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieOverviewModel>> call, @NonNull Response<List<MovieOverviewModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Resource.success(response.body())); // Không cần .getContent()
                } else {
                    liveData.postValue(Resource.error("Không thể tải danh sách phim"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MovieOverviewModel>> call, @NonNull Throwable t) {
                liveData.postValue(Resource.error("Lỗi: " + t.getMessage()));
            }
        });
    }
}
