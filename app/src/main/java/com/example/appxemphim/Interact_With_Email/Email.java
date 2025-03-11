package com.example.appxemphim.Interact_With_Email;
import okhttp3.Request;
import okhttp3.OkHttpClient;

import okhttp3.Callback;


public class Email {
    private static final String API_KEY = "d4b78013738548a3bd12ce4c2143fe79";
    private static final String API_URL = "https://emailvalidation.abstractapi.com/v1/?api_key=" + API_KEY + "&email=";

    public static void checkEmailExistsAsync(String email, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL + email)
                .build();

        client.newCall(request).enqueue(callback);
    }

}
