package com.example.appxemphim.UI.Activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Adapter.ListVideoAdapter;
import com.example.appxemphim.ViewModel.MovieDetailViewModel;
import com.example.appxemphim.databinding.ActivityMovieDetailsBinding;
import com.example.appxemphim.databinding.ActivityTestterBinding;

import java.util.List;

public class testter extends AppCompatActivity {
    private ActivityTestterBinding binding;
    private android.app.ProgressDialog progressDialog;
    ListVideoAdapter listVideoAdapter;
    MovieDetailViewModel movieDetailViewModel ;
    MovieDetailModel movieDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestterBinding.inflate(getLayoutInflater());
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
                        // Khi dữ liệu thành công, lấy dữ liệu và hiển thị lên UI
                        progressDialog.dismiss();
                        movieDetails = resource.getData();
                        if (movieDetails != null) {
                            List<VideoModel> videoModels = movieDetails.getVideos();
                            Toast.makeText(this, String.valueOf(videoModels.size()), Toast.LENGTH_SHORT).show();
                            listVideoAdapter = new ListVideoAdapter(this,videoModels);
                            binding.listitem.setAdapter(listVideoAdapter);

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
}