package com.example.appxemphim.Network;

import com.example.appxemphim.Model.GenreModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GenreApiService {
    @GET("/genres/getAll")
    Call<List<GenreModel>> getAllGenre();



}
