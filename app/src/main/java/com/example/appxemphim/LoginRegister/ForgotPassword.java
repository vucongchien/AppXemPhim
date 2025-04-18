package com.example.appxemphim.LoginRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.appxemphim.UI.Utils.CheckEmail;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    EditText editTextEmail;
    Button btnSendEmail;
    Boolean isCheckingEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmailtoOtp);
        btnSendEmail = findViewById(R.id.buttonSendEmail);

        editTextEmail.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 1000;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //thongbao.setVisibility(View.GONE);
                isCheckingEmail = true;
                timer.cancel(); // Hủy đếm thời gian trước đó nếu người dùng tiếp tục nhập
                timer = new Timer();
                String email = charSequence.toString().trim();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        CheckEmail.checkEmailFromApi(email, ForgotPassword.this, new CheckEmail.EmailCheckCallback() {
                            @Override
                            public void onResult(boolean exists) {
                                isCheckingEmail = exists;
                                if (!exists) {
                                    isCheckingEmail = false;
                                    Toast.makeText(ForgotPassword.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }, DELAY);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCheckingEmail) {
                    Toast.makeText(ForgotPassword.this, "email không tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
                ApiLoginRegisterService api = RetrofitInstance.getApiService();
                Call<ResponseBody> call = api.sentDTO(editTextEmail.getText().toString().trim());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if(response.code()==200){
                                try {
                                    String responseString = response.body().string();
                                    JSONObject jsonObject = new JSONObject(responseString);
                                    String OTP = jsonObject.getString("ma");
                                    String email = jsonObject.getString("email");
                                    SharedPreferences sharedPref = getSharedPreferences("LocalStore", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("Email", email);
                                    editor.putString("OTP", OTP);
                                    editor.apply();
                                    Toast.makeText(ForgotPassword.this, OTP, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotPassword.this, VerifyOTP.class);
                                    startActivity(intent);
                                }catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(ForgotPassword.this, "Lỗi xử lý dữ liệu phản hồi", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(ForgotPassword.this, "Lỗi Sent DTO", Toast.LENGTH_SHORT).show();
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
            }
        });


    }

    public void BackSign(View view) {
        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
    }

    public void Back(View view) {
        finish();
    }
}