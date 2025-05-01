package com.example.appxemphim.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.Network.ReviewService;
import com.example.appxemphim.Request.ReviewRequest;
import com.example.appxemphim.UI.Utils.Resource;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewRepositytory {
    private ReviewService reviewService;

    public ReviewRepositytory(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("LocalStore", MODE_PRIVATE);
        String ma = sharedPref.getString("Token","");
        this.reviewService = RetrofitInstance.createService(ReviewService.class,ma);
    }

    public  void addReview(ReviewRequest reviewRequest, MutableLiveData<Resource<String>> liveData){
        liveData.setValue(Resource.loading());
        reviewService.addReview(reviewRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful() && response.body() != null){
                        liveData.setValue(Resource.success(response.body().string()));
                    }else{
                        String errorMessage = "Thêm thất bại";
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            errorMessage = errorBody; // Hoặc bạn có thể parse JSON nếu response là JSON
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
    public void getReview(String movie_Id,MutableLiveData<Resource<List<ReviewModel>>> liveData){
        liveData.setValue(Resource.loading());
        reviewService.getReview(movie_Id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String jsonString = response.body().string(); // Đọc toàn bộ response thành chuỗi JSON
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ReviewModel>>() {}.getType();
                        List<ReviewModel> reviews = gson.fromJson(jsonString, listType); // Parse JSON thành List<ReviewModel>
                        liveData.setValue(Resource.success(reviews));
                    } else {
                        String errorMessage = "Thêm thất bại";
                        if (response.errorBody() != null) {
                            String errorBody = response.errorBody().string();
                            errorMessage = errorBody; // Hoặc parse JSON nếu cần
                        }
                        liveData.setValue(Resource.error(errorMessage));
                    }
                } catch (Exception e) {
                    liveData.setValue(Resource.error("Lỗi đọc dữ liệu"));
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }
}
