package com.example.appxemphim.Network;

import com.example.appxemphim.Request.CommentRequest;
import com.example.appxemphim.Request.ReviewRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CommentService {
    @POST("comment/create")
    Call<ResponseBody> addComment(@Body CommentRequest commentRequest);
}
