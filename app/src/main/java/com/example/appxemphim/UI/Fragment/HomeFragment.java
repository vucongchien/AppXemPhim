package com.example.appxemphim.UI.Fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.Model.DTO.EpisodeInfoDTO;
import com.example.appxemphim.R;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.Repository.ShowTimeRepository;
import com.example.appxemphim.UI.Activity.MovieDetailsActivity;
import com.example.appxemphim.UI.Activity.SearchActivity;
import com.example.appxemphim.UI.Adapter.CarouselAdapter;
import com.example.appxemphim.UI.Adapter.PopularAdapter;
import com.example.appxemphim.UI.Adapter.ShowtimeAdapter;
import com.example.appxemphim.UI.Utils.SpaceItemDecoration;
import com.example.appxemphim.UI.Utils.UIStateHandler;
import com.example.appxemphim.ViewModel.MovieForHomeViewModel;
import com.example.appxemphim.ViewModel.MovieForHomeViewModelFactory;
import com.example.appxemphim.ViewModel.ShowtimeViewModel;
import com.example.appxemphim.ViewModel.ShowtimeViewModelFactory;
import com.example.appxemphim.databinding.FragmentHomeBinding;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private PopularAdapter popularAdapter, forYouAdapter, onlyAdapter;
    private ShowtimeAdapter showtimeAdapter;
    private CarouselAdapter carouselAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoScrollRunnable;
    private List<EpisodeInfoDTO> allShowtimes = null;
    private MovieForHomeViewModel movieViewModel;
    private ShowtimeViewModel showtimeViewModel;
    private UIStateHandler showtimeUIStateHandler,carouselUIStateHandler;
    private String selectedGenre = null;
    private Integer selectedYear = null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initViews();
        initData();
        highlightTodayChip();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setChipWidthByPercentage();
        setTextViewSizeByPercent();
    }

    private void highlightTodayChip() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        Chip chipToHighlight = null;
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                chipToHighlight = binding.showtimeMonday;
                break;
            case Calendar.TUESDAY:
                chipToHighlight = binding.showtimeTuesday;
                break;
            case Calendar.WEDNESDAY:
                chipToHighlight = binding.showtimeWednesday;
                break;
            case Calendar.THURSDAY:
                chipToHighlight = binding.showtimeThursday;
                break;
            case Calendar.FRIDAY:
                chipToHighlight = binding.showtimeFriday;
                break;
            case Calendar.SATURDAY:
                chipToHighlight = binding.showtimeSaturday;
                break;
            case Calendar.SUNDAY:
                chipToHighlight = binding.showtimeSunday;
                break;
        }

        if (chipToHighlight != null) {
            chipToHighlight.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));
        }
    }

    private void setChipWidthByPercentage() {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int chipWidth = (int) (screenWidth * 0.142);

        Chip[] allChips = {
                binding.showtimeMonday,
                binding.showtimeTuesday,
                binding.showtimeWednesday,
                binding.showtimeThursday,
                binding.showtimeFriday,
                binding.showtimeSaturday,
                binding.showtimeSunday
        };

        for (Chip chip : allChips) {
            ViewGroup.LayoutParams params = chip.getLayoutParams();
            params.width = chipWidth;
            chip.setLayoutParams(params);
        }
    }

    private void setTextViewSizeByPercent() {
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int targetHeight = (int) (screenHeight * 0.05);

        TextView[] textViews = {
                binding.popular,
                binding.forYou,
                binding.onlyApp,
                binding.showtimeTitle
        };

        for (TextView textView : textViews) {
            ViewGroup.LayoutParams params = textView.getLayoutParams();
            params.height = targetHeight;
            textView.setLayoutParams(params);
        }
    }

    private void initViews(){
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        showtimeUIStateHandler =new UIStateHandler(binding.showtimeProcessBar,binding.showtimeImageErr,binding.showtimeImageNotFound,binding.recyclerViewShowtime);
        carouselUIStateHandler = new UIStateHandler(binding.carouselLoading,binding.carouselError,binding.carouselNotFound,binding.carousel);
        carouselAdapter = new CarouselAdapter();
        carouselAdapter.setOnMovieClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id", movieId);
            startActivity(intent);
        });
        binding.carousel.setAdapter(carouselAdapter);
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int itemCount = carouselAdapter.getItemCount();
                int nextItem = 0;
                if (itemCount > 0) {
                    nextItem = (binding.carousel.getCurrentItem() + 1) % itemCount;
                    binding.carousel.setCurrentItem(nextItem, true);
                    handler.postDelayed(this, 10000);
                }
            }
        };
        handler.postDelayed(autoScrollRunnable, 10000);
        binding.carousel.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleY(scale);
            page.setAlpha(0.5f + (1 - Math.abs(position)) * 0.5f);
        });

        showtimeAdapter = new ShowtimeAdapter();
        binding.recyclerViewShowtime.setAdapter(showtimeAdapter);
        binding.recyclerViewShowtime.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewPopular.setNestedScrollingEnabled(false);
        popularAdapter = new PopularAdapter();
        popularAdapter.setOnMovieClickListener(movieId -> {
            Intent intent=new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id",movieId);
            startActivity(intent);
        });
        popularAdapter.setOnMovieClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id", movieId);
            startActivity(intent);
        });
        binding.recyclerViewPopular.setAdapter(popularAdapter);
        binding.recyclerViewPopular.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        binding.recyclerViewRetrotv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewRetrotv.setNestedScrollingEnabled(false);
        forYouAdapter = new PopularAdapter();
        forYouAdapter.setOnMovieClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id", movieId);
            startActivity(intent);
        });
        binding.recyclerViewRetrotv.setAdapter(forYouAdapter);
        binding.recyclerViewRetrotv.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        binding.recyclerViewOnlyApp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewOnlyApp.setNestedScrollingEnabled(false);
        onlyAdapter = new PopularAdapter();
        onlyAdapter.setOnMovieClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id", movieId);
            startActivity(intent);
        });
        binding.recyclerViewOnlyApp.setAdapter(onlyAdapter);
        binding.recyclerViewOnlyApp.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        binding.headerButton2.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchActivity.class);
            startActivity(intent);
        });

        binding.btnCancelled.setOnClickListener(v -> {
            selectedGenre = null;
            selectedYear = null;
            binding.btnCategories.setText("Categories");
            binding.btnYear.setText("Year");
            binding.btnCancelled.setVisibility(View.GONE);
            updateCarouselFromFilter(); // reset lại carousel
        });

        binding.btnCategories.setOnClickListener(v -> {
            CategoryDialogFragment dialog = new CategoryDialogFragment(genre -> {
                selectedGenre = genre;
                binding.btnCategories.setText(genre);
                if(binding.btnCancelled.getVisibility() == View.GONE) {
                    binding.btnCancelled.setVisibility(View.VISIBLE);
                }

                updateCarouselFromFilter();
            });
            dialog.show(getParentFragmentManager(), "CategoryDialog");

        });

        binding.btnYear.setOnClickListener(v -> {
            YearDialogFragment dialog = new YearDialogFragment(year -> {
                selectedYear = Integer.valueOf(year); // giả sử year là String số
                binding.btnYear.setText(year);
                if(binding.btnCancelled.getVisibility() == View.GONE) {
                    binding.btnCancelled.setVisibility(View.VISIBLE);
                }

                updateCarouselFromFilter();
            });
            dialog.show(getParentFragmentManager(), "YearDialog");

        });

        showtimeAdapter.setOnShowtimeClickListener(movieId -> {
            Intent intent = new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id", movieId);
            intent.putExtra("autoplay", true);
            startActivity(intent);
        });


    }

    protected void initData() {
        MovieRepository repository = new MovieRepository();
        MovieForHomeViewModelFactory factory = new MovieForHomeViewModelFactory(repository);
        movieViewModel = new ViewModelProvider(this, factory).get(MovieForHomeViewModel.class);

        movieViewModel.popular.observe(requireActivity(), movies -> {
            popularAdapter.submitList(movies.getData());
        });

        observeCarousel();

        movieViewModel.forYou.observe(requireActivity(), movies -> forYouAdapter.submitList(movies.getData()));
        movieViewModel.only.observe(requireActivity(), movies -> onlyAdapter.submitList(movies.getData()));

        movieViewModel.loadDataPopular();
        movieViewModel.loadDataForYou();
        movieViewModel.loadDataOnly();
        movieViewModel.loadDataCarousels(new ArrayList<>(),new ArrayList<>());

        ShowTimeRepository showtimeRepository = new ShowTimeRepository();
        ShowtimeViewModelFactory showtimeFactory = new ShowtimeViewModelFactory(showtimeRepository);
        showtimeViewModel = new ViewModelProvider(this, showtimeFactory).get(ShowtimeViewModel.class);

        // Load lịch chiếu hôm nay
        Calendar calendar = Calendar.getInstance();
        int todayIndex = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7; // convert SUNDAY = 1 to 6, MONDAY = 2 to 0,...

        observeShowtimeForDay(todayIndex);  // tự động hiển thị hôm nay
        setChipClickListener(binding.showtimeMonday, 0);
        setChipClickListener(binding.showtimeTuesday, 1);
        setChipClickListener(binding.showtimeWednesday, 2);
        setChipClickListener(binding.showtimeThursday, 3);
        setChipClickListener(binding.showtimeFriday, 4);
        setChipClickListener(binding.showtimeSaturday, 5);
        setChipClickListener(binding.showtimeSunday, 6);

        showtimeViewModel.showTimesLiveData.observe(getViewLifecycleOwner(), showTimeResource -> {
        });
    }
    private void setChipClickListener(Chip chip, int dayIndex) {
        chip.setOnClickListener(v -> observeShowtimeForDay(dayIndex));

    }

    private void updateCarouselFromFilter() {
        List<String> genres = new ArrayList<>();
        if (selectedGenre != null && !selectedGenre.equals("Categories")) {
            genres.add(selectedGenre);
        }

        List<Integer> years = new ArrayList<>();
        if (selectedYear != null) {
            years.add(selectedYear);
        }

        movieViewModel.loadDataCarousels(genres, years);
    }

    private void observeCarousel(){
        movieViewModel.carousels.observe(getViewLifecycleOwner(),data->{
            if(data!=null){
                switch (data.getStatus()) {
                    case LOADING:
                        carouselUIStateHandler.showLoading();
                        break;
                    case SUCCESS:
                        if (data.getData() != null && !data.getData().isEmpty()) {
                            carouselAdapter.submitList(data.getData());
                            carouselUIStateHandler.showData();
                        } else {
                            carouselUIStateHandler.showNotFound();
                        }
                        break;

                    case ERROR:
                    default:
                        carouselUIStateHandler.showError();
                        break;
                }
            }
        });
    }

    private void observeShowtimeForDay(int dayIndex) {
        showtimeViewModel.getShowTimesByWeekday(dayIndex).observe(getViewLifecycleOwner(), showTimeResource -> {
            if (showTimeResource != null) {
                switch (showTimeResource.getStatus()) {
                    case LOADING:
                        showtimeUIStateHandler.showLoading();
                        break;
                    case SUCCESS:
                        if (showTimeResource.getData() != null && !showTimeResource.getData().isEmpty()) {
                            showtimeAdapter.submitList(showTimeResource.getData());
                            showtimeUIStateHandler.showData();
                        } else {
                            showtimeUIStateHandler.showNotFound();
                        }
                        break;

                    case ERROR:
                    default:
                        showtimeUIStateHandler.showError();
                        break;
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(autoScrollRunnable);
        binding = null;
    }
}