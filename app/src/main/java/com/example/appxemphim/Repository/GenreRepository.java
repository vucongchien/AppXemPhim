package com.example.appxemphim.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.GenreModel;
import com.example.appxemphim.Network.GenreApiService;
import com.example.appxemphim.Network.RetrofitClient;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreRepository {
    private final GenreApiService apiService;

    public GenreRepository() {
        this.apiService = RetrofitClient.getInstance().getGenreApiService();;
    }

    public void fetchGenres(MutableLiveData<Resource<List<GenreModel>>> liveData) {
        liveData.setValue(Resource.loading());

        Call<List<GenreModel>> call = apiService.getAllGenre();

        call.enqueue(new Callback<List<GenreModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GenreModel>> call, @NonNull Response<List<GenreModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(Resource.success(response.body()));
                } else {
                    liveData.postValue(Resource.error("Không thể tải dữ liệu thể loại"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GenreModel>> call, @NonNull Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

}
