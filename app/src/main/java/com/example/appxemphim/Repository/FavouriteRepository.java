package com.example.appxemphim.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Network.FavouriteMovieService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.UI.Utils.Resource;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRepository {
    private  final FavouriteMovieService favouriteMovieService;
    public FavouriteRepository(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("LocalStore", MODE_PRIVATE);
        String ma = sharedPref.getString("Token","");
        this.favouriteMovieService = RetrofitInstance.createService(FavouriteMovieService.class,ma);
    }

    public  void addMovieInFavourite(String movie_id, MutableLiveData<Resource<String>> liveData){
        liveData.setValue(Resource.loading());
        favouriteMovieService.addMovieInFavourite(movie_id).enqueue(new Callback<ResponseBody>() {
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
}
