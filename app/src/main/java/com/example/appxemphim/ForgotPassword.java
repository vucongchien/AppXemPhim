package com.example.appxemphim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgotPassword extends AppCompatActivity {
    EditText editTextEmail;
    Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmailtoOtp);
        btnSendEmail = findViewById(R.id.buttonSendEmail);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                //API check email tồn tại Trong db
                if(email.equals("truongtung9ctcv@gmail.com")){
                    //API sent DTO
                    //lưa SharedPreferences
                    startActivity(new Intent(ForgotPassword.this, VerifyOTP.class));
                }else{
                    Toast.makeText(ForgotPassword.this, "email không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void BackSign(View view) {
        startActivity(new Intent(ForgotPassword.this, Login.class));
    }

    public void Back(View view) {
        finish();
    }
}