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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.HOME_FOLDER.CarouselAdapter;
import com.example.appxemphim.HOME_FOLDER.MovieAdapter;
import com.example.appxemphim.HOME_FOLDER.MovieUIModel;
import com.example.appxemphim.HOME_FOLDER.MovieViewModel;
import com.example.appxemphim.HOME_FOLDER.SpaceItemDecoration;
import com.example.appxemphim.R;
import com.example.appxemphim.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private CarouselAdapter carouselAdapter;
    private MovieAdapter topRatedMovieAdapter;
    private MovieViewModel movieViewModel;
    private FragmentDashboardBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initializeUI();
        setupListeners();
        observeData();
        movieViewModel.LoadTopRatedData();
        movieViewModel.LoadHotData();

        return view;
    }

    private void initializeUI() {
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Carousel setup
        binding.recyclerViewCarousel.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        carouselAdapter = new CarouselAdapter(requireContext(), new ArrayList<>(), this::showMovieDetailFragment);
        binding.recyclerViewCarousel.setAdapter(carouselAdapter);
        binding.recyclerViewCarousel.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewCarousel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager==null) return;

                    int currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition();

                    if (currentPosition!= RecyclerView.NO_POSITION){
                        MovieUIModel currentMovie=carouselAdapter.getListMovie().get(currentPosition);

                        binding.textNameFilm.setText(currentMovie.getTitle());
                        binding.textYearFilmPublish.setText(currentMovie.getYear());
                        binding.textRating.setText(currentMovie.getRating());

                    }

                }
            }
        });


        PagerSnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerViewCarousel);



        // Top-rated movies setup
        topRatedMovieAdapter = new MovieAdapter(requireContext(), new ArrayList<>(), this::showMovieToast);
        binding.recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewTopRated.setAdapter(topRatedMovieAdapter);
        binding.recyclerViewTopRated.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_spacing)));
    }

    private void observeData() {
        // Quan sát danh sách phim hot cho carousel
        movieViewModel.getHotMovieList().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case "LOADING":
                        Toast.makeText(requireContext(), "Loading hot movies...", Toast.LENGTH_SHORT).show();
                        break;
                    case "SUCCESS":
                        if (resource.data != null) {
                            carouselAdapter.updateHotMovieList(resource.data);
                        }
                        break;
                    case "ERROR":
                        carouselAdapter.updateHotMovieList(new ArrayList<>());
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        // Quan sát danh sách phim top-rated
        movieViewModel.getTopRatedMovieList().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.status) {
                    case "LOADING":
                        Toast.makeText(requireContext(), "Loading top-rated movies...", Toast.LENGTH_SHORT).show();
                        break;
                    case "SUCCESS":
                        if (resource.data != null) {
                            topRatedMovieAdapter.updateMovieList(resource.data);
                        }
                        break;
                    case "ERROR":
                        topRatedMovieAdapter.updateMovieList(new ArrayList<>());
                        Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void showMovieToast(MovieUIModel movie) {
        Toast.makeText(requireContext(), "Selected: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void showMovieDetailFragment(MovieUIModel movieUIModel){
        MovieDetailFragment movieDetailFragment=MovieDetailFragment.newInstance(movieUIModel);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, movieDetailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setupListeners() {
        binding.btnSearch.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Search clicked", Toast.LENGTH_SHORT).show()
        );

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