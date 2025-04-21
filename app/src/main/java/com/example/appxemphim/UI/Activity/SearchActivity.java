package com.example.appxemphim.UI.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.ViewModel.MovieSearchViewModel;
import com.example.appxemphim.UI.Adapter.SearchAdapter;
import com.example.appxemphim.ViewModel.MovieSearchViewModelFactory;
import com.example.appxemphim.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchAdapter searchAdapter;
    private MovieSearchViewModel searchViewModel;
    private MovieRepository movieRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        observeData();
        setupSearch();

        // Load default data (all titles) on start
        searchViewModel.loadDataSearch(null, null, null, null, null);
    }
    private void init() {

        searchAdapter = new SearchAdapter();
        binding.rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvMovies.setAdapter(searchAdapter);

        movieRepository=new MovieRepository();
        MovieSearchViewModelFactory factory = new MovieSearchViewModelFactory(movieRepository);
        searchViewModel = new ViewModelProvider(this, factory).get(MovieSearchViewModel.class);
        searchViewModel.result.observe(this, movies -> searchAdapter.submitList(movies.getData()));
    }

    private void observeData(){
        searchViewModel.result.observe(this,movies->{
            if (movies.getStatus() == Resource.Status.SUCCESS && movies.getData() != null) {
                searchAdapter.submitList(movies.getData());
                Toast.makeText(this, "success loading movies: " + movies.getData().size(), Toast.LENGTH_SHORT).show();
            } else if (movies.getStatus() == Resource.Status.ERROR) {
                Toast.makeText(this, "Error loading movies: " + movies.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                    searchViewModel.loadDataSearch(query,null,null,null,null);
                }
                else{
                    searchViewModel.loadDataSearch(null, null, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
