package com.example.appxemphim.Repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.DTO.FavoriteDTO;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.Network.FavoriteListApiService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.Utilities.SessionManager;

import java.util.List;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRepository {
    private  final FavoriteListApiService favoriteListApiService;
    public FavouriteRepository(Context context){

        String ma = SessionManager.getToken(context);
        Log.d("favorite", "token:  "+ma);
        this.favoriteListApiService = RetrofitInstance.createService(FavoriteListApiService.class,ma);
    }

    public  void addMovieInFavourite(String movie_id, MutableLiveData<Resource<String>> liveData){
        liveData.setValue(Resource.loading());
        favoriteListApiService.addMovieInFavourite(movie_id).enqueue(new Callback<ResponseBody>() {
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

    public void fetchFavoriteList(MutableLiveData<Resource<List<MovieOverviewModel>>> liveData) {
        Log.d("favorite", "fetchFavoriteList: ");
        liveData.setValue(Resource.loading());

        favoriteListApiService.getFavoriteList().enqueue(new Callback<List<FavoriteDTO>>() {
            @Override
            public void onResponse(Call<List<FavoriteDTO>> call, Response<List<FavoriteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieOverviewModel> movies = response.body().stream()
                            // lấy mỗi movie
                            .map(FavoriteDTO::getMovie)
                            .collect(Collectors.toList());

                    liveData.setValue(Resource.success(movies));
                    Log.d("favorite", "fetchFavoriteList size:  "+movies.size());
                } else {
                    liveData.setValue(Resource.error("Lỗi phản hồi từ server"));
                    Log.d("favorite", "fetchFavoriteList size:  "+"looix");
                }
            }

            @Override
            public void onFailure(Call<List<FavoriteDTO>> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }



}
