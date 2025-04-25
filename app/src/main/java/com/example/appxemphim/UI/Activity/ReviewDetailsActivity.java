package com.example.appxemphim.UI.Activity;

import static android.os.Build.VERSION_CODES.P;

import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.media.Rating;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.appxemphim.databinding.ActivityReviewDetailsBinding;
import com.example.appxemphim.R;

import java.util.Map;

public class ReviewDetailsActivity extends AppCompatActivity {
    private  ActivityReviewDetailsBinding binding;
    private android.app.ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewDetailsBinding.inflate(getLayoutInflater());
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        setContentView(binding.getRoot());
        int[] ratings = {75155, 14921, 6116, 3337, 6067}; // từ 5 sao -> 1 sao
        setupRatingBars(ratings);

    }

    private void setupRatingBars(int[] starCounts){
        int[] colorResIds = {
                R.color.star5,    // 5 sao
                R.color.star4,          // 4 sao
                R.color.star3,         // 3 sao
                R.color.star2,         // 2 sao
                R.color.star1             // 1 sao
        };
        int total = 0;
        for (int count : starCounts) {
            total += count;
        }



        ProgressBar[] progressBars = {
                binding.progressBar5,
                binding.progressBar4,
                binding.progressBar3,
                binding.progressBar2,
                binding.progressBar1
        };

        TextView[] labels = {
                binding.labelInsideBar5,
                binding.labelInsideBar4,
                binding.labelInsideBar3,
                binding.labelInsideBar2,
                binding.labelInsideBar1
        };

        for (int i = 0; i < 5; i++) {
            int percent = total > 0 ? (starCounts[i] * 100 / total) : 0;
            progressBars[i].setProgressTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, colorResIds[i]))
            );
            progressBars[i].setProgress(percent);
            labels[i].setText(String.valueOf(starCounts[i]));
            ObjectAnimator animation = ObjectAnimator.ofInt(progressBars[i], "progress", 0, percent);
            animation.setDuration(2000);
            animation.start();
        }
    }

    private void showRatingPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_rating, null);

        Button closeButton = popupView.findViewById(R.id.btnSubmit);
        RatingBar ratingBar = popupView.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1f);
        ratingBar.setScaleX(1.4f);
        ratingBar.setScaleY(1.4f);

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            // Xử lý rating ở đây
        });

        // Tạo popup chiếm 70% chiều cao
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        int popupHeight = (int) (screenHeight * 0.7);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                popupHeight,
                true
        );

        // Background trong suốt
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // Hiệu ứng từ dưới lên (animation style custom)
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        // Hiển thị ở dưới cùng
        popupWindow.showAtLocation(binding.getRoot(), Gravity.BOTTOM, 0, 0);

        closeButton.setOnClickListener(v1 -> popupWindow.dismiss());
    }


    public void writeRating(View view) {
        showRatingPopup();
    }
}