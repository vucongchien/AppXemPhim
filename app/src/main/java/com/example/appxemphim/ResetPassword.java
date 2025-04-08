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

public class ResetPassword extends AppCompatActivity {
    EditText editTextNewPassword, editTextConfirmNewPassword;
    Button buttonUpdate;


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
                        Toast.makeText(ResetPassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPassword.this, Login.class));
                    }else {
                        Toast.makeText(ResetPassword.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}