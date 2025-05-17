package com.example.appxemphim.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.DTO.EpisodeInfoDTO;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Network.RetrofitClient;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.Network.ShowTimeApiService;
import com.example.appxemphim.Request.ShowTimeRequest;
import com.example.appxemphim.Response.ShowTimeResponse;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowTimeRepository {
    private ShowTimeApiService showTimeApiService;

    public ShowTimeRepository(){
        this.showTimeApiService= RetrofitClient.getInstance().getShowTimeApiService();
    }

    public void fetchShowTime(MutableLiveData<Resource<List<EpisodeInfoDTO>>> liveData){
        liveData.setValue(Resource.loading());
        Call<ShowTimeResponse> call = showTimeApiService.getShowTimes(-1,0,10);
        call.enqueue(new Callback<ShowTimeResponse>() {
            @Override
            public void onResponse(Call<ShowTimeResponse> call, Response<ShowTimeResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    liveData.postValue(Resource.success(response.body().getContent()));
                } else {
                    liveData.postValue(Resource.error("Không thể tải dữ liệu lịch chiếu "));
                }
            }

            @Override
            public void onFailure(Call<ShowTimeResponse> call, Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
                Log.d("chien", "onFailure: "+t.getMessage());
            }
        });
    }

    public void fetchShowTimeByDay(MutableLiveData<Resource<List<EpisodeInfoDTO>>> liveData,int day){
        liveData.setValue(Resource.loading());
        Call<ShowTimeResponse> call = showTimeApiService.getShowTimes(day,0,10);
        call.enqueue(new Callback<ShowTimeResponse>() {
            @Override
            public void onResponse(Call<ShowTimeResponse> call, Response<ShowTimeResponse> response) {
                if (response.isSuccessful()&&response.body()!=null){
                    liveData.postValue(Resource.success(response.body().getContent()));
                } else {
                    liveData.postValue(Resource.error("Không thể tải dữ liệu lịch chiếu "));
                }
            }

            @Override
            public void onFailure(Call<ShowTimeResponse> call, Throwable t) {
                liveData.postValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    public void createShowTime(ShowTimeRequest request) {

        Call<ResponseBody> call = showTimeApiService.createShowTime(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("tạo thành công lịch chiếu với req: "+request );
                } else {
                    System.out.println("tạo thất bại. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Lỗi mạng khi tạo lịch chiếu: " + t.getMessage());
            }
        });
    }


    public void updateShowTime(ShowTimeRequest request) {

        Call<ResponseBody> call = showTimeApiService.updateShowTime(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("update thành công lịch chiếu với req: "+request );
                } else {
                    System.out.println("update thất bại. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Lỗi mạng khi update lịch chiếu: " + t.getMessage());
            }
        });
    }


    public void deleteShowTime(String movieId) {
        Call<ResponseBody> call = showTimeApiService.deleteShowTime(movieId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    System.out.println("Xoá thành công lịch chiếu với ID: " + movieId);
                } else {
                    System.out.println("Xoá thất bại. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Lỗi mạng khi xoá lịch chiếu: " + t.getMessage());
            }
        });
    }

}
