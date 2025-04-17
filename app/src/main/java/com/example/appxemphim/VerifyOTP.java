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

public class VerifyOTP extends AppCompatActivity {
    EditText otp1,otp2,otp3,otp4,otp5,otp6;

    Button buttonVerify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify_otp);
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        buttonVerify = findViewById(R.id.buttonVerify);

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otp1.getText().toString().trim() +
                        otp2.getText().toString().trim() +
                        otp3.getText().toString().trim() +
                        otp4.getText().toString().trim() +
                        otp5.getText().toString().trim() +
                        otp6.getText().toString().trim();

                if(otp.length() < 6){
                    Toast.makeText(VerifyOTP.this, "Vui lòng nhập otp",Toast.LENGTH_SHORT).show();
                }else{
                    if (otp.equals("123456")){
                        startActivity(new Intent(VerifyOTP.this, ResetPassword.class));
                    }else{
                        Toast.makeText(VerifyOTP.this, "OTP không đúng",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void Back(View view) {
        finish();
    }
}