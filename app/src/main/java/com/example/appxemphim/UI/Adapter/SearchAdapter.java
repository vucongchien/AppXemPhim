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
import com.example.appxemphim.UI.Interface.OnMovieClickListener;
import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.R;

public class SearchAdapter extends ListAdapter<MovieUIModel, SearchAdapter.SearchViewHolder> {
    private OnMovieClickListener listener;

    public SearchAdapter() {
        super(DIFF_CALLBACK);
    }

    //DiffcallBack
    private static final DiffUtil.ItemCallback<MovieUIModel> DIFF_CALLBACK =new DiffUtil.ItemCallback<MovieUIModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieUIModel oldItem, @NonNull MovieUIModel newItem) {
            return oldItem.getMovieId() == newItem.getMovieId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieUIModel oldItem, @NonNull MovieUIModel newItem) {
            return oldItem.equals(newItem);
        }
    };


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        MovieUIModel item = getItem(position);
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());

        Glide.with(holder.itemView.getContext())
                .load(item.getPosterUrl())
                .placeholder(R.drawable.main_banner)
                .error(R.drawable.main_banner)
                .override(100, 150)
                .into(holder.thumbnailImageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.OnMovieClick(item);
            }
        });
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.listener = listener;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.result_thumbnail);
            titleTextView = itemView.findViewById(R.id.result_title);
            descriptionTextView = itemView.findViewById(R.id.result_description);
        }
    }
}
