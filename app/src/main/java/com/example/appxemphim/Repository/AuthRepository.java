// AuthRepository.java
package com.example.appxemphim.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.DTO.EmailDTO;
import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitClient;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.Request.RepassRequest;
import com.example.appxemphim.UI.Utils.Resource;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final ApiLoginRegisterService apiService;

    public AuthRepository(String token) {
        // Nếu cần token (cho API như reset mật khẩu)
        this.apiService = RetrofitInstance.createService(ApiLoginRegisterService.class, token);
    }

    public AuthRepository() {
        // Nếu không cần token (ví dụ: kiểm tra email)
        this.apiService = RetrofitInstance.getApiService();
    }

    public void loginWithToken(String token, MutableLiveData<Resource<String>> liveData) {
        liveData.setValue(Resource.loading());
        apiService.loginWithToken(token).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        liveData.setValue(Resource.success(response.body().string()));
                    } else {
                        liveData.setValue(Resource.error("Đăng nhập thất bại"));
                    }
                } catch (IOException e) {
                    liveData.setValue(Resource.error("Lỗi đọc dữ liệu"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    public void checkEmail(String email, MutableLiveData<Resource<String>> liveData) {
        liveData.setValue(Resource.loading());
        apiService.ischeckEmail(email).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        liveData.setValue(Resource.success(response.body().string()));
                    } else {
                        liveData.setValue(Resource.error("Không thể kiểm tra email"));
                    }
                } catch (IOException e) {
                    liveData.setValue(Resource.error("Lỗi đọc dữ liệu"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    public void sentDTO(String email, MutableLiveData<Resource<EmailDTO>> liveData) {
        liveData.setValue(Resource.loading());
        apiService.sentDTO(email).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String json = response.body().string();
                        EmailDTO dto = new Gson().fromJson(json, EmailDTO.class);
                        liveData.setValue(Resource.success(dto));
                    } else {
                        liveData.setValue(Resource.error("Không thể gửi mã xác nhận"));
                    }
                } catch (IOException e) {
                    liveData.setValue(Resource.error("Lỗi đọc dữ liệu"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                liveData.setValue(Resource.error("Lỗi mạng: " + t.getMessage()));
            }
        });
    }

    public void repass(RepassRequest request, MutableLiveData<Resource<String>> liveData) {
        liveData.setValue(Resource.loading());
        apiService.repass(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        liveData.setValue(Resource.success(response.body().string()));
                    } else {
                        liveData.setValue(Resource.error("Không thể đổi mật khẩu"));
                    }
                } catch (IOException e) {
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
