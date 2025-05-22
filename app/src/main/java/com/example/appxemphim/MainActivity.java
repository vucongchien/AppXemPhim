package com.example.appxemphim;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim.LoginRegister.ForgotPassword;
import com.example.appxemphim.LoginRegister.GoogleAuthActivity;
import com.example.appxemphim.LoginRegister.LoginActivity;
import com.example.appxemphim.LoginRegister.RegisterActivity;
import com.example.appxemphim.LoginRegister.ResetPassword;
import com.example.appxemphim.LoginRegister.VerifyOTP;
import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Network.RetrofitInstance;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Activity.EditProfileActivity;
import com.example.appxemphim.UI.Activity.HomeActivity;
import com.example.appxemphim.UI.Activity.MovieDetailsActivity;
import com.example.appxemphim.UI.Activity.testter;
import com.example.appxemphim.Utilities.FirebaseUtils;
import com.example.appxemphim.ViewModel.MovieDetailViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    private  FirebaseUser user;
    FirebaseFirestore db;
    FirebaseDatabase realtime;
    TextView name;
    Button logout;
    MovieDetailViewModel movieDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseUtils.getAuth();
        user= FirebaseUtils.getUser();
        realtime = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        RetrofitInstance.init(this);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstRun = true;

        if (isFirstRun) {
            runIntroAnimations();  // gọi animation
            Log.d("AnimationTest", "Chạy animation intro...");
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        }
       //checkout();

    }

//    private void checkout() {
//        user = mAuth.getCurrentUser();
//        if(user != null){
//            name.setText("chào mừng "+user.getDisplayName()+" đến với app xem phim");
//            name.setVisibility(View.VISIBLE);
//            logout.setVisibility(View.VISIBLE);
//        }
//    }
    private void runIntroAnimations() {
        Log.d("MainActivityTest", "Đang chạy animation");
        ImageView imageView = findViewById(R.id.imageViewHome);
        TextView text1 = findViewById(R.id.text1);
        TextView text2 = findViewById(R.id.text2);
        TextView text3 = findViewById(R.id.text3);
        Button btn = findViewById(R.id.buttonGetStart);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        imageView.startAnimation(fadeIn);
        text1.startAnimation(slideUp);
        text2.startAnimation(slideUp);
        text3.startAnimation(slideUp);
        btn.startAnimation(fadeIn);
    }


    public void registed(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
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


    public void goHomeActivity(View view) {
       // startActivity(new Intent(MainActivity.this, HomeActivity.class));

    }

    public void GetStart(View view) {
        Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
        //intent.putExtra("movie_id","rMlXfo9TGonjR8NuwNGE");
        startActivity(intent);
        startActivity(new Intent(MainActivity.this, HomeActivity.class));
    }
}