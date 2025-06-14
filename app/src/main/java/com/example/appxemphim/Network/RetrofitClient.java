package com.example.appxemphim.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.41.25:8081/";
    private static RetrofitClient instance;
    private final Retrofit retrofit;

    private RetrofitClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)// Gán client với timeout vào Retrofit
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public MovieApiService getMovieApiService() {
        return retrofit.create(MovieApiService.class);
    }

    public GenreApiService getGenreApiService(){return  retrofit.create(GenreApiService.class);}

    public ShowTimeApiService getShowTimeApiService(){return retrofit.create(ShowTimeApiService.class);}

    // Thêm 2 service mới:
    public HistoryService getHistoryService() {
        return retrofit.create(HistoryService.class);
    }

    public ReviewService getReviewService() {
        return retrofit.create(ReviewService.class);
    }


}
