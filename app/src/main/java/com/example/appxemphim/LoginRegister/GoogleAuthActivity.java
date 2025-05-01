package com.example.appxemphim.LoginRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appxemphim.MainActivity;
import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.R;

import com.example.appxemphim.Utilities.FirebaseUtils;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoogleAuthActivity extends MainActivity {
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 123;
    private FirebaseAuth mAuth;
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        oneTapClient = Identity.getSignInClient(this);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.default_web_client_id))
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                .build();

        signIn();
    }

    private void signIn() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        startIntentSenderForResult(result.getPendingIntent().getIntentSender(), REQ_ONE_TAP, null, 0, 0, 0);
                    } catch (Exception e) {
                        Log.e("Google Sign-In", "Error: " + e.getMessage());
                    }
                })
                .addOnFailureListener(this, e -> {

                    Toast.makeText(this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONE_TAP) {
            try {
                SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                String idToken = credential.getGoogleIdToken();
                if (idToken != null) {
                    firebaseAuthWithGoogle(idToken);
                }
            } catch (ApiException e) {
                Log.e("Google Sign-In", "Lỗi: " + e.getMessage());
                finish();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();
                        ApiLoginRegisterService api = RetrofitInstance.getApiService();
                        Call<ResponseBody> call = api.loginWithToken(user.getUid());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    if(response.code()==200){
                                        try {
                                            String token = response.body().string();
                                            SharedPreferences sharedPref = getSharedPreferences("LocalStore", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("Email", user.getEmail());
                                            editor.putString("Token", token);
                                            editor.apply();
                                            Log.d("aduuu", "onComplete: "+token);
                                            Intent intent = new Intent(GoogleAuthActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                            Toast.makeText(GoogleAuthActivity.this, "Lỗi xử lý dữ liệu phản hồi", Toast.LENGTH_SHORT).show();
                                        }

                                    }else {
                                        Toast.makeText(GoogleAuthActivity.this, "Lỗi laays token", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.e("API_ERROR", "Code: " + response.code());
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("API_FAILURE", t.getMessage());
                            }
                        });
                    } else {
                        Toast.makeText(this, "Xác thực thất bại!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}