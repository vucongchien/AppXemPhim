package com.example.appxemphim.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {

    public void fetchHotMovies(MutableLiveData<Resource<List<MovieUIModel>>> liveData) {
        liveData.setValue(Resource.loading());

        RetrofitInstance.getApiService().getHotMovies().enqueue(new Callback<List<MovieUIModel>>() {
            @Override
            public void onResponse(Call<List<MovieUIModel>> call, Response<List<MovieUIModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(Resource.success(response.body()));
                } else {
                    liveData.setValue(Resource.error("No data available"));
                }
            }

            @Override
            public void onFailure(Call<List<MovieUIModel>> call, Throwable t) {
                liveData.setValue(Resource.error("Error: " + t.getMessage()));
            }
        });
    }

    // Bạn có thể thêm fetchTopRatedMovies(), fetchAllMovies() tương tự ở đây
}