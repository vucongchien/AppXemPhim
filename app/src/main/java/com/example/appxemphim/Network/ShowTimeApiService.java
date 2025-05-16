package com.example.appxemphim.Network;

import com.example.appxemphim.Model.DTO.FavoriteDTO;
import com.example.appxemphim.Request.ShowTimeRequest;
import com.example.appxemphim.Response.ShowTimeResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShowTimeApiService {
    @POST("api/v1/showtimes")
    Call<ResponseBody> createShowTime(ShowTimeRequest showTimeRequest);

    @GET("api/v1/showtimes")
    Call<ShowTimeResponse> getShowTimes(
            @Query("day") int day,
            @Query("page") int page,
            @Query("size") int size
    );

    @POST("api/v1/showtimes/update")
    Call<ResponseBody> updateShowTime(ShowTimeRequest showTimeRequest);

    @DELETE("api/v1/showtimes/delete/{movieId}")
    Call<ResponseBody> deleteShowTime(@Path("movieId") String movieId);


}
