package com.example.appxemphim;


import static com.example.appxemphim.Interact_With_Email.Email.checkEmailExistsAsync;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegistedActivity extends MainActivity {
    EditText gmail, username, pass, repass;
    Button regist;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registed);
        gmail = findViewById(R.id.editTextText);
        username = findViewById(R.id.editTextText2);
        pass = findViewById(R.id.editTextText3);
        repass = findViewById(R.id.editTextText4);
        regist = findViewById(R.id.button2);
    }

    public void regist(View view) {
        String emailtext= gmail.getText().toString();
        String name = username.getText().toString();
        String password = pass.getText().toString();
        if(!repass.getText().toString().equals(pass.getText().toString())){
            Toast.makeText(this, "Nhap lai mk ko dung!", Toast.LENGTH_SHORT).show();
        }
        else{
            checkEmailExistsAsync(emailtext, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(RegistedActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        try {
                            String json = response.body().string();
                            JSONObject jsonObject = new JSONObject(json);
                            String deliverability = jsonObject.optString("deliverability", "UNDELIVERABLE");
                            boolean emailExists = "DELIVERABLE".equals(deliverability);

                            runOnUiThread(() -> {
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
                                }
                            });

                        } catch (JSONException e) {
                            Log.e("EmailCheck", "Lỗi JSON", e);
                        }
                    }
                }
            });

        }
    }
}