package com.example.appxemphim.Network;

import com.example.appxemphim.Request.HistoryRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HistoryService {

    @POST("history/create")
    Call<ResponseBody> createHistory(@Body HistoryRequest historyRequest);

    @GET("history/getAll")
    Call<ResponseBody> getAllHistory();
}
