package com.example.appxemphim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends MainActivity {
    EditText gmail;
    EditText pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        gmail = findViewById(R.id.editTextText5);
        pass = findViewById(R.id.editTextText6);
        checkLogin();
    }

    private void checkLogin() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String gmailText = getIntent().getStringExtra("gmail");
            String passText = getIntent().getStringExtra("pass");
            gmail.setText(gmailText);
            pass.setText(passText);
        }
    }


    public void login(View view) {
       if(gmail.getText().toString().isEmpty()  || pass.getText().toString().isEmpty() ){
           Toast.makeText(this, "vui lòng nhập đủ thông tin gmail và pass", Toast.LENGTH_SHORT).show();
       }else{
            mAuth.signInWithEmailAndPassword(gmail.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}