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
import com.example.appxemphim.ViewModel.AuthViewModel;
import com.example.appxemphim.databinding.ActivityRegisterBinding;
import com.example.appxemphim.databinding.ActivityResetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {
    private android.app.ProgressDialog progressDialog;
    private ActivityResetPasswordBinding binding;
    private RepassRequest repassRequest;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private SharedPreferences sharedPref;
    private  String email;
    //email,DTO lấy tên SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPref = getSharedPreferences("LocalStore", MODE_PRIVATE);
        email = sharedPref.getString("Email","");
        binding.buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = binding.editTextNewPassword.getText().toString();
                String confirmNewPassword = binding.editTextConfirmNewPassword.getText().toString();

                if(newPassword.isEmpty() || confirmNewPassword.isEmpty()){
                    Toast.makeText(ResetPassword.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                }else {
                    if(newPassword.equals(confirmNewPassword)) {
                        repassRequest = new RepassRequest(email,newPassword);
                        //API setPAss
                        AuthViewModel authViewModel = new AuthViewModel();
                        authViewModel.repass(repassRequest);
                        authViewModel.repassResult.observe(ResetPassword.this, resource -> {
                            if (resource != null) {
                                switch (resource.getStatus()) {
                                    case LOADING:
                                        progressDialog.show();
                                        break;

                                    case SUCCESS:
                                        // Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
                                        progressDialog.dismiss();
                                        Toast.makeText(ResetPassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                                        intent.putExtra("gmail", user.getEmail());
                                        intent.putExtra("pass", newPassword);
                                        startActivity(intent);
                                        break;
                                    case ERROR:
                                        progressDialog.dismiss();
                                        Toast.makeText(ResetPassword.this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }else{
                                Toast.makeText(ResetPassword.this, "Fuck", Toast.LENGTH_SHORT).show();
                            }

                        });
                    }
                }
            }
        });
    }
}