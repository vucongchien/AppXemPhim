package com.example.appxemphim.UI.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appxemphim.ViewModel.MovieSearchViewModel;
import com.example.appxemphim.UI.Adapter.SearchAdapter;
import com.example.appxemphim.databinding.ActivitySearchBinding;
import androidx.activity.EdgeToEdge;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchAdapter searchAdapter;
    private MovieSearchViewModel searchViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        searchAdapter = new SearchAdapter();
        initViews();
        initData();
    }

    protected void initViews() {
        EdgeToEdge.enable(this);

        binding.rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvMovies.setAdapter(searchAdapter);
    }

    protected void initData() {
        searchViewModel = new ViewModelProvider(this).get(MovieSearchViewModel.class);
        searchViewModel.result.observe(this, movies -> searchAdapter.submitList(movies.getData()));
        // Optionally trigger initial fetch:
        // searchViewModel.loadDataSearch(null, null, null, null, null, 1, 20);
    }
}
