package com.example.appxemphim.Network;

import com.example.appxemphim.Model.MovieUIModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("movies/findAll") // <-- Thay bằng endpoint thực tế của bạn
    Call<List<MovieUIModel>> getHotMovies();

    @GET("top-rated")
    Call<List<MovieUIModel>> getTopRatedMovies();

    @GET("all")
    Call<List<MovieUIModel>> getAllMovies();

}