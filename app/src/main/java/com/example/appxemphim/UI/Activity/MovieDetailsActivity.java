package com.example.appxemphim.UI.Activity;


import static androidx.core.content.ContentProviderCompat.requireContext;
import static java.security.AccessController.getContext;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.Model.ActorModel;
import com.example.appxemphim.Model.Comment;
import com.example.appxemphim.Model.DirectorModel;
import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.FavouriteRepository;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.Request.CommentRequest;
import com.example.appxemphim.Request.ReviewRequest;
import com.example.appxemphim.UI.Adapter.CommentAdapter;
import com.example.appxemphim.UI.Adapter.VideoAdapter;
import com.example.appxemphim.ViewModel.CommentViewModel;
import com.example.appxemphim.ViewModel.FavouriteViewModel;
import com.example.appxemphim.ViewModel.MovieDetailViewModel;
import com.example.appxemphim.ViewModel.ReviewViewModel;
import com.example.appxemphim.databinding.ActivityMovieDetailsBinding;
import com.google.firebase.Timestamp;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private ExoPlayer exoPlayer;
    private ProgressDialog progressDialog;
    MovieDetailViewModel movieDetailViewModel ;
    PopupWindow popupWindow;
    MovieDetailModel movieDetails;
    private FavouriteViewModel favouriteViewModel;
    private final Map<String,Boolean> userLikedComments = new HashMap();
    private List<Comment> fullCommentList = new ArrayList<>();
    VideoAdapter videoAdapter;
    String movie_id_current;
    private CommentViewModel commentViewModel;
    private CommentAdapter commentAdapter;

    private  String longText ;
    private String shortText ;
    final boolean[] ismoredes = {false};
    boolean ismorevideo = false;
    private  ReviewViewModel reviewViewModel;
    private List<VideoModel> videoModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);
        setContentView(binding.getRoot());
        movie_id_current= getIntent().getStringExtra("movie_id");

        commentViewModel = new CommentViewModel();
        commentViewModel.init(this, movie_id_current);
        movieDetailViewModel =  new MovieDetailViewModel(new MovieRepository());
        movieDetailViewModel.loadMovieDetail(movie_id_current);

        favouriteViewModel = new FavouriteViewModel(new FavouriteRepository(MovieDetailsActivity.this));
        getcomment();
        movieDetailViewModel.movieDetail.observe(this, resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        break;

                    case SUCCESS:
                        movieDetails = resource.getData();
                        if (movieDetails != null) {
                            setUI(movieDetails);

                        }
                        break;

                    case ERROR:
                        Toast.makeText(this, "Có lỗi xảy ra: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        binding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exoPlayer != null) {
                    exoPlayer.release();  // Giải phóng tài nguyên
                    exoPlayer = null;
                }
                finish();
            }
        });
        binding.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exoPlayer != null) {
                    exoPlayer.release();  // Giải phóng tài nguyên
                    exoPlayer = null;
                }
                Intent intent = new Intent(MovieDetailsActivity.this, WatchVideoActivity.class);
                intent.putExtra("video_data", videoModels.get(0));
                startActivity(intent);
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
        videoModels = movieDetails.getVideos();
        for (int i = 0; i < videoModels.size(); i++) {
            videoModels.get(i).setName("Tập " + (i + 1));
        }
        videoAdapter = new VideoAdapter(this,videoModels);
        binding.listvideoDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.listvideoDetail.setAdapter(videoAdapter);
        binding.moreVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ismorevideo = !ismorevideo;
                if (ismorevideo) {
                    binding.moreVideos.setText("Ẩn bớt"); // đổi sang dấu trừ
                    binding.listvideoDetail.setVisibility(View.VISIBLE); // hiện list
                } else {
                    binding.moreVideos.setText("Xem thêm"); // đổi lại dấu cộng
                    binding.listvideoDetail.setVisibility(View.GONE); // ẩn list
                }
            }
        });
        videoAdapter.setOnItemClickListener(video ->{
            if (exoPlayer != null) {
                exoPlayer.release();  // Giải phóng tài nguyên
                exoPlayer = null;
            }
            Intent intent = new Intent(this, WatchVideoActivity.class);
            intent.putExtra("video_data", video);
            startActivity(intent);
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
        favouriteViewModel.addfavorite(movie_id_current,
                ()->{
                    Toast.makeText(this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                },
                ()->{
                    Toast.makeText(this, "Lỗi rùi bạn ơi", Toast.LENGTH_SHORT).show();
                });

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
                reviewRequest.setMovie_id(movie_id_current);
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

    public void getcomment() {

        binding.listComment.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(new CommentAdapter.OnCommentClickListener() {
            @Override
            public void onLikeClick(Comment comment, int position) {
                boolean currentState = userLikedComments.getOrDefault(comment.getCommentId(), false);
                boolean newState = !currentState;
                userLikedComments.put(comment.getCommentId(), newState);
                if (newState) {
                    comment.setLike(comment.getLike() + 1);
                } else {
                    comment.setLike(comment.getLike() - 1);
                }
                commentViewModel.like(movie_id_current, comment.getCommentId(), newState);
                commentAdapter.notifyItemChanged(position);
            }
            @Override
            public void onReplyClick(Comment comment,int position) {
                comment.setReply(!comment.getReply());
                commentAdapter.notifyItemChanged(position);
            }
            @Override
            public void onShowMoreClick(Comment comment, int position) {
                List<Comment> replies = commentViewModel.getDescendants(comment.getCommentId(), fullCommentList);
                comment.setExpanded(!comment.isExpanded());
                if(comment.isExpanded()) {
                    comment.setComments(replies);
                } else {
                    comment.setComments(new ArrayList<>());
                }

                commentAdapter.notifyItemChanged(position);
            }

            @Override
            public void onSendClick(Comment comment, int positon,String message) {
                CommentRequest commentRequest = new CommentRequest();
                commentRequest.setMovie_id(movie_id_current);
                commentRequest.setParent_comment_id(comment.getCommentId());
                commentRequest.setContent(message);
                commentViewModel.sendComment(commentRequest,
                        () -> { // onSuccess
                            Toast.makeText(MovieDetailsActivity.this, "ngon", Toast.LENGTH_SHORT).show();
                        },
                        () -> { // onFailure
                            Toast.makeText(MovieDetailsActivity.this, "dell giòn", Toast.LENGTH_SHORT).show();
                        });
            }

        });

        binding.listComment.setAdapter(commentAdapter);
        commentViewModel.getAllComments().observe(this, comments -> {
            if (comments != null) {
                fullCommentList = comments;
                for (Comment comment : comments) {
                    if (!userLikedComments.containsKey(comment.getCommentId())) {
                        userLikedComments.put(comment.getCommentId(), false);
                    }
                }
                List<Comment> parentComments = new ArrayList<>(commentViewModel.getParentComments(comments));
                for(Comment comment: parentComments){
                    List<Comment> replies = commentViewModel.getDescendants(comment.getCommentId(), fullCommentList);
                    comment.setComments(replies);
                }
                commentAdapter.submitList(parentComments);
            }
        });

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentRequest commentRequest = new CommentRequest();
                commentRequest.setMovie_id(movie_id_current);
                commentRequest.setParent_comment_id("");
                commentRequest.setContent(binding.editMessage.getText().toString().trim());
                commentViewModel.sendComment(commentRequest,
                        () -> { // onSuccess
                            Toast.makeText(MovieDetailsActivity.this, "ngon", Toast.LENGTH_SHORT).show();
                        },
                        () -> { // onFailure
                            Toast.makeText(MovieDetailsActivity.this, "dell giòn", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }

}