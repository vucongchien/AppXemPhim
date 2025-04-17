package com.example.appxemphim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    EditText editTextNewPassword, editTextConfirmNewPassword;
    Button buttonUpdate;

    String email,otp;

    //email,DTO lấy tên SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);
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
                        AuthCredential credential = EmailAuthProvider.getCredential(email, otp);
                        MainActivity.mAuth.signInWithCredential(credential)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        MainActivity.user = MainActivity.mAuth.getCurrentUser();
                                        if (MainActivity.user != null) {
                                            MainActivity.user.updatePassword(newPassword)
                                                    .addOnCompleteListener(updateTask -> {
                                                        if (updateTask.isSuccessful()) {
                                                            Intent intent= new Intent(ResetPassword.this, Login.class);
                                                            intent.putExtra("gmail",email);
                                                            intent.putExtra("pass",newPassword);
                                                            startActivity(intent);
                                                        } else {
                                                            Log.e("UpdatePassword", "Cập nhật thất bại!", updateTask.getException());
                                                        }
                                                    });
                                        }
                                    } else {
                                        Log.e("Login", "Đăng nhập thất bại!", task.getException());
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