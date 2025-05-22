package com.example.appxemphim.UI.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.ViewModel.MovieSearchViewModel;
import com.example.appxemphim.UI.Adapter.SearchAdapter;
import com.example.appxemphim.ViewModel.MovieSearchViewModelFactory;
import com.example.appxemphim.databinding.ActivitySearchBinding;
import androidx.activity.EdgeToEdge;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchAdapter searchAdapter;
    private MovieSearchViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        searchAdapter = new SearchAdapter();
        initViews();
        initData();

        setupSearch();
    }

    protected void initViews() {
        EdgeToEdge.enable(this);

        binding.rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvMovies.setAdapter(searchAdapter);
        binding.ivBack.setOnClickListener(v->finish());
    }

    protected void initData() {
        // Khởi tạo repository của bạn, nếu dùng singleton thì gọi qua getter
        MovieRepository repository = new MovieRepository(); // hoặc MovieRepository.getInstance()

        // Tạo factory
        MovieSearchViewModelFactory factory = new MovieSearchViewModelFactory(repository);

        // Lấy ViewModel với factory
        viewModel = new ViewModelProvider(this, factory).get(MovieSearchViewModel.class);

        // Sử dụng viewModel...
        viewModel.result.observe(this, movies -> searchAdapter.submitList(movies.getData()));
        // Optionally trigger initial fetch:
        viewModel.loadDataSearch(null, null, null, null, null, 0, 20);
    }

    private void setupSearch() {
        // Assuming there's a SearchView with id "searchView" in the layout
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query=charSequence.toString().trim();
                Log.d("searchhhhhh", "onTextChanged: "+query);
                if(!query.isEmpty()){
                    viewModel.loadDataSearch(query,null, null, null, null, 0, 20);
                }
                else{
                    viewModel.loadDataSearch(null, null, null, null, null, 0, 20);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
