package com.example.appxemphim.LoginRegister;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim.R;
import com.example.appxemphim.databinding.ActivityResetPasswordBinding;
import com.example.appxemphim.databinding.ActivityVerifyOtpBinding;

public class VerifyOTP extends AppCompatActivity {
    private ActivityVerifyOtpBinding binding;
    private EditText[] otpInputs;
    String ma;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPref = getSharedPreferences("LocalStore", MODE_PRIVATE);
        ma = sharedPref.getString("OTP","");
        Toast.makeText(this, ma, Toast.LENGTH_SHORT).show();
        otpInputs = new EditText[]{
                binding.otp1,
                binding.otp2,
                binding.otp3,
                binding.otp4,
                binding.otp5,
                binding.otp6
        };
        setupOTPInputs();
        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder otp = new StringBuilder();
                for (EditText editText : otpInputs) {
                    otp.append(editText.getText().toString());
                }

                if(otp.length() < 6){
                    Toast.makeText(VerifyOTP.this, "Vui lòng nhập otp",Toast.LENGTH_SHORT).show();
                }else{
                    if (otp.toString().equals(ma)){
                        startActivity(new Intent(VerifyOTP.this, ResetPassword.class));
                    }else{
                        Toast.makeText(VerifyOTP.this, "OTP không đúng",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    private void setupOTPInputs() {
        for (int i = 0; i < otpInputs.length; i++) {
            final int index = i;

            otpInputs[index].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().isEmpty() && index < otpInputs.length - 1) {
                        otpInputs[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });

            otpInputs[index].setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_DEL &&
                        otpInputs[index].getText().toString().isEmpty() &&
                        index > 0) {
                    otpInputs[index - 1].setText("");
                    otpInputs[index - 1].requestFocus();
                    return true;
                }
                return false;
            });
        }
    }
}