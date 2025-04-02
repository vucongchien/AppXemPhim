package com.example.appxemphim.HOME_FOLDER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.HOME_FOLDER.Interface.OnMovieClickListener;
import com.example.appxemphim.R;

import java.util.ArrayList;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouseViewHolder> {

    private final Context context;
    private final List<MovieUIModel> hotMovieList;
    private final OnMovieClickListener onClickListener;

    public CarouselAdapter(Context context, List<MovieUIModel> hotMovieList, OnMovieClickListener onClickListener) {
        this.context = context;
        this.hotMovieList = new ArrayList<>(hotMovieList);
        this.onClickListener = onClickListener;
    }

    public void updateHotMovieList(List<MovieUIModel> newHotMovieList) {
        if (newHotMovieList == null) {
            hotMovieList.clear();
            notifyDataSetChanged();
        }
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MovieDiffCallBack(hotMovieList, newHotMovieList));
        hotMovieList.clear();
        hotMovieList.addAll(newHotMovieList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public CarouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carousel_movie, parent, false);
        return new CarouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouseViewHolder holder, int position) {
        MovieUIModel mainMovie = hotMovieList.get(position);
//        int leftIndex = (position - 1 + hotMovieList.size()) % hotMovieList.size();
//        int rightIndex = (position + 1) % hotMovieList.size();
//        MovieUIModel leftMovie = hotMovieList.get(leftIndex);
//        MovieUIModel rightMovie = hotMovieList.get(rightIndex);

        Glide.with(context)
                .load(mainMovie.getPosterUrl())
                .placeholder(R.drawable.main_banner)
                .error(R.drawable.main_banner)
                .into(holder.mainImageView);
    }

    @Override
    public int getItemCount() {
        return hotMovieList == null ? 0 : hotMovieList.size();
    }

    public static class CarouseViewHolder extends RecyclerView.ViewHolder {
        ImageView mainImageView;

        public CarouseViewHolder(@NonNull View itemView) {
            super(itemView);
            mainImageView = itemView.findViewById(R.id.mainImageView);

        }
    }
}