package com.example.appxemphim.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.HistoryModel;
import com.example.appxemphim.Model.HistoryWithMovie;
import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Network.HistoryService;
import com.example.appxemphim.Network.MovieApiService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.Request.HistoryRequest;
import com.example.appxemphim.UI.Utils.Resource;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryRepository {
    private static final Logger log = LoggerFactory.getLogger(HistoryRepository.class);
    private HistoryService historyService;
    private MovieApiService movieService;  // interface bạn đã có sẵn cho movie

    public HistoryRepository(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("LocalStore", MODE_PRIVATE);
        String token = sharedPref.getString("Token", "");
        this.historyService = RetrofitInstance.createService(HistoryService.class);
        this.movieService = RetrofitInstance.createService(MovieApiService.class);
    }
    public void addHistory(HistoryRequest historyRequest, MutableLiveData<Resource<String>> liveData) {
        liveData.setValue(Resource.loading());
        historyService.createHistory(historyRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        liveData.setValue(Resource.success(response.body().string()));
                    } else {
                        String errorMessage = "Thêm thất bại";
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                        liveData.setValue(Resource.error(errorMessage));
                    }
                } catch (Exception e) {
                    liveData.setValue(Resource.error("Lỗi đọc dữ liệu"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });

    }
        public void getHistoryWithMovies(MutableLiveData<Resource<List<HistoryWithMovie>>> liveData) {
        liveData.setValue(Resource.loading());

        // Bước 1: Lấy danh sách lịch sử
        historyService.getAllHistory().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("historyList" ,"historyList failed: " +response);
                try {

                    if (response.isSuccessful() && response.body() != null) {
                        String jsonString = response.body().string();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<HistoryModel>>() {}.getType();
                        List<HistoryModel> historyList = gson.fromJson(jsonString, listType);
                        Log.e("historyList" ,"historyList failed: " +historyList);

                        if (historyList.isEmpty()) {
                            liveData.setValue(Resource.success(new ArrayList<>()));
                            return;
                        }

                        // Bước 2: Lấy chi tiết phim cho từng video_id
                        List<HistoryWithMovie> resultList = new ArrayList<>();
                        final int total = historyList.size();
                        final int[] count = {0};
                        final boolean[] errorOccurred = {false};

                        for (HistoryModel history : historyList) {
                            movieService.getMovieById(history.getVideo_id().trim()).enqueue(new Callback<MovieDetailModel>() {
                                @Override
                                public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                                    Log.e("movieService" ,"movieService failed: " +response);
                                    count[0]++;
                                    if (response.isSuccessful() && response.body() != null) {
                                        HistoryWithMovie hwm = new HistoryWithMovie(history, response.body());
                                        resultList.add(hwm);
                                        Log.e("resultList" ,"resultList failed: " +resultList);
                                    } else {
                                        errorOccurred[0] = true;
                                    }
                                    if (count[0] == total) {
                                        if (!errorOccurred[0]) {
                                            liveData.setValue(Resource.success(resultList));
                                        } else {
                                            liveData.setValue(Resource.error("Có lỗi khi lấy chi tiết phim"));
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MovieDetailModel> call, Throwable t) {
                                    count[0]++;
                                    errorOccurred[0] = true;
                                    if (count[0] == total) {
                                        liveData.setValue(Resource.error("Lỗi mạng khi lấy chi tiết phim"));
                                    }
                                }
                            });
                        }
                    } else {
                        Log.e("Lấy lịch sử thất bại" ,"Lấy lịch sử thất bại failed: " +response);
                        liveData.setValue(Resource.error("Lấy lịch sử thất bại"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    liveData.setValue(Resource.error("Lỗi đọc dữ liệu lịch sử"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }
}
