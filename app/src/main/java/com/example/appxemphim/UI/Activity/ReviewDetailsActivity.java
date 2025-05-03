//package com.example.appxemphim.UI.Activity;
//
//import static android.os.Build.VERSION_CODES.P;
//
//import android.animation.ObjectAnimator;
//import android.content.res.ColorStateList;
//import android.graphics.drawable.ColorDrawable;
//import android.media.Rating;
//import android.os.Bundle;
//import android.renderscript.ScriptGroup;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.ProgressBar;
//import android.widget.RatingBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.example.appxemphim.Model.ReviewModel;
//import com.example.appxemphim.Request.ReviewRequest;
//import com.example.appxemphim.ViewModel.ReviewViewModel;
//import com.example.appxemphim.databinding.ActivityReviewDetailsBinding;
//import com.example.appxemphim.R;
//
//import java.util.List;
//import java.util.Map;
//
//public class ReviewDetailsActivity extends AppCompatActivity {
//    private  ActivityReviewDetailsBinding binding;
//    private android.app.ProgressDialog progressDialog;
//    private  ReviewViewModel reviewViewModel;
//    PopupWindow popupWindow;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityReviewDetailsBinding.inflate(getLayoutInflater());
//        progressDialog = new android.app.ProgressDialog(this);
//        progressDialog.setMessage("Đang tải dữ liệu...");
//        progressDialog.setCancelable(true);
//        setContentView(binding.getRoot());
//        getdata("rMlXfo9TGonjR8NuwNGE");
//    }
//
//
//
//    private void setupRatingBars(List<ReviewModel> reviewModelList) {
//        int[] colorResIds = {
//                R.color.star5, R.color.star4, R.color.star3, R.color.star2, R.color.star1
//        };
//        ProgressBar[] progressBars = {
//                binding.progressBar5, binding.progressBar4, binding.progressBar3, binding.progressBar2, binding.progressBar1
//        };
//        TextView[] labels = {
//                binding.labelInsideBar5, binding.labelInsideBar4, binding.labelInsideBar3, binding.labelInsideBar2, binding.labelInsideBar1
//        };
//
//        int total = reviewModelList.size();
//        int totalRating = reviewModelList.stream()
//                .mapToInt(review -> (int) review.getRating())
//                .sum();
//
//        if (total > 0) {
//            binding.ratingValue.setText(String.format("%.1f", (float) totalRating / total));
//            binding.ratingBar.setRating((float) totalRating / total);
//        } else {
//            binding.ratingValue.setText("0");
//        }
//        binding.ratingCount.setText(String.valueOf(total));
//
//
//        for (int i = 0; i < 5; i++) {
//            int starValue = 5 - i;
//            int starCount = (int) reviewModelList.stream()
//                    .filter(review -> (int) review.getRating() == starValue)
//                    .count();
//            int percent = total > 0 ? (starCount * 100 / total) : 0;
//
//            progressBars[i].setProgressTintList(
//                    ColorStateList.valueOf(ContextCompat.getColor(this, colorResIds[i]))
//            );
//            labels[i].setText(String.valueOf(starCount));
//
//            ObjectAnimator animation = ObjectAnimator.ofInt(progressBars[i], "progress", 0, percent);
//            animation.setDuration(2000);
//            animation.start();
//        }
//
//    }
//
//
//
//
//
//    public void writeRating(View view) {
//        showRatingPopup();
//    }
//
//
//}