package com.example.appxemphim.UI.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.UI.Adapter.CarouselAdapter;
import com.example.appxemphim.UI.Adapter.MovieAdapter;
import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.ViewModel.FilterManager;
import com.example.appxemphim.ViewModel.MovieViewModel;
import com.example.appxemphim.UI.Utils.SpaceItemDecoration;
import com.example.appxemphim.UI.Activity.MovieDetailActivity;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Activity.SearchActivity;
import com.example.appxemphim.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private TopResultsFragment topResultsFragment;
    private CarouselAdapter carouselAdapter;
    private MovieAdapter topRatedMovieAdapter;
    private MovieViewModel movieViewModel;
    private FragmentDashboardBinding binding;
    private FilterManager filterManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initializeUI();
        setupListeners();
        setUpSpinner();
        observeData();
        movieViewModel.loadTopRatedData();
        movieViewModel.loadHotData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        topResultsFragment=(TopResultsFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView);
        setupFilterListeners(); // Thiết lập lắng nghe sự kiện cho chip và spinner

        movieViewModel.loadAllMovies();
    }

    private void initializeUI() {
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        filterManager = new FilterManager();


        // Carousel setup
        binding.recyclerViewCarousel.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        carouselAdapter = new CarouselAdapter(requireContext(), movie ->
                MovieDetailActivity.showMovieDetailActivity(requireActivity(), movie)
        );
        binding.recyclerViewCarousel.setAdapter(carouselAdapter);
        binding.recyclerViewCarousel.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewCarousel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager == null) return;

                    int currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition();

                    if (currentPosition != RecyclerView.NO_POSITION) {
                        MovieUIModel currentMovie = carouselAdapter.getMovieAt(currentPosition); // Sử dụng getItem thay vì getListMovie
                        binding.textNameFilm.setText(currentMovie.getTitle());
                        binding.textYearFilmPublish.setText(currentMovie.getYear());
                        binding.textRating.setText(currentMovie.getRating());
                    }
                }
            }
        });

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.recyclerViewCarousel);

        // Top-rated movies setup
        topRatedMovieAdapter = new MovieAdapter(requireContext(), new ArrayList<>(), this::showMovieToast);
        binding.recyclerViewTopRated.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewTopRated.setAdapter(topRatedMovieAdapter);
        binding.recyclerViewTopRated.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_spacing)));
    }

    private void setUpSpinner(){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(
          requireContext(),R.array.category_array,R.layout.spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categorySpinner.setAdapter(adapter);
    }

    /** Thiết lập lắng nghe sự kiện cho ChipGroup và Spinner */
    private void setupFilterListeners() {

        // Listener cho Spinner (category filter)
        binding.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                filterManager.updateCategoryFilter(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                filterManager.updateCategoryFilter(null);
            }

        });
    }


    private void observeData() {
        // Quan sát danh sách phim gốc từ MovieViewModel
        movieViewModel.getAllMovies().observe(getViewLifecycleOwner(), resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS && resource.getData() != null) {
                filterManager.setAllMovies(resource.getData());
            }
        });

        // Quan sát danh sách phim đã lọc từ FilterManager
        filterManager.getFilteredMovies().observe(getViewLifecycleOwner(), filteredMovies -> {
            if (topResultsFragment != null) {
                topResultsFragment.updateFilteredMovies(filteredMovies);

                if (topResultsFragment.isHidden()) {
                    getChildFragmentManager().beginTransaction()
                            .show(topResultsFragment)
                            .commit();
                }

                binding.fragmentContainerView.setVisibility(View.VISIBLE);
            }
        });

        // Quan sát danh sách phim hot cho carousel
        movieViewModel.getHotMovieList().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        Log.d("DashboardFragment", "Loading hot movies...");
                        Toast.makeText(requireContext(), "Loading hot movies...", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        if (resource.getData() != null) {
                            carouselAdapter.submitList(resource.getData());
                            Log.d("DashboardFragment", "Hot movies loaded successfully");
                        }
                        break;
                    case ERROR:
                        carouselAdapter.submitList(new ArrayList<>());
                        Log.e("DashboardFragment", "Error loading hot movies: " + resource.getMessage());
                        Toast.makeText(requireContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        // Quan sát danh sách phim top-rated
        movieViewModel.getTopRatedMovieList().observe(getViewLifecycleOwner(), resource -> {
            if (resource != null) {
                switch (resource.getStatus()) {
                    case LOADING:
                        Toast.makeText(requireContext(), "Loading top-rated movies...", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        if (resource.getData() != null) {
                            topRatedMovieAdapter.updateMovieList(resource.getData());
                        }
                        break;
                    case ERROR:
                        topRatedMovieAdapter.updateMovieList(new ArrayList<>());
                        Toast.makeText(requireContext(), resource.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void showMovieToast(MovieUIModel movie) {
        Toast.makeText(requireContext(), "Selected: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void setupListeners() {
        binding.btnSearch.setOnClickListener(v ->
                SearchActivity.showSearchActivity(requireActivity())
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.recyclerViewCarousel.clearOnScrollListeners();
        binding.recyclerViewCarousel.setAdapter(null);
        binding.recyclerViewTopRated.setAdapter(null);
        binding = null;
    }
}