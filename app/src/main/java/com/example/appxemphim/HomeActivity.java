package com.example.appxemphim;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.UI.Interface.OnMovieClickListener;
import com.example.appxemphim.UI.MovieAdapter;
import com.example.appxemphim.UI.MovieUIModel;
import com.example.appxemphim.UI.MovieViewModel;
import com.example.appxemphim.UI.SpaceItemDecoration;
import com.example.appxemphim.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private MovieAdapter movieAdapter;
    private MovieViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        initializeUI();
        setupListeners();
        observeData();
        viewModel.loadMovieData();
    }

    private void initializeUI() {

        binding.recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        movieAdapter = new MovieAdapter(this, new ArrayList<>(), movie -> showMovieToast(movie));
        binding.recyclerViewTopRated.setAdapter(movieAdapter);

        //them space giua cac movie item
        int spacing = getResources().getDimensionPixelOffset(R.dimen.item_spacing);
        binding.recyclerViewTopRated.addItemDecoration(new SpaceItemDecoration(spacing));
    }

    private void observeData() {
        viewModel.getMovieList().observe(this, resource -> {
            switch (resource.status){
                case "LOADING":
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
                    break;
                case "SUCCESS":
                    movieAdapter.updateMovieList(resource.data);
                    break;
                case "ERROR":
                    movieAdapter.updateMovieList(new ArrayList<>());
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void setupListeners(){
        // Xử lý sự kiện cho nút Search
        binding.btnSearch.setOnClickListener(v ->
                Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show()
        );

        // Xử lý sự kiện cho nút Filter
        binding.btnFilter.setOnClickListener(v ->
                Toast.makeText(this, "Filter clicked", Toast.LENGTH_SHORT).show()
        );

        // Xử lý sự kiện cho Bottom Navbar
        binding.btnHome.setOnClickListener(v ->
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show()
        );
        binding.btnListFilmLove.setOnClickListener(v ->
                Toast.makeText(this, "List Film Love clicked", Toast.LENGTH_SHORT).show()
        );
        binding.btnProfile.setOnClickListener(v ->
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
        );


    }

    private void showMovieToast(MovieUIModel movie) {
        Toast.makeText(this, "Selected: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}