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

import com.example.appxemphim.MainActivity;
import com.example.appxemphim.Model.DTO.EmailDTO;
import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.AuthRepository;
import com.example.appxemphim.UI.Utils.CheckEmail;
import com.example.appxemphim.ViewModel.AuthViewModel;
import com.example.appxemphim.databinding.ActivityForgotPasswordBinding;
import com.example.appxemphim.databinding.ActivityMovieDetailsBinding;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    Boolean isCheckingEmail;
    private android.app.ProgressDialog progressDialog;
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        binding.editTextEmailtoOtp.addTextChangedListener(new TextWatcher() {
//            private Timer timer = new Timer();
//            private final long DELAY = 1000;
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                //thongbao.setVisibility(View.GONE);
//                isCheckingEmail = true;
//                timer.cancel(); // Hủy đếm thời gian trước đó nếu người dùng tiếp tục nhập
//                timer = new Timer();
//                String email = charSequence.toString().trim();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        CheckEmail.checkEmailFromApi(email, ForgotPassword.this, new CheckEmail.EmailCheckCallback() {
//                            @Override
//                            public void onResult(boolean exists) {
//                                isCheckingEmail = exists;
//                                if (!exists) {
//                                    isCheckingEmail = false;
//                                    Toast.makeText(ForgotPassword.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }, DELAY);
//            }
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        binding.buttonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!isCheckingEmail) {
//                    Toast.makeText(ForgotPassword.this, "email không tồn tại", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                AuthViewModel authViewModel = new AuthViewModel();
                authViewModel.sendDTO(binding.editTextEmailtoOtp.getText().toString().trim());
                authViewModel.sentDtoResult.observe(ForgotPassword.this, resource -> {
                    if (resource != null) {
                        switch (resource.getStatus()) {
                            case LOADING:
                                progressDialog.show();
                                break;

                            case SUCCESS:
                                // Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
                                progressDialog.dismiss();
                                EmailDTO emailDTO = resource.getData();
                                if (emailDTO != null) {
                                    SharedPreferences sharedPref = getSharedPreferences("LocalStore", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("Email", emailDTO.getEmail());
                                    editor.putString("OTP", emailDTO.getMa());
                                    editor.apply();
                                    Toast.makeText(ForgotPassword.this, "OTP: " + emailDTO.getMa(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ForgotPassword.this, VerifyOTP.class);
                                    startActivity(intent);

                                }
                                break;
                            case ERROR:
                                progressDialog.dismiss();
                                Toast.makeText(ForgotPassword.this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
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