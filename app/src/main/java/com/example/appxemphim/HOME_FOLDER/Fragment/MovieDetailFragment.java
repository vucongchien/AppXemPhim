package com.example.appxemphim.HOME_FOLDER.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.HOME_FOLDER.MovieUIModel;
import com.example.appxemphim.R;

public class MovieDetailFragment extends Fragment {
    private MovieUIModel movie;

    public static MovieDetailFragment newInstance(MovieUIModel movie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", movie); // Truyền dữ liệu phim
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = getArguments().getParcelable("movie");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
//
//        // Hiển thị thông tin phim
//        TextView movieTitle = view.findViewById(R.id.movieTitle);
//        movieTitle.setText(movie.getTitle());
//
//        // Tải ảnh phim bằng Glide
//        ImageView movieImage = view.findViewById(R.id.movieImage);
//        Glide.with(requireContext()).load(movie.getImageUrl()).into(movieImage);

        return view;
    }
}