package com.example.appxemphim.Network;

import com.example.appxemphim.Request.RepassRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiLoginRegisterService {

    @POST("auth/login/{uid}")
    Call<ResponseBody> loginWithToken(@Path("uid") String uid);

    @GET("/email/{email}")
    Call<ResponseBody> ischeckEmail(@Path("email") String email);

    @POST("/email/senDTO/{email}")
    Call<ResponseBody> sentDTO(@Path("email")String email);

    @PATCH("/auth/repass")
    Call<ResponseBody> repass(@Body RepassRequest repassRequest);

}