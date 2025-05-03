package com.example.appxemphim.UI.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
import com.example.appxemphim.UI.Utils.SpaceItemDecoration;
import com.example.appxemphim.ViewModel.MovieForHomeViewModel;
import com.example.appxemphim.ViewModel.MovieForHomeViewModelFactory;
import com.example.appxemphim.databinding.FragmentHomeBinding;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private PopularAdapter popularAdapter, retroAdapter, onlyAdapter;
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
        return view;
    }

    private void initViews(){
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        //Carousel
        carouselAdapter=new CarouselAdapter();
        binding.carousel.setAdapter(carouselAdapter);
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = (binding.carousel.getCurrentItem() + 1) % carouselAdapter.getItemCount();
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

        // RecyclerView Popular
        binding.recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewPopular.setNestedScrollingEnabled(false);
        popularAdapter = new PopularAdapter();
        binding.recyclerViewPopular.setAdapter(popularAdapter);
        binding.recyclerViewPopular.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // RecyclerView Retro
        binding.recyclerViewRetrotv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewRetrotv.setNestedScrollingEnabled(false);
        retroAdapter = new PopularAdapter();
        binding.recyclerViewRetrotv.setAdapter(retroAdapter);
        binding.recyclerViewRetrotv.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        // RecyclerView Only App
        binding.recyclerViewOnlyApp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewOnlyApp.setNestedScrollingEnabled(false);
        onlyAdapter = new PopularAdapter();
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