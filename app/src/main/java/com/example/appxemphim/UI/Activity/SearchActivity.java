package com.example.appxemphim.UI.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appxemphim.R;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Fragment.FilterBottomSheetDialogFragment;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.ViewModel.MovieSearchViewModel;
import com.example.appxemphim.UI.Adapter.SearchAdapter;
import com.example.appxemphim.ViewModel.MovieSearchViewModelFactory;
import com.example.appxemphim.databinding.ActivitySearchBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.activity.EdgeToEdge;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchAdapter searchAdapter;
    private MovieSearchViewModel viewModel;
    private Handler debounceHandler = new Handler();
    private Runnable debounceRunnable;
    private static final long DEBOUNCE_DELAY = 300;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        searchAdapter = new SearchAdapter();
        initViews();
        initViewModel();

        setupSearch();
        setupFilter();
    }

    protected void initViews() {
        EdgeToEdge.enable(this);

        binding.rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvMovies.setAdapter(searchAdapter);
        binding.ivBack.setOnClickListener(v->finish());
    }

    private void initViewModel() {
        MovieRepository repository = new MovieRepository();
        MovieSearchViewModelFactory factory = new MovieSearchViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(MovieSearchViewModel.class);

        // Observe search results
        viewModel.result.observe(this, resource -> {
            if (resource.getStatus()== Resource.Status.SUCCESS) {
                searchAdapter.submitList(resource.getData());
            } else if (resource.getStatus()== Resource.Status.ERROR) {

                Toast.makeText(SearchActivity.this, "Lỗi: " + resource.getMessage(), Toast.LENGTH_SHORT).show();
            } else if (resource.getStatus()== Resource.Status.LOADING) {
                // (Tùy chọn) Hiển thị ProgressBar khi đang tải
                // Ví dụ: progressBar.setVisibility(View.VISIBLE);2
            }
        });

        // Observe combined filters and query
        viewModel.getFilterLiveData().observe(this, filter -> {
            viewModel.loadDataSearchWithFilter(filter);
        });

        // Initial load with empty filters
        viewModel.setQuery(null);
        viewModel.setGenres(null);
        viewModel.setYears(null);
        viewModel.setNations(null);
        viewModel.setMinRating(null);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (debounceRunnable != null) {
                    debounceHandler.removeCallbacks(debounceRunnable);
                }
                debounceRunnable = () -> viewModel.setQuery(s.toString().trim());
                debounceHandler.postDelayed(debounceRunnable, DEBOUNCE_DELAY);
            }
        });
    }

    private void setupFilter() {
        binding.ivFilter.setOnClickListener(v -> {
            FilterBottomSheetDialogFragment sheet = new FilterBottomSheetDialogFragment();
            sheet.setOnFilterSelectedListener(data -> {
                viewModel.setGenres(data.getGenres());
                viewModel.setYears(data.getYears());
                viewModel.setNations(data.getNations());
                viewModel.setMinRating(data.getMinRating());
            });
            sheet.show(getSupportFragmentManager(), sheet.getTag());
        });
    }


}
