package com.example.appxemphim.LoginRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Activity.HomeActivity;
import com.example.appxemphim.Utilities.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        String name = getIntent().getStringExtra("gmail");
        String pass = getIntent().getStringExtra("pass");
        if(name!=null){
            editTextUserName.setText(name);
        }
        if(pass!=null){
            editTextPassword.setText(pass);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
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
                                                editor.putString("Email", username);
                                                editor.putString("Token", token);
                                                editor.apply();
                                                Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                                Toast.makeText(LoginActivity.this, "Lỗi xử lý dữ liệu phản hồi", Toast.LENGTH_SHORT).show();
                                            }
                                        }else {
                                            Toast.makeText(LoginActivity.this, "Lỗi laays token", Toast.LENGTH_SHORT).show();
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
                        }else{
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void GetRegister(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void Forgetpw(View view) {
        startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
    }

    public void SignGG(View view) {
        Intent intent = new Intent(LoginActivity.this, GoogleAuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}