package com.example.appxemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Interface.OnMovieClickListener;
import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.R;

import java.util.ArrayList;
import java.util.List;

public class CarouselAdapter extends ListAdapter<MovieUIModel,CarouselAdapter.CarouseViewHolder> {

    private final Context context;
    private final OnMovieClickListener onClickListener;

    // Constructor với DiffUtil.ItemCallback
    public CarouselAdapter(Context context, OnMovieClickListener onClickListener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    // Định nghĩa DiffUtil.ItemCallback
    private static final DiffUtil.ItemCallback<MovieUIModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieUIModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieUIModel oldItem, @NonNull MovieUIModel newItem) {
            return oldItem.getMovieId() == newItem.getMovieId(); // Giả sử MovieUIModel có getMovieId()
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieUIModel oldItem, @NonNull MovieUIModel newItem) {
            return oldItem.equals(newItem); // So sánh toàn bộ nội dung
        }
    };

    @NonNull
    @Override
    public CarouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carousel_movie, parent, false);
        return new CarouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouseViewHolder holder, int position) {
        MovieUIModel movie = getItem(position);
        Glide.with(context)
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.main_banner)
                .error(R.drawable.main_banner)
                .into(holder.mainImageView);

        holder.itemView.setOnClickListener(view -> onClickListener.OnMovieClick(movie));
    }

    public static class CarouseViewHolder extends RecyclerView.ViewHolder {
        ImageView mainImageView;

        public CarouseViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImageView = itemView.findViewById(R.id.mainImageView);
        }
    }

    public MovieUIModel getMovieAt(int position){
        return getItem(position);
    }
}