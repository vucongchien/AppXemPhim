package com.example.appxemphim.LoginRegister;

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

import com.example.appxemphim.R;
import com.example.appxemphim.UI.Utils.CheckEmail;
import com.example.appxemphim.Utilities.FirebaseUtils;
import com.example.appxemphim.databinding.ActivityLoginBinding;
import com.example.appxemphim.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private boolean isCheckingEmail = false;
    boolean emailExists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
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
                String email = charSequence.toString().trim();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        CheckEmail.checkEmailFromApi(email, RegisterActivity.this, new CheckEmail.EmailCheckCallback() {
                            @Override
                            public void onResult(boolean exists) {
                                isCheckingEmail = exists;
                                if (!exists) {
                                    isCheckingEmail = false;
                                    Toast.makeText(RegisterActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                                    //thongbao.setText("Email không tồn tại");
                                }
                            }
                        });
                    }
                }, DELAY);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.editTextFirstName.getText().toString();
                String lastName = binding.editTextLastName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPasswordRegister.getText().toString();
                String confirmPassword = binding.editTextConfirmPassword.getText().toString();

                if (!emailExists) {
                    Toast.makeText(RegisterActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                    //thongbaodk.setVisibility(View.VISIBLE);
                    //thongbaodk.setText("Email không tồn tại");
                    return;
                }

                if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(firstName+lastName)
                                                .build();
                                        user.updateProfile(profileUpdates).addOnCompleteListener(e -> {
                                            if (e.isSuccessful()) {
                                                Log.d("Firebase", "User name updated.");
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                Map<String, Object> avatarData = new HashMap<>();
                                                avatarData.put("avata", "https://drive.google.com/uc?export=download&id=1Wh7yZaCbdtLDI2Sr2noMf27mYspnSU1w");

                                                db.collection("Avata").document(user.getUid())
                                                        .set(avatarData)
                                                        .addOnSuccessListener(aVoid -> Log.d("Firestore", "Avatar document added successfully."))
                                                        .addOnFailureListener(ex -> Log.w("Firestore", "Error adding avatar document", ex));
                                                Toast.makeText(getApplicationContext(), user.getDisplayName(), Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                intent.putExtra("gmail", user.getEmail());
                                                intent.putExtra("pass", password);
                                                startActivity(intent);
                                            } else {
                                                Log.w("TAG", "false ", task.getException());
                                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Exception exception = task.getException();
                                        if (exception instanceof FirebaseAuthUserCollisionException) {
                                            //thongbaodk.setVisibility(View.VISIBLE);
                                            //thongbaodk.setText("Email đã được đăng ký");
                                            Toast.makeText(RegisterActivity.this, "Email đã được đăng ký", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Lỗi: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });
    }

    public void GetSign(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        //về sign
    }

    public void Back(View view) {
        finish();//đi về sign
    }




}