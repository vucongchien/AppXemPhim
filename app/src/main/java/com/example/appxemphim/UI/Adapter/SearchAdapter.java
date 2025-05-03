package com.example.appxemphim.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Interface.OnMovieClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ListAdapter<MovieOverviewModel, SearchAdapter.SearchViewHolder> {
    private OnMovieClickListener clickListener;

    public SearchAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.clickListener = listener;
    }

    private static final DiffUtil.ItemCallback<MovieOverviewModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieOverviewModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieOverviewModel oldItem, @NonNull MovieOverviewModel newItem) {
            return oldItem.getMovieId().equals(newItem.getMovieId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieOverviewModel oldItem, @NonNull MovieOverviewModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_girdview_search_activity, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        MovieOverviewModel item = getItem(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getPosterUrl())
                .placeholder(R.drawable.placeholder_poster)
                .error(R.drawable.placeholder_poster)
                .into(holder.thumbnailImageView);

        if (clickListener != null) {
            holder.itemView.setOnClickListener(v -> clickListener.OnMovieClick(item.getMovieId()));
        }
    }

    public void addItems(List<MovieOverviewModel> newItems) {
        if (newItems != null && !newItems.isEmpty()) {
            List<MovieOverviewModel> currentList = new ArrayList<>(getCurrentList());
            currentList.addAll(newItems);
            submitList(currentList);
        }
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        final ImageView thumbnailImageView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.ivPoster);
        }
    }
}