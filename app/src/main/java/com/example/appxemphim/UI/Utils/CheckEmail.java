package com.example.appxemphim.UI.Utils;

import android.content.Context;
import android.widget.Toast;

import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckEmail {
    public interface EmailCheckCallback {
        void onResult(boolean exists);
    }
    public static void checkEmailFromApi(String email, Context context, EmailCheckCallback callback) {
        ApiLoginRegisterService apiService = RetrofitInstance.getApiService();

        Call<ResponseBody> call = apiService.ischeckEmail(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                boolean exists = false;
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String result = response.body().string().trim();
                        exists = result.equalsIgnoreCase("true");
                        callback.onResult(exists);
                        //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onResult(false);
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    callback.onResult(false);
                    Toast.makeText(context, "Lỗi kiểm tra email", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onResult(false);
                Toast.makeText(context, "Không thể kết nối máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
