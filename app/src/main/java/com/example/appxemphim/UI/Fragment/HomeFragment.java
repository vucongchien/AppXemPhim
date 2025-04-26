package com.example.appxemphim.UI.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.R;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.UI.Adapter.PopularAdapter;
import com.example.appxemphim.UI.Activity.SearchActivity;
import com.example.appxemphim.UI.Utils.SpaceItemDecoration;
import com.example.appxemphim.ViewModel.MovieForHomeViewModel;
import com.example.appxemphim.ViewModel.MovieForHomeViewModelFactory;
import com.example.appxemphim.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private PopularAdapter popularAdapter, retroAdapter, onlyAdapter;
    private MovieForHomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Khởi tạo binding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        initViews();
        initData();


        return view;
    }

    private void initViews(){
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);


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



        // Button Search
        binding.btnCancelled.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        // Button Categories
        binding.btnCategories.setOnClickListener(v -> {
            String[] genres = {"Hành động", "Tình cảm", "Hài hước", "Khoa học", "Hoạt hình"};
            new AlertDialog.Builder(getActivity())
                    .setTitle("Chọn thể loại phim")
                    .setItems(genres, (dialog, which) -> {
                        String selectedGenre = genres[which];
                        Toast.makeText(getActivity(), "Bạn chọn: " + selectedGenre, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .create()
                    .show();
        });
    }

    protected void initData() {
        // Khởi tạo repository của bạn, nếu dùng singleton thì gọi qua getter
        MovieRepository repository = new MovieRepository();

        // Tạo factory
        MovieForHomeViewModelFactory factory = new MovieForHomeViewModelFactory(repository);

        // Lấy ViewModel với factory
        viewModel = new ViewModelProvider(this, factory).get(MovieForHomeViewModel.class);

        // Sử dụng viewModel...
        viewModel.popular.observe(requireActivity(), movies -> popularAdapter.submitList(movies.getData()));
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
        binding = null;
    }
}