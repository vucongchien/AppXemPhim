package com.example.appxemphim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Login extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();
                MainActivity.mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            MainActivity.user = MainActivity.mAuth.getCurrentUser();
                            MainActivity.user.getUid();
                            Toast.makeText(Login.this, MainActivity.user.getUid(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void GetRegister(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }

    public void Forgetpw(View view) {
        startActivity(new Intent(Login.this, ForgotPassword.class));
    }

    public void SignGG(View view) {
        Intent intent = new Intent(Login.this,GoogleAuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}