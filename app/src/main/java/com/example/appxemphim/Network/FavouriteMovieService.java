package com.example.appxemphim.Network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FavouriteMovieService {
    @POST("favourite/create/{video_id}")
    Call<ResponseBody> addMovieInFavourite(@Path("video_id") String video_id);
}
