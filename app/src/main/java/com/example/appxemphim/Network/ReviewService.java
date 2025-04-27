package com.example.appxemphim.Network;

import com.example.appxemphim.Request.ReviewRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewService {
    @POST("review/create")
    Call<ResponseBody> addReview( @Body ReviewRequest reviewRequest);

    @GET("review/get/{movie_id}")
    Call<ResponseBody> getReview( @Path("movie_id") String movie_id);
}
