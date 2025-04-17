package com.example.appxemphim;

import static com.example.appxemphim.Interact_With_Email.Email.checkEmailExistsAsync;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Register extends AppCompatActivity {
    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword, editTextConfirmPassword;

    Button buttonRegister;
    private boolean isCheckingEmail = false;
    boolean emailExists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPasswordRegister);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        buttonRegister = findViewById(R.id.buttonRegister);
        editTextEmail.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 1000;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //thongbao.setVisibility(View.GONE);
                isCheckingEmail = true;
                timer.cancel(); // Hủy đếm thời gian trước đó nếu người dùng tiếp tục nhập
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //API check Email
                    }
                }, DELAY);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (!emailExists) {
                    Toast.makeText(Register.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                    //thongbaodk.setVisibility(View.VISIBLE);
                    //thongbaodk.setText("Email không tồn tại");
                    return;
                }

                if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(Register.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    MainActivity.mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        MainActivity.user = MainActivity.mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(firstName+lastName)
                                                .build();
                                        MainActivity.user.updateProfile(profileUpdates).addOnCompleteListener(e -> {
                                            if (e.isSuccessful()) {
                                                Log.d("Firebase", "User name updated.");
                                                Toast.makeText(getApplicationContext(), MainActivity.user.getDisplayName(), Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Register.this, Login.class);
                                                intent.putExtra("gmail", MainActivity.user.getEmail());
                                                intent.putExtra("pass", password);
                                                startActivity(intent);
                                            } else {
                                                Log.w("TAG", "false ", task.getException());
                                                Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
    }

    public void GetSign(View view) {
        startActivity(new Intent(Register.this, Login.class));
        //về sign
    }

    public void Back(View view) {
        finish();//đi về sign
    }
}