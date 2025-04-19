package com.example.appxemphim.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiMovieService {
    @GET("/api/v1/movies")
    Call<ResponseBody> getmovie();
}
