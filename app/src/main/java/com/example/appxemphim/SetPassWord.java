package com.example.appxemphim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

public class SetPassWord extends MainActivity {
    EditText newpass;
    EditText repass;
    TextView thongbao;
    String email;
    String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_set_pass_word);
        newpass = findViewById(R.id.newpass);
        repass = findViewById(R.id.repass);
        thongbao = findViewById(R.id.thongbao1);
        email= getIntent().getStringExtra("email");
        otp= getIntent().getStringExtra("code_otp");
    }

    public void changepass(View view) {

        if(!repass.getText().toString().equals(newpass.getText().toString())    ||  newpass.getText().toString().isEmpty()){
            thongbao.setVisibility(View.VISIBLE);
            thongbao.setText("mat khau khong khop");
        }else{
            AuthCredential credential = EmailAuthProvider.getCredential(email, otp);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Cập nhật mật khẩu mới
                                user.updatePassword(newpass.getText().toString())
                                        .addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                Intent intent= new Intent(SetPassWord.this, LoginActivity.class);
                                                intent.putExtra("gmail",email);
                                                intent.putExtra("pass",newpass.getText().toString());
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
        }
    }
}