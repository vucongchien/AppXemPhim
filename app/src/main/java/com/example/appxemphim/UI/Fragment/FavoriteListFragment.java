package com.example.appxemphim.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.Repository.FavouriteRepository;
import com.example.appxemphim.UI.Activity.MovieDetailsActivity;
import com.example.appxemphim.UI.Adapter.FavoriteListAdapter;
import com.example.appxemphim.ViewModel.FavoriteViewModelFactory;
import com.example.appxemphim.ViewModel.FavouriteViewModel;
import com.example.appxemphim.databinding.FragmentFavoriteListBinding;

import java.util.ArrayList;

public class FavoriteListFragment extends Fragment {

    private FragmentFavoriteListBinding binding;
    private FavoriteListAdapter adapter;
    private FavouriteViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        initViews();
        initData();

        return view;
    }

    private void initViews() {
        adapter=new FavoriteListAdapter();
        adapter.setOnMovieClickListener(movieId -> {
            Intent intent=new Intent(requireContext(), MovieDetailsActivity.class);
            intent.putExtra("movie_id",movieId);
            startActivity(intent);
        });

        binding.listFavoriteRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.listFavoriteRecycler.setAdapter(adapter);


    }

    private void initData() {
        //khởi tạo repository
        FavouriteRepository favouriteRepository=new FavouriteRepository(requireContext());

        // tạo factory
        FavoriteViewModelFactory factory =new FavoriteViewModelFactory(favouriteRepository);

        // lấy viewmodel với factory
        viewModel=new ViewModelProvider(this,factory).get(FavouriteViewModel.class);

        // sử dụng viewmodel
        // sử dụng viewmodel
        viewModel.favoriteList.observe(requireActivity(), movies -> {
            if (movies.getData() != null && !movies.getData().isEmpty()) {
                // Nếu có dữ liệu, ẩn trạng thái "empty"
                binding.tvEmpty.setVisibility(View.GONE);
                adapter.submitList(movies.getData());
            } else {
                // Nếu không có dữ liệu, hiển thị trạng thái "empty"
                binding.tvEmpty.setVisibility(View.VISIBLE);
            }
        });

        viewModel.loadData();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
