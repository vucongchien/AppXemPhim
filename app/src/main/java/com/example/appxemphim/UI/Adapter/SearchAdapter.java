package com.example.appxemphim.UI.Adapter;

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
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Interface.OnMovieClickListener;

public class SearchAdapter extends ListAdapter<MovieOverviewModel, SearchAdapter.SearchViewHolder> {
    private final Context context;
    private final OnMovieClickListener clickListener;
    public SearchAdapter(Context context, OnMovieClickListener clickListener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.clickListener = clickListener;
    }

    private static final DiffUtil.ItemCallback<MovieOverviewModel> DIFF_CALLBACK =new DiffUtil.ItemCallback<MovieOverviewModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieOverviewModel oldItem, @NonNull MovieOverviewModel newItem) {
            return oldItem.getMovieId() == newItem.getMovieId();
        }
        @Override
        public boolean areContentsTheSame(@NonNull MovieOverviewModel oldItem, @NonNull MovieOverviewModel newItem) {
            return oldItem.equals(newItem);
        }
    };


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_girdview_search_activity, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        MovieOverviewModel item = getItem(position);

        Glide.with(context)
                .load(item.getPosterUrl())
                .placeholder(R.drawable.placeholder_poster)
                .error(R.drawable.placeholder_poster)
                .override(100, 150)
                .into(holder.thumbnailImageView);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.OnMovieClick(item);
            }
        });
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.ivPoster);
        }
    }
}
