package com.example.appxemphim.Network;

import com.example.appxemphim.Request.HistoryRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HistoryService {

    @POST("history/create")
    Call<ResponseBody> createHistory(@Body HistoryRequest historyRequest);

    @GET("history/getAll")
    Call<ResponseBody> getAllHistory();

    @GET("history/by-video/{video_id}")
    Call<ResponseBody> getHistoryByVdId(@Path("video_id") String video_id);
}
