package com.example.appxemphim.UI.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.appxemphim.Model.FilterData;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.View.FilterChipGroupView;
import com.example.appxemphim.ViewModel.MovieSearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private FilterChipGroupView genreView, nationView, yearView;
    private SeekBar rating;
    private MovieSearchViewModel viewModel;
    private OnFilterSelectedListener listener;

    public interface OnFilterSelectedListener {
        void onFilterSelected(FilterData filter);
    }

    public void setOnFilterSelectedListener(OnFilterSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_filter, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MovieSearchViewModel.class);

        genreView = v.findViewById(R.id.filterGenreView);
        nationView = v.findViewById(R.id.filterNationView);
        yearView = v.findViewById(R.id.filterYearView);
        rating=v.findViewById(R.id.seekBar);


        // Load danh sách bộ lọc
        List<String> genres = Arrays.asList("Action", "Comedy", "Drama", "Horror");
        List<String> nations = Arrays.asList("USA", "UK", "Japan", "Korea");
        List<String> years = Arrays.asList("2025", "2024", "2023", "2022");

        genreView.setStringItems(genres);
        nationView.setStringItems(nations);
        yearView.setStringItems(years);


        // Khôi phục trạng thái từ ViewModel
        List<String> selectedGenres = viewModel.getGenres().getValue();
        if (selectedGenres != null) {
            genreView.setStringSelected(selectedGenres);
        }
        List<String> selectedNations = viewModel.getNations().getValue();
        if (selectedNations != null) {
            nationView.setStringSelected(selectedNations);
        }
        List<String> selectedYears = viewModel.getYears().getValue();
        if (selectedYears != null) {
            yearView.setStringSelected(selectedYears);
        }
        Double minRating = viewModel.getMinRating().getValue();
        Log.d("adu", "minRating: "+rating.getProgress());
        if (minRating != null) {
            rating.setProgress(minRating.intValue());
        }


        // Khi nhấn Apply
        v.findViewById(R.id.apply).setOnClickListener(x -> {
            List<String> selectedGenresList = genreView.getStringSelected();
            List<String> selectedNationsList = nationView.getStringSelected();
            List<String> selectedYearsList = yearView.getStringSelected();

            // Cập nhật ViewModel
            viewModel.setGenres(selectedGenresList);
            viewModel.setNations(selectedNationsList);
            viewModel.setYears((selectedYearsList));
            viewModel.setMinRating((double) rating.getProgress());
            Log.d("adu", "onCreateView: "+rating.getProgress());


            FilterData data = new FilterData(
                    null,
                    selectedGenresList,
                    selectedYearsList,
                    selectedNationsList,
                    (double) rating.getProgress()
            );
            if (listener != null) listener.onFilterSelected(data);
            dismiss();
        });

        // Khi nhấn Reset (chỉ clear UI, không ảnh hưởng ViewModel)
        v.findViewById(R.id.reset).setOnClickListener(x -> {
            genreView.clearSelected();
            nationView.clearSelected();
            yearView.clearSelected();
            rating.setProgress(0);
        });

        return v;
    }

    private List<Integer> convertYears(List<String> yearStrings) {
        List<Integer> years = new ArrayList<>();
        for (String s : yearStrings) {
            try {
                years.add(Integer.parseInt(s));
            } catch (NumberFormatException ignored) {}
        }
        return years;
    }

}
