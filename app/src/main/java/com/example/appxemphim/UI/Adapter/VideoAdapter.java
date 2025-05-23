package com.example.appxemphim.UI.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.databinding.VideoItemBinding;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<VideoModel> videoList;
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(VideoModel video);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public VideoAdapter(Context context, List<VideoModel> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoItemBinding binding = VideoItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoModel video = videoList.get(position);

        Glide.with(context)
                .load(Uri.parse(video.getVideo_url()))
                .thumbnail(0.1f)
                .into(holder.binding.thumbnail);

        holder.binding.episodeTitle.setText(video.getName());

        long totalSeconds = video.getDuration() / 1000;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        long totalMinutes = totalSeconds / 60;

        String timeFormatted = (hours > 1)
                ? String.format("%dh %02dm", hours, minutes)
                : String.format("%dm", totalMinutes);

        holder.binding.episodeDuration.setText(timeFormatted);

        holder.binding.getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        final VideoItemBinding binding;

        public VideoViewHolder(VideoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
