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
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.R;
import com.example.appxemphim.Request.RepassRequest;
import com.example.appxemphim.Utilities.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {
    EditText editTextNewPassword, editTextConfirmNewPassword;
    Button buttonUpdate;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private SharedPreferences sharedPref;
    private  String email;
    //email,DTO lấy tên SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        sharedPref = getSharedPreferences("LocalStore", MODE_PRIVATE);
        email = sharedPref.getString("Email","");
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmNewPassword = findViewById(R.id.editTextConfirmNewPassword);
        buttonUpdate = findViewById(R.id.buttonResetPassword);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editTextNewPassword.getText().toString();
                String confirmNewPassword = editTextConfirmNewPassword.getText().toString();

                if(newPassword.isEmpty() || confirmNewPassword.isEmpty()){
                    Toast.makeText(ResetPassword.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }else {
                    if(newPassword.equals(confirmNewPassword)){
                        //API setPAss
                        ApiLoginRegisterService api = RetrofitInstance.getApiService();
                        Call<ResponseBody> call = api.repass(new RepassRequest(email,newPassword));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    if(response.code()==200){
                                        Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                                        intent.putExtra("gmail", user.getEmail());
                                        intent.putExtra("pass", newPassword);
                                        startActivity(intent);
                                    }else {
                                        Toast.makeText(ResetPassword.this, "Lỗi laays token", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.e("API_ERROR", "Code: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }else {
                        Toast.makeText(ResetPassword.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}