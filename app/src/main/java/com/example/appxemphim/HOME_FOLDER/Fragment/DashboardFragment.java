package com.example.appxemphim.HOME_FOLDER.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.HOME_FOLDER.MovieAdapter;
import com.example.appxemphim.HOME_FOLDER.MovieUIModel;
import com.example.appxemphim.HOME_FOLDER.MovieViewModel;
import com.example.appxemphim.HOME_FOLDER.SpaceItemDecoration;
import com.example.appxemphim.R;
import com.example.appxemphim.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private MovieViewModel movieViewModel;
    private MovieAdapter movieAdapter;
    private FragmentDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        initializeUI();
        setupListeners();
        observeData();
        movieViewModel.loadMovieData();

        return view;
    }

    private void initializeUI(){
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieAdapter = new MovieAdapter(requireContext(), new ArrayList<>(), this::showMovieToast);

        binding.recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewTopRated.setAdapter(movieAdapter);
        binding.recyclerViewTopRated.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_spacing)));
    }

    private void observeData() {
        movieViewModel.getMovieList().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case "LOADING":
                        Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show();
                        break;
                    case "SUCCESS":
                        if (resource.data != null) {
                            movieAdapter.updateMovieList(resource.data);
                        }
                        break;
                    case "ERROR":
                        movieAdapter.updateMovieList(new ArrayList<>());
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    private void showMovieToast(MovieUIModel movie) {
        Toast.makeText(requireContext(), "Selected: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void setupListeners(){
        // Xử lý sự kiện cho nút Search
        binding.btnSearch.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Search clicked", Toast.LENGTH_SHORT).show()
        );

        // Xử lý sự kiện cho nút Filter
        binding.btnFilter.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Filter clicked", Toast.LENGTH_SHORT).show()
        );


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
