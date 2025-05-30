package com.example.appxemphim.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.Model.VideoModel;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    private List<VideoModel> episodeList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(VideoModel episode);
    }

    public EpisodeAdapter(Context context, List<VideoModel> episodeList, OnItemClickListener listener) {
        this.context = context;
        this.episodeList = episodeList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(android.R.id.text1);
        }

        public void bind(VideoModel episode, OnItemClickListener listener) {
            textView.setText(episode.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(episode));
        }
    }

    @NonNull
    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, int position) {
        holder.bind(episodeList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return episodeList == null ? 0 : episodeList.size();
    }
}
