package com.example.appxemphim.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Interface.OnMovieClickListener;
import com.example.appxemphim.Utilities.Utils;

public class FavoriteListAdapter extends ListAdapter<MovieOverviewModel, FavoriteListAdapter.FavoriteListViewHolder> {

    private OnMovieClickListener clickListener;

    public FavoriteListAdapter() {
        super(DIFF_CALLBACK);
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

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public FavoriteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListViewHolder holder, int position) {
        MovieOverviewModel item = getItem(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getPosterUrl())
                .placeholder(R.drawable.placeholder_poster)
                .error(R.drawable.placeholder_poster)
                .into(holder.thumbnailImageView);

        holder.title.setText(item.getTitle());

        holder.year.setText(String.valueOf(Utils.getYearFromTimestamp(item.getCreatedAt())) );
        if (clickListener != null) {
            holder.itemView.setOnClickListener(v -> clickListener.OnMovieClick(item.getMovieId()));
        }
    }

    static class FavoriteListViewHolder extends RecyclerView.ViewHolder {
        final ImageView thumbnailImageView;
        final TextView title;
        final TextView year;

        public FavoriteListViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.imgPoster);
            title=itemView.findViewById(R.id.tvTitle);
            year =itemView.findViewById(R.id.tvYear);

        }
    }
}
