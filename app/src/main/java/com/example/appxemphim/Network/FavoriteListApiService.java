package com.example.appxemphim.Network;

import com.example.appxemphim.Model.DTO.FavoriteDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavoriteListApiService {
    @POST("favourite/create/{video_id}")
    Call<ResponseBody> addMovieInFavourite(@Path("video_id") String video_id);

    @GET("favourite/getAll")
    Call<List<FavoriteDTO>> getFavoriteList();
}
