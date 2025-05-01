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

import com.example.appxemphim.MainActivity;
import com.example.appxemphim.Model.DTO.EmailDTO;
import com.example.appxemphim.Network.ApiLoginRegisterService;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.R;
import com.example.appxemphim.Utilities.FirebaseUtils;
import com.example.appxemphim.ViewModel.AuthViewModel;
import com.example.appxemphim.databinding.ActivityForgotPasswordBinding;
import com.example.appxemphim.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private android.app.ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        String name = getIntent().getStringExtra("gmail");
        String pass = getIntent().getStringExtra("pass");
        if(name!=null){
            binding.editTextUserName.setText(name);
        }
        if(pass!=null){
            binding.editTextPassword.setText(pass);
        }


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnLogin();
            }
        });
    }

    private void setBtnLogin(){
        String username = binding.editTextUserName.getText().toString();
        String password = binding.editTextPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    user = mAuth.getCurrentUser();
                    sendFCMtoServer();
                    AuthViewModel authViewModel = new AuthViewModel();
                    authViewModel.login(user.getUid());
                    authViewModel.loginResult.observe(LoginActivity.this, resource -> {
                        if (resource != null) {
                            switch (resource.getStatus()) {
                                case LOADING:
                                    progressDialog.show();
                                    break;

                                case SUCCESS:
                                    // Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
                                    progressDialog.dismiss();
                                    String token = resource.getData();
                                    if (token != null) {
                                        SharedPreferences sharedPref = getSharedPreferences("LocalStore", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("Token", token);
                                        editor.apply();
                                        Log.d("aduuu", "onComplete: "+token);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);

                                    }
                                    break;
                                case ERROR:
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendFCMtoServer(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("FCM", "Lỗi khi lấy FCM token", task.getException());
                return;
            }

            String token = task.getResult();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser == null || token == null) {
                Log.e("FCM", "User hoặc Token null");
                return;
            }

            String userId = currentUser.getUid();

            FirebaseFirestore.getInstance()
                    .collection("Users")
                    .document(userId)
                    .set(Collections.singletonMap("fcm_token", token), SetOptions.merge())
                    .addOnSuccessListener(aVoid -> Log.d("FCM", "Token updated"))
                    .addOnFailureListener(e -> Log.e("FCM", "Update failed", e));
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