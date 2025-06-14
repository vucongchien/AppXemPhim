package com.example.appxemphim.Network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static TokenManager tokenManager;

    public static void init(Context context) {
        tokenManager = new TokenManager(context);
    }
    private static final String BASE_URL = "http://192.168.41.25:8081/";

    // 1. Không cần static retrofit cố định khi dùng token độnxg
    private static Retrofit createRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new TokenAuthenticator(tokenManager))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String token = tokenManager.getAccessToken();
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit createRetrofitWithoutHeader() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // 2. Tạo API không cần token
    public static ApiLoginRegisterService getApiService() {
        return createRetrofitWithoutHeader().create(ApiLoginRegisterService.class);
    }

    // 3. Generic: Dùng được cho nhiều service interface khác
    public static <T> T createService(Class<T> serviceClass) {
        return createRetrofit().create(serviceClass);
    }
}
