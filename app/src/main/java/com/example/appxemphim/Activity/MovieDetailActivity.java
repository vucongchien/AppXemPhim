package com.example.appxemphim.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.R;

public class MovieDetailActivity extends AppCompatActivity {
    private MovieUIModel movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lấy dữ liệu phim từ Intent
        if (getIntent() != null && getIntent().hasExtra("movie")) {
            movie = getIntent().getParcelableExtra("movie");
        }

        // Hiển thị thông tin phim
        if (movie != null) {
            TextView movieTitle = findViewById(R.id.movieTitle);
            movieTitle.setText(movie.getTitle());

            // Tải ảnh phim bằng Glide
//            ImageView movieImage = findViewById(R.id.movieImage);
//            Glide.with(this).load(movie.getImageUrl()).into(movieImage);
        }
    }

    public static void showMovieDetailActivity(Activity activity, MovieUIModel movie) {
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        activity.startActivity(intent);
    }


}
