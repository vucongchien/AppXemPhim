package com.example.appxemphim;


import static com.example.appxemphim.Interact_With_Email.Email.checkEmailExistsAsync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegistedActivity extends MainActivity {
<<<<<<< Updated upstream
    EditText email, username, pass, repass;
    boolean emailExists;
    TextView thongbao, thongbaodk;
    private boolean isCheckingEmail = false;
=======
    EditText gmail, username, pass, repass;
>>>>>>> Stashed changes

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registed);
        email = findViewById(R.id.editTextText);
        username = findViewById(R.id.editTextText2);
        pass = findViewById(R.id.editTextText3);
        repass = findViewById(R.id.editTextText4);
<<<<<<< Updated upstream
        thongbao = findViewById(R.id.notiRegisterEmail);
        thongbaodk = findViewById(R.id.thongbaodk);
        email.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 1000;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                thongbao.setVisibility(View.GONE);
                isCheckingEmail = true;
                timer.cancel(); // Hủy đếm thời gian trước đó nếu người dùng tiếp tục nhập
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        checkEmailExistsAsync(email.getText().toString(), new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                isCheckingEmail = false;
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                isCheckingEmail = false;
                                if(response.isSuccessful() && response.body() != null){
                                    try {
                                        String json = response.body().string();
                                        JSONObject jsonObject= new JSONObject(json);
                                        String deliverability = jsonObject.optString("deliverability","UNDELIVERABLE");
                                        emailExists = "DELIVERABLE".equals(deliverability);
                                        if(!emailExists){
                                            runOnUiThread(() -> {
                                                thongbao.setVisibility(View.VISIBLE);
                                                thongbao.setText("Email không tồn tại");
                                            });
                                        }
                                    }catch (JSONException e) {
                                        Log.e("EmailCheck", "Lỗi JSON", e);
                                    }
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
=======
>>>>>>> Stashed changes
    }

    public void regist(View view) {
        String emailtext= email.getText().toString();
        String name = username.getText().toString();
        String password = pass.getText().toString();
        if (!repass.getText().toString().equals(pass.getText().toString())) {
            Toast.makeText(this, "Nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
            thongbaodk.setVisibility(View.VISIBLE);
            thongbaodk.setText("mật khẩu sai");
            return;
        }

<<<<<<< Updated upstream
        if (!emailExists) {
            Toast.makeText(this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
            thongbaodk.setVisibility(View.VISIBLE);
            thongbaodk.setText("Email không tồn tại");
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailtext, password)
                .addOnCompleteListener(RegistedActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(e -> {
                                if (e.isSuccessful()) {
                                    Log.d("Firebase", "User name updated.");
                                    Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_LONG).show();
=======
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            String json = response.body().string();
                            JSONObject jsonObject = new JSONObject(json);
                            String deliverability = jsonObject.optString("deliverability", "UNDELIVERABLE");
                            boolean emailExists = "DELIVERABLE".equals(deliverability);
                                if (emailExists) {
                                    mAuth.createUserWithEmailAndPassword(emailtext,password).addOnCompleteListener(RegistedActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                Log.d("TAG", "true " );
                                                FirebaseUser user=mAuth.getCurrentUser();
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(name)
                                                        .build();
                                                user.updateProfile(profileUpdates)
                                                        .addOnCompleteListener(e -> {
                                                            if (e.isSuccessful()) {
                                                                Log.d("Firebase", "User name updated.");
                                                                Toast.makeText(getApplicationContext(),user.getDisplayName(),Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                Intent intent = new Intent(RegistedActivity.this, LoginActivity.class);
                                                intent.putExtra("gmail", user.getEmail().toString());
                                                intent.putExtra("pass",password);
                                                startActivity(intent);
                                            }else {
                                                Log.w("TAG", "false ",task.getException());
                                                Toast.makeText(RegistedActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegistedActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
>>>>>>> Stashed changes
                                }

                            Intent intent = new Intent(RegistedActivity.this, LoginActivity.class);
                            intent.putExtra("gmail", user.getEmail());
                            intent.putExtra("pass", password);
                            startActivity(intent);
                        } else {
                            Log.w("TAG", "false ", task.getException());
                            Toast.makeText(RegistedActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}