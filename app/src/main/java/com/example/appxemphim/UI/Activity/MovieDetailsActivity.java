package com.example.appxemphim.UI.Activity;

import static com.google.api.ResourceProto.resource;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import com.example.appxemphim.Model.ActorModel;
import com.example.appxemphim.Model.DirectorModel;
import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.Request.ReviewRequest;
import com.example.appxemphim.UI.Adapter.ListVideoAdapter;
import com.example.appxemphim.ViewModel.FavouriteViewModel;
import com.example.appxemphim.ViewModel.MovieDetailViewModel;
import com.example.appxemphim.ViewModel.ReviewViewModel;
import com.example.appxemphim.databinding.ActivityMovieDetailsBinding;
import com.google.firebase.Timestamp;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private ProgressDialog progressDialog;
    private ExoPlayer exoPlayer;
    MovieDetailViewModel movieDetailViewModel ;
    FavouriteViewModel favouriteViewModel;
    MovieDetailModel movieDetails;
    PopupWindow popupWindow;
    ListVideoAdapter listVideoAdapter;
    String movie_id_current;

    private  String longText ;
    private String shortText ;
    final boolean[] ismoredes = {false};
    boolean ismorevideo = false;
    private  ReviewViewModel reviewViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        setContentView(binding.getRoot());
        movieDetailViewModel =  new MovieDetailViewModel(new MovieRepository());
        movieDetailViewModel.loadMovieDetail("rMlXfo9TGonjR8NuwNGE");
        movieDetailViewModel.movieDetail.observe(this, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        progressDialog.show();
                        break;

                    case SUCCESS:
                        progressDialog.dismiss();
                        movieDetails = resource.getData();
                        if (movieDetails != null) {
                            setUI(movieDetails);

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



    void setUI(MovieDetailModel movieDetails){
        exoPlayer = new ExoPlayer.Builder(this).build();
        binding.thumbnail.setPlayer(exoPlayer);

        // URL video từ Google Drive
        String videoUrl = movieDetails.getTrailer_url();

        // Tạo MediaItem từ URL video
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));

        // Thiết lập MediaItem cho ExoPlayer
        exoPlayer.setMediaItem(mediaItem);

        // Phát video
        exoPlayer.prepare();
        exoPlayer.play();
        binding.title.setText(movieDetails.getTitle());
        binding.year.setText(convertTimestampToDate(movieDetails.getCreated_at()));
        //des
        longText= movieDetails.getDescription();
        shortText = longText.length() > 50 ? longText.substring(0, 50) + "..." : longText;
        sttTextdescription(shortText, false);
        //actor
        for(ActorModel actorModel: movieDetails.getActors()){
            binding.actor.setText(binding.actor.getText()+ actorModel.getName()+ ", ");
        }
        //dire
        for(DirectorModel directorModel: movieDetails.getDirectors()){
            binding.directors.setText(binding.directors.getText()+ directorModel.getName()+", ");
        }
        //listvideo
        List<VideoModel> videoModels = movieDetails.getVideos();
        Toast.makeText(this, String.valueOf(videoModels.size()), Toast.LENGTH_SHORT).show();
        listVideoAdapter = new ListVideoAdapter(this,videoModels);
        binding.listvideoDetail.setAdapter(listVideoAdapter);
        binding.moreLikeThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ismorevideo = !ismorevideo;
                if (ismorevideo) {

                    binding.moreLikeThis.setText("-"); // đổi sang dấu trừ
                    binding.listvideoDetail.setVisibility(View.VISIBLE); // hiện list
                } else {
                    binding.moreLikeThis.setText("+"); // đổi lại dấu cộng
                    binding.listvideoDetail.setVisibility(View.GONE); // ẩn list
                }
            }
        });


    }

    public String convertTimestampToDate(Timestamp timestamp) {
        if (timestamp == null) return "";

        Date date = timestamp.toDate(); // chuyển Timestamp thành Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    void sttTextdescription(String text, boolean expanded) {
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(text);

        if (longText.length() > 50) {
            String toggle = expanded ? " Thu gọn" : " Xem thêm";
            int start = spannable.length();
            spannable.append(toggle);

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    ismoredes[0] = !ismoredes[0];
                    binding.description.setMaxLines(ismoredes[0] ? Integer.MAX_VALUE : 3);
                    sttTextdescription(ismoredes[0] ? longText : shortText, ismoredes[0]);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    ds.setColor(Color.parseColor("#00BCD4"));
                    ds.setUnderlineText(false);
                }
            };

            spannable.setSpan(clickableSpan, start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        binding.description.setText(spannable);
        binding.description.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void addMyList(View view) {
//        favouriteViewModel = new FavouriteViewModel( MovieDetailsActivity.this);
//        favouriteViewModel.addfavourite("rMlXfo9TGonjR8NuwNGE");
//        favouriteViewModel.addmovieinfavourite.observe(this, resource ->{
//            if (resource != null) {
//                switch (resource.getStatus()) {
//                    case LOADING:
//                        progressDialog.show();
//                        break;
//
//                    case SUCCESS:
//                        // Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
//                        progressDialog.dismiss();
//                        String movieDetails = resource.getData();
//                        if (movieDetails != null) {
//                            Toast.makeText(this, movieDetails, Toast.LENGTH_SHORT).show();
//                        }
//                        break;
//                    case ERROR:
//                        progressDialog.dismiss();
//                        Toast.makeText(this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });

    }

    public void rate(View view) {
        showRatingPopup();
    }

    private void showRatingPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_rating, null);
        ImageView imageView = popupView.findViewById(R.id.btnClose);
        Button submit = popupView.findViewById(R.id.btnSubmit);
        RatingBar ratingBar = popupView.findViewById(R.id.ratingBar);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(1f);
        ratingBar.setScaleX(1.4f);
        ratingBar.setScaleY(1.4f);

        // Tạo popup chiếm 70% chiều cao
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenHeight = displayMetrics.heightPixels;
        int popupHeight = (int) (screenHeight * 0.3);

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
                reviewViewModel = new ReviewViewModel(MovieDetailsActivity.this);
                reviewViewModel.addReview(reviewRequest);
                reviewViewModel.addDataReview.observe(MovieDetailsActivity.this,resource->{
                    if (resource != null) {
                        switch (resource.getStatus()) {
                            case LOADING:
                                progressDialog.show();
                                break;

                            case SUCCESS:// Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
                                progressDialog.dismiss();
                                String movieDetails = resource.getData();
                                if (movieDetails != null) {
                                    Toast.makeText(MovieDetailsActivity.this, movieDetails, Toast.LENGTH_SHORT).show();
                                    popupWindow.dismiss();
                                }
                                break;
                            case ERROR:
                                progressDialog.dismiss();
                                Toast.makeText(MovieDetailsActivity.this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });


            }
        });


    }


}