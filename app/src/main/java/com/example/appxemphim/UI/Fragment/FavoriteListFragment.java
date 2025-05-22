package com.example.appxemphim.UI.Fragment;

import android.content.Intent;
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

import com.example.appxemphim.Repository.FavouriteRepository;
import com.example.appxemphim.UI.Activity.MovieDetailsActivity;
import com.example.appxemphim.UI.Adapter.FavoriteListAdapter;
import com.example.appxemphim.UI.Utils.UIStateHandler;
import com.example.appxemphim.ViewModel.FavoriteViewModelFactory;
import com.example.appxemphim.ViewModel.FavouriteViewModel;
import com.example.appxemphim.databinding.FragmentFavoriteListBinding;

import java.util.ArrayList;

public class FavoriteListFragment extends Fragment {

    private FragmentFavoriteListBinding binding;
    private FavoriteListAdapter adapter;
    private FavouriteViewModel viewModel;
    private UIStateHandler uiStateHandler;


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
        uiStateHandler=new UIStateHandler(binding.progressBar6,binding.imageErr,binding.imageNotFound,binding.listFavoriteRecycler);
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
        viewModel.favoriteList.observe(requireActivity(), data -> {
            switch (data.getStatus()) {
                case LOADING:
                    uiStateHandler.showLoading();
                    break;

                case SUCCESS:
                    if (data.getData() != null && !data.getData().isEmpty()) {
                        adapter.submitList(data.getData());
                        uiStateHandler.showData();
                    } else {
                        uiStateHandler.showNotFound();
                    }
                    break;

                case ERROR:
                default:
                    uiStateHandler.showError();
                    break;
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
