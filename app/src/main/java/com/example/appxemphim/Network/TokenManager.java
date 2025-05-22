package com.example.appxemphim.Network;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
        private final SharedPreferences prefs;

        public TokenManager(Context context) {
            prefs = context.getSharedPreferences("LocalStore", Context.MODE_PRIVATE);
        }

        public String getAccessToken() {
            return prefs.getString("Token", null);
        }

        public void saveAccessToken(String token) {
            prefs.edit().putString("Token", token).apply();
        }

        public String getRefreshToken() {
            return prefs.getString("RefreshToken", null);
        }

        public void saveRefreshToken(String token) {
            prefs.edit().putString("RefreshToken", token).apply();
        }
}
