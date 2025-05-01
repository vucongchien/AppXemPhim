package com.example.appxemphim.Repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Network.MovieApiService;
import com.example.appxemphim.Network.RetrofitClient;
import com.example.appxemphim.Response.MovieOverviewResponse;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    private final MovieApiService apiService;

    public MovieRepository() {
        this.apiService = RetrofitClient.getInstance().getMovieApiService();
    }

    public void fetchHotMovies(MutableLiveData<Resource<List<MovieOverviewModel>>> liveData) {
        liveData.setValue(Resource.loading());

        Call<MovieOverviewResponse> call = apiService.getMovies(
                null,       // title
                null,       // genres
                null,       // years
                null,       // nations
                0.0,        // minRating
                0,          // page
                10          // size
        );

        call.enqueue(new Callback<MovieOverviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieOverviewResponse> call, @NonNull Response<MovieOverviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Resource.success(response.body().getContent()));
                } else {
                    liveData.postValue(Resource.error("Không thể tải dữ liệu phim hot"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieOverviewResponse> call, Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    public void fetchTopRatedMovies(MutableLiveData<Resource<List<MovieOverviewModel>>> liveData) {
        liveData.setValue(Resource.loading());
        Call<MovieOverviewResponse> call = apiService.getMovies(
                null,       // title
                null,       // genres
                null,       // years
                null,       // nations
                0.0,        // minRating
                0,          // page
                10          // size
        );

        call.enqueue(new Callback<MovieOverviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieOverviewResponse> call, @NonNull Response<MovieOverviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Resource.success(response.body().getContent()));
                } else {
                    liveData.postValue(Resource.error("Không thể tải dữ liệu phim top rated"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieOverviewResponse> call, Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    public void fetchAllMovies(MutableLiveData<Resource<List<MovieOverviewModel>>> liveData) {
        liveData.setValue(Resource.loading());

        Call<MovieOverviewResponse> call = apiService.getMovies(
                null,       // title
                null,       // genres
                null,       // years
                null,       // nations
                0.0,        // minRating
                0,          // page
                20          // size
        );

        call.enqueue(new Callback<MovieOverviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieOverviewResponse> call, @NonNull Response<MovieOverviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Resource.success(response.body().getContent()));
                } else {
                    liveData.postValue(Resource.error("Không thể tải dữ liệu toàn bộ phim "));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieOverviewResponse> call, Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    public void fetchDetailMovieById(String id, MutableLiveData<Resource<MovieDetailModel>> liveData) {
        liveData.setValue(Resource.loading());

        Call<MovieDetailModel> call = apiService.getMovieById(id);

        call.enqueue(new Callback<MovieDetailModel>() {
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

    public void searchMovies(
            @Nullable String title,
            @Nullable List<String> genres,
            @Nullable List<Integer> years,
            @Nullable List<String> nations,
            @Nullable Double minRating,
            int page,
            int size,
            MutableLiveData<Resource<List<MovieOverviewModel>>> liveData
    ) {
        liveData.setValue(Resource.loading());

        Call<MovieOverviewResponse> call = apiService.getMovies(
                title,
                genres,
                years,
                nations,
                minRating,
                page,
                size
        );

        call.enqueue(new Callback<MovieOverviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieOverviewResponse> call, @NonNull Response<MovieOverviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Resource.success(response.body().getContent()));
                } else {
                    liveData.postValue(Resource.error("Không tìm thấy kết quả phù hợp"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieOverviewResponse> call, @NonNull Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

}
