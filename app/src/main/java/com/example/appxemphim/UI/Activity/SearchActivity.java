package com.example.appxemphim.UI.Activity;

import android.view.LayoutInflater;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appxemphim.UI.BaseActivity;
import com.example.appxemphim.ViewModel.MovieSearchViewModel;
import com.example.appxemphim.UI.Adapter.SearchAdapter;
import com.example.appxemphim.databinding.ActivitySearchBinding;

import dagger.hilt.android.AndroidEntryPoint;
import androidx.activity.EdgeToEdge;

import javax.inject.Inject;

@AndroidEntryPoint
public class SearchActivity extends BaseActivity<ActivitySearchBinding> {

    @Inject
    SearchAdapter searchAdapter;
    private MovieSearchViewModel searchViewModel;

    @Override
    protected ActivitySearchBinding inflateBinding(LayoutInflater inflater) {
        return ActivitySearchBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        EdgeToEdge.enable(this);
        binding.rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvMovies.setAdapter(searchAdapter);
    }

    @Override
    protected void initData() {
        searchViewModel = new ViewModelProvider(this).get(MovieSearchViewModel.class);
        searchViewModel.result.observe(this, movies -> searchAdapter.submitList(movies.getData()));
        // Optionally trigger initial fetch:
        // searchViewModel.loadDataSearch(null, null, null, null, null, 1, 20);
    }
}
