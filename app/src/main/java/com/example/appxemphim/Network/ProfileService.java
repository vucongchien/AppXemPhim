package com.example.appxemphim.Network;

import com.example.appxemphim.Model.Profile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ProfileService {
    @Multipart
    @PATCH("profile/update")
    Call<ResponseBody> updateProfile(
            @Part("name") RequestBody name,
            @Part("gmail") RequestBody gmail,
            @Part MultipartBody.Part file
    );
    @GET("auth/information/{uid}")
    Call<Profile> getProfile(@Path("uid") String uid);
}
