package com.example.appxemphim.UI.Adapter;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.MovieOverviewModel;
import com.example.appxemphim.R;
import com.example.appxemphim.UI.Interface.OnMovieClickListener;

/**
 * Adapter sử dụng ListAdapter với DiffUtil để xử lý danh sách hình ảnh.
 */
public class PopularAdapter extends ListAdapter<MovieOverviewModel, PopularAdapter.PopularViewHolder> {

    private OnMovieClickListener clickListener;

    public PopularAdapter(){
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
    public PopularAdapter.PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_popular_item, parent, false);
        return new PopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.PopularViewHolder holder, int position) {
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

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        final ImageView thumbnailImageView;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.img_popular_item);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                View blurView = itemView.findViewById(R.id.blur_background);
                blurView.setRenderEffect(
                        RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
                );
            }
        }
    }
}
