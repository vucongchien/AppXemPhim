package com.example.appxemphim;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.UserProfileChangeRequest;

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
        if(!repass.getText().toString().equals(pass.getText().toString())){
            Toast.makeText(this, "Nhap lai mk ko dung!", Toast.LENGTH_SHORT).show();
        }
        else{
            String emailtext= gmail.getText().toString();
            String name = username.getText().toString();
            String password = pass.getText().toString();
            mAuth.createUserWithEmailAndPassword(emailtext,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
                        Toast.makeText(getApplicationContext(),user.getEmail(),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistedActivity.this, MainActivity.class));
                    }else {
                        Log.w("TAG", "false ",task.getException());
                        Toast.makeText(RegistedActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}