package com.example.appxemphim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim.Activity.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore db;
    FirebaseDatabase realtime;
    TextView name;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
      setContentView(R.layout.activity_main);
        name =findViewById(R.id.textView);
        logout = findViewById(R.id.button6);
        mAuth= FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        realtime = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
       checkout();

    }

    private void checkout() {
        user = mAuth.getCurrentUser();
        if(user != null){
            name.setText("chào mừng "+user.getDisplayName()+" đến với app xem phim");
            name.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
        }
    }


    public void siginwithgoogle(View view) {
        Intent intent = new Intent(MainActivity.this,GoogleAuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void registed(View view) {
        Intent intent = new Intent(MainActivity.this,RegistedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void logout(View view) {
        mAuth.signOut();
        name.setVisibility(View.GONE);
        logout.setVisibility(View.GONE);
        Toast.makeText(this, "bạn đã đăng xuất ", Toast.LENGTH_SHORT).show();
    }

    public void gotoprofile(View view) {
        try {
            Log.d("MainActivity", "Chuyển sang ProfileActivity...");
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } catch (Exception e) {
            Log.e("MainActivity", "Lỗi khi mở ProfileActivity: " + e.getMessage());
        }
    }

    public void forgetPass(View view) {
        startActivity(new Intent(MainActivity.this, ForgotPassword.class));
    }

    public void changepageaddmovie(View view) {
        //startActivity(new Intent(MainActivity.this,Test_Take_Movie.class));
        startActivity(new Intent(MainActivity.this,Add_data_sample.class));
    }

    public void goHomeActivity(View view) {
        startActivity(new Intent(MainActivity.this, HomeActivity.class));

    }
}