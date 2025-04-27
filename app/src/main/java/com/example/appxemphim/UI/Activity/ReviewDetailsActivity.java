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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.Request.ReviewRequest;
import com.example.appxemphim.UI.Adapter.ListReviewAdapter;
import com.example.appxemphim.ViewModel.ReviewViewModel;
import com.example.appxemphim.databinding.ActivityReviewDetailsBinding;
import com.example.appxemphim.R;

import java.util.List;
import java.util.Map;

public class ReviewDetailsActivity extends AppCompatActivity {
    private  ActivityReviewDetailsBinding binding;
    private android.app.ProgressDialog progressDialog;
    private  ReviewViewModel reviewViewModel;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewDetailsBinding.inflate(getLayoutInflater());
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        setContentView(binding.getRoot());
        String movie_id = getIntent().getStringExtra("video_id");
        getdata("rMlXfo9TGonjR8NuwNGE");

    }

    private void getdata(String movie_id){
        reviewViewModel = new ReviewViewModel(ReviewDetailsActivity.this);
        reviewViewModel.getReview(movie_id);
        reviewViewModel.getDataReview.observe(this, resource->{
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        progressDialog.show();
                        break;

                    case SUCCESS:
                        // Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
                        progressDialog.dismiss();
                        List<ReviewModel> reviewModelList = resource.getData();
                        if (reviewModelList != null) {
                            setupRatingBars(reviewModelList);
                        }
                        break;
                    case ERROR:
                        progressDialog.dismiss();
                        Toast.makeText(this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void setupRatingBars(List<ReviewModel> reviewModelList) {
        int[] colorResIds = {
                R.color.star5, R.color.star4, R.color.star3, R.color.star2, R.color.star1
        };
        ProgressBar[] progressBars = {
                binding.progressBar5, binding.progressBar4, binding.progressBar3, binding.progressBar2, binding.progressBar1
        };
        TextView[] labels = {
                binding.labelInsideBar5, binding.labelInsideBar4, binding.labelInsideBar3, binding.labelInsideBar2, binding.labelInsideBar1
        };

        int total = reviewModelList.size();
        int totalRating = reviewModelList.stream()
                .mapToInt(review -> (int) review.getRating())
                .sum();

        if (total > 0) {
            binding.ratingValue.setText(String.format("%.1f", (float) totalRating / total));
            binding.ratingBar.setRating((float) totalRating / total);
        } else {
            binding.ratingValue.setText("0");
        }
        binding.ratingCount.setText(String.valueOf(total));


        for (int i = 0; i < 5; i++) {
            int starValue = 5 - i;
            int starCount = (int) reviewModelList.stream()
                    .filter(review -> (int) review.getRating() == starValue)
                    .count();
            int percent = total > 0 ? (starCount * 100 / total) : 0;

            progressBars[i].setProgressTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, colorResIds[i]))
            );
            labels[i].setText(String.valueOf(starCount));

            ObjectAnimator animation = ObjectAnimator.ofInt(progressBars[i], "progress", 0, percent);
            animation.setDuration(2000);
            animation.start();
        }
        ListReviewAdapter listReviewAdapter = new ListReviewAdapter(ReviewDetailsActivity.this,reviewModelList);
        binding.listRating.setAdapter(listReviewAdapter);
    }

    private void showRatingPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_rating, null);
        ImageView imageView = popupView.findViewById(R.id.btnClose);
        Button submit = popupView.findViewById(R.id.btnSubmit);
        RatingBar ratingBar = popupView.findViewById(R.id.ratingBar);
        EditText review =  popupView.findViewById(R.id.etReview);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1f);
        ratingBar.setScaleX(1.4f);
        ratingBar.setScaleY(1.4f);

        // Tạo popup chiếm 70% chiều cao
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        int popupHeight = (int) (screenHeight * 0.7);

        popupWindow = new PopupWindow(
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
        imageView.setOnClickListener(v1->popupWindow.dismiss());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewRequest reviewRequest = new ReviewRequest();
                reviewRequest.setMovie_id("rMlXfo9TGonjR8NuwNGE");
                reviewRequest.setUser_id("51B78veg7ZVfrdHDikXzOV31t1w1");
                reviewRequest.setRating((int) ratingBar.getRating());
                reviewRequest.setDescription(review.getText().toString());
                reviewViewModel = new ReviewViewModel(ReviewDetailsActivity.this);
                reviewViewModel.addReview(reviewRequest);
                reviewViewModel.addDataReview.observe(ReviewDetailsActivity.this,resource->{
                    if (resource != null) {
                        switch (resource.getStatus()) {
                            case LOADING:
                                progressDialog.show();
                                break;

                            case SUCCESS:// Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
                                progressDialog.dismiss();
                                String movieDetails = resource.getData();
                                if (movieDetails != null) {
                                    Toast.makeText(ReviewDetailsActivity.this, movieDetails, Toast.LENGTH_SHORT).show();
                                    getdata("rMlXfo9TGonjR8NuwNGE");
                                    popupWindow.dismiss();
                                }
                                break;
                            case ERROR:
                                progressDialog.dismiss();
                                Toast.makeText(ReviewDetailsActivity.this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }
        });
    }


    public void writeRating(View view) {
        showRatingPopup();
    }


}