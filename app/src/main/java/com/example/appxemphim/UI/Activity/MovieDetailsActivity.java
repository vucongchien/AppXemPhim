package com.example.appxemphim.UI.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appxemphim.Model.ActorModel;
import com.example.appxemphim.Model.DirectorModel;
import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Network.MovieApiService;
import com.example.appxemphim.Network.RetrofitClient;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Adapter.ListVideoAdapter;
import com.example.appxemphim.ViewModel.MovieDetailViewModel;
import com.example.appxemphim.databinding.ActivityMovieDetailsBinding;


public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    MovieDetailViewModel movieDetailViewModel;
    MovieDetailModel movieDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        movieDetailViewModel = new MovieDetailViewModel(new MovieRepository());
        movieDetailViewModel.loadMovieDetail("rMlXfo9TGonjR8NuwNGE");
        movieDetails = movieDetailViewModel.movieDetail.getValue().getData();
        binding.description.setText(movieDetails.getDescription());
        binding.title.setText(movieDetails.getTitle());

    }
}