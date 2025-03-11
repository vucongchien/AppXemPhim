package com.example.appxemphim;

import static com.example.appxemphim.Interact_With_Email.Email.checkEmailExistsAsync;
import static com.example.appxemphim.Interact_With_Email.EmailOtp.sendOTP;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ForgotPassword extends AppCompatActivity {
    EditText email;
    EditText otp;
    TextView thongbao;
    TextView thongbaootp;
    String code_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.Email);
        otp =findViewById(R.id.OTP);
        thongbao = findViewById(R.id.thongbao);
        thongbaootp = findViewById(R.id.thongbaootp);
        email.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 1000;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel(); // Hủy đếm thời gian trước đó nếu người dùng tiếp tục nhập
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                       checkEmailExistsAsync(email.getText().toString(), new Callback() {
                           @Override
                           public void onFailure(@NonNull Call call, @NonNull IOException e) {

                           }

                           @Override
                           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                               if(response.isSuccessful() && response.body() != null){
                                   try {
                                       String json = response.body().string();
                                       JSONObject jsonObject= new JSONObject(json);
                                       String deliverability = jsonObject.optString("deliverability","UNDELIVERABLE");
                                       boolean emailExists = "DELIVERABLE".equals(deliverability);
                                        if(!emailExists){
                                            runOnUiThread(() -> {
                                                thongbao.setVisibility(View.VISIBLE);
                                                thongbao.setText("email không tồn tại");
                                            });
                                        }
                                   }catch (JSONException e) {
                                       Log.e("EmailCheck", "Lỗi JSON", e);
                                   }
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
    }

    public void sendOtp(View view) {
        code_otp = String.valueOf(new Random().nextInt(999999 - 100000) + 100000); // Tạo mã OTP 6 số
        sendOTP(this ,email.getText().toString(), code_otp);
        Toast.makeText(this, "Mã OTP đã được gửi đến email!", Toast.LENGTH_SHORT).show();

    }


    public void confirm(View view) {
        if(!code_otp.equals(otp.getText().toString())){
            thongbaootp.setVisibility(View.VISIBLE);
            thongbaootp.setText("Mã OTP không chính xác");
        }else{
            Intent intent = new Intent(ForgotPassword.this, SetPassWord.class);
            intent.putExtra("email",email.getText().toString());
            intent.putExtra("code_otp",code_otp);
            startActivity(intent);
        }
    }
}