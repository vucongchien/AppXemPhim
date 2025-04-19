package com.example.appxemphim.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:8081/";
    private static RetrofitClient instance;
    private final Retrofit retrofit;

    private RetrofitClient() {
        // Cấu hình OkHttpClient với timeout
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // timeout khi kết nối
                .readTimeout(30, TimeUnit.SECONDS)     // timeout khi đọc dữ liệu
                .writeTimeout(30, TimeUnit.SECONDS)    // timeout khi ghi dữ liệu
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient) // Gán client với timeout vào Retrofit
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public MovieApiService getApiService() {
        return retrofit.create(MovieApiService.class);
    }
}
