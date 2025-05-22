package com.example.appxemphim.Network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private final TokenManager tokenManager;
    public TokenAuthenticator(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        if (responseCount(response) >= 2) {
            return null;
        }
        String refreshToken = tokenManager.getRefreshToken();
        Response refreshResponse = refreshToken(refreshToken);

        if (refreshResponse.isSuccessful()) {
            Log.d("NONO",tokenManager.getAccessToken());
            String newAccessToken = refreshResponse.body().string();
            Log.d("NONO",newAccessToken);
            tokenManager.saveAccessToken(newAccessToken);

            // Gửi lại request ban đầu với token mới
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newAccessToken)
                    .build();
        } else {
            return null; // refresh thất bại
        }
    }

    private int responseCount(Response response) {
        int count = 1;
        while ((response = response.priorResponse()) != null) {
            count++;
        }
        return count;
    }

    private Response refreshToken(String refreshToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:8081/auth/resettoken/" + refreshToken;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return client.newCall(request).execute();
    }
}
