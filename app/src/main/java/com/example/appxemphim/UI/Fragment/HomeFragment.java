package com.example.appxemphim.UI.Fragment;

import android.content.Context;
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

import com.example.appxemphim.R;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Activity.HomeActivity;
import com.example.appxemphim.UI.Activity.MovieDetailsActivity;
import com.example.appxemphim.UI.Activity.SearchActivity;
import com.example.appxemphim.UI.Adapter.CarouselAdapter;
import com.example.appxemphim.UI.Adapter.PopularAdapter;
import com.example.appxemphim.UI.Utils.Resource;
import com.example.appxemphim.UI.Utils.SpaceItemDecoration;
import com.example.appxemphim.ViewModel.MovieForHomeViewModel;
import com.example.appxemphim.ViewModel.MovieForHomeViewModelFactory;
import com.example.appxemphim.databinding.FragmentHomeBinding;
import com.google.android.material.chip.Chip;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private PopularAdapter popularAdapter, retroAdapter, onlyAdapter,showtimeAdapter;
    private CarouselAdapter carouselAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoScrollRunnable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Khởi tạo binding
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
        // Lấy ngày hôm nay
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Ánh xạ Calendar.DAY_OF_WEEK với Chip tương ứng
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

        // Đổi màu text nếu chip hợp lệ
        if (chipToHighlight != null) {
            chipToHighlight.setTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_blue_light));
        }
    }
    private void setChipWidthByPercentage() {
        // Lấy chiều rộng màn hình
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        // Tính chiều rộng mỗi chip = 14.2% (100 / 7)
        int chipWidth = (int) (screenWidth * 0.142); // hoặc 0.14 tùy bạn

        // Danh sách các chip
        Chip[] allChips = {
                binding.showtimeMonday,
                binding.showtimeTuesday,
                binding.showtimeWednesday,
                binding.showtimeThursday,
                binding.showtimeFriday,
                binding.showtimeSaturday,
                binding.showtimeSunday
        };

        // Áp dụng chiều rộng cho từng chip
        for (Chip chip : allChips) {
            ViewGroup.LayoutParams params = chip.getLayoutParams();
            params.width = chipWidth;
            chip.setLayoutParams(params);
        }
    }
    private void setTextViewSizeByPercent() {
        // Lấy chiều rộng và cao màn hình
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        // Tính % chiều rộng và chiều cao mong muốn
        int targetHeight = (int) (screenHeight * 0.05);

        // List các TextView cần set
        TextView[] textViews = {
                binding.popular,
                binding.retroTv,
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
        //Carousel
        carouselAdapter=new CarouselAdapter();
        carouselAdapter.setOnMovieClickListener(movieId -> {
            Intent intent=new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id",movieId);
            startActivity(intent);
        });

        binding.carousel.setAdapter(carouselAdapter);
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {

                int itemCount = carouselAdapter.getItemCount();
                int nextItem=0;
                if (itemCount > 0) {
                    nextItem = (binding.carousel.getCurrentItem() + 1) % itemCount;
                    binding.carousel.setCurrentItem(nextItem, true);
                    handler.postDelayed(this, 10000);
                }
                binding.carousel.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(autoScrollRunnable, 10000);
        binding.carousel.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleY(scale);
            page.setAlpha(0.5f + (1 - Math.abs(position)) * 0.5f);
        });

        // RecyclerView Showtime (Lịch chiếu)
        binding.recyclerViewShowtime.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewShowtime.setNestedScrollingEnabled(false);
        showtimeAdapter = new PopularAdapter();
        binding.recyclerViewShowtime.setAdapter(showtimeAdapter);
        binding.recyclerViewShowtime.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // RecyclerView Popular
        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewPopular.setNestedScrollingEnabled(false);
        popularAdapter = new PopularAdapter();
        popularAdapter.setOnMovieClickListener(movieId -> {
            Intent intent=new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id",movieId);
            startActivity(intent);
        });
        binding.recyclerViewPopular.setAdapter(popularAdapter);
        binding.recyclerViewPopular.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // RecyclerView Retro
        binding.recyclerViewRetrotv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewRetrotv.setNestedScrollingEnabled(false);
        retroAdapter = new PopularAdapter();
        retroAdapter.setOnMovieClickListener(movieId -> {
            Intent intent=new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id",movieId);
            startActivity(intent);
        });
        binding.recyclerViewRetrotv.setAdapter(retroAdapter);
        binding.recyclerViewRetrotv.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // RecyclerView Only App
        binding.recyclerViewOnlyApp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewOnlyApp.setNestedScrollingEnabled(false);
        onlyAdapter = new PopularAdapter();
        onlyAdapter.setOnMovieClickListener(movieId -> {
            Intent intent=new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id",movieId);
            startActivity(intent);
        });
        binding.recyclerViewOnlyApp.setAdapter(onlyAdapter);
        binding.recyclerViewOnlyApp.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        binding.headerButton2.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchActivity.class);
            startActivity(intent);
        });


        // Button Cancelled category
        binding.btnCancelled.setOnClickListener(v -> {
            binding.btnCategories.setText("Categories");
            binding.btnYear.setText("Year");
            binding.btnCancelled.setVisibility(View.GONE);
        });

        // Button Categories
        binding.btnCategories.setOnClickListener(v -> {
            CategoryDialogFragment dialog = new CategoryDialogFragment(selectedGenre -> {
                binding.btnCategories.setText(selectedGenre);
                if(binding.btnCancelled.getVisibility() == View.GONE)
                {
                    binding.btnCancelled.setVisibility(View.VISIBLE);
                }
            });
            dialog.show(getParentFragmentManager(), "CategoryDialog");
        });

        binding.btnYear.setOnClickListener(v -> {
            YearDialogFragment dialog = new YearDialogFragment(selectedGenre -> {
                binding.btnYear.setText(selectedGenre);
                if(binding.btnCancelled.getVisibility() == View.GONE)
                {
                    binding.btnCancelled.setVisibility(View.VISIBLE);
                }
            });
            dialog.show(getParentFragmentManager(), "YearDialog");
        });
    }

    protected void initData() {
        MovieForHomeViewModel viewModel;

        // Khởi tạo repository của bạn, nếu dùng singleton thì gọi qua getter
        MovieRepository repository = new MovieRepository();

        // Tạo factory
        MovieForHomeViewModelFactory factory = new MovieForHomeViewModelFactory(repository);

        // Lấy ViewModel với factory
        viewModel = new ViewModelProvider(this, factory).get(MovieForHomeViewModel.class);

        // Sử dụng viewModel...
        viewModel.popular.observe(requireActivity(), movies -> {
            popularAdapter.submitList(movies.getData());
            carouselAdapter.submitList(movies.getData());
        });
        viewModel.retro.observe(requireActivity(), movies -> retroAdapter.submitList(movies.getData()));
        viewModel.only.observe(requireActivity(), movies -> onlyAdapter.submitList(movies.getData()));
        // Optionally trigger initial fetch:
        viewModel.loadDataPopular();
        viewModel.loadDataRetro();
        viewModel.loadDataOnly();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(autoScrollRunnable);
        binding = null;
    }
}