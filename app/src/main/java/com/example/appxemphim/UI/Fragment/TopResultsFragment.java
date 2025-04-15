package com.example.appxemphim.UI.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appxemphim.UI.Activity.MovieDetailActivity;
import com.example.appxemphim.UI.Adapter.MovieAdapter;
import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Utils.SpaceItemDecoration;
import com.example.appxemphim.databinding.FragmentTopResultsBinding;

import java.util.ArrayList;
import java.util.List;

public class TopResultsFragment extends Fragment {
    private FragmentTopResultsBinding binding;
    private MovieAdapter topResultsAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding=FragmentTopResultsBinding.inflate(inflater,container,false);

        View view= binding.getRoot();


        topResultsAdapter=new MovieAdapter(requireContext(),new ArrayList<>(), movieUIModel -> {
            MovieDetailActivity.showMovieDetailActivity(requireActivity(),movieUIModel);
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.recyclerView.setAdapter(topResultsAdapter);
        binding.recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelOffset(R.dimen.item_spacing)));

        return view;
    }

    public void updateFilteredMovies(List<MovieUIModel> filteredMovies) {
        topResultsAdapter.updateMovieList(filteredMovies);
        Toast.makeText(requireContext(),"fiteredMovie size: "+filteredMovies.size(),Toast.LENGTH_SHORT).show();
    }

}