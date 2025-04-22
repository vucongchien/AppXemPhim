package com.example.appxemphim.Network;

import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Response.MovieOverviewResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {
    @GET("api/v1/movies")
    Call<MovieOverviewResponse> getMovies(
            @Query("title") String title,
            @Query("genres") List<String> genres,
            @Query("years") List<Integer> years,
            @Query("nations") List<String> nations,
            @Query("minRating") Double minRating,
            @Query("page") int page,
            @Query("size") int size
    );

    // Lấy chi tiết phim theo ID
    @GET("/api/v1/movies/{id}")
    Call<MovieDetailModel> getMovieById(@Path("id") String movieId);


    // Lấy chi tiết dạng phụ (ví dụ: diễn viên, đánh giá, v.v.)
    @GET("/api/v1/movies/{id}/{detailType}")
    Call<ResponseBody> getMovieDetails(
            @Path("id") String movieId,
            @Path("detailType") String detailType
    );
}
