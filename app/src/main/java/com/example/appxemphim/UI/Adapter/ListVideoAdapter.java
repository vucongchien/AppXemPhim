package com.example.appxemphim.UI.Adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.R;
import com.example.appxemphim.databinding.VideoItemBinding;

import java.util.List;

public class ListVideoAdapter extends BaseAdapter {
    private List<VideoModel> videoModels;
    private Context context;

    public ListVideoAdapter(Context context, List<VideoModel> videoModels) {
        this.context = context;
        this.videoModels = videoModels;
    }

    @Override
    public int getCount() {
        return videoModels.size();
    }

    @Override
    public Object getItem(int i) {
        return videoModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return  i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VideoItemBinding binding;
        if (view == null) {
            binding = VideoItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding); // lưu binding lại
        } else {
            binding = (VideoItemBinding) view.getTag();
        }

        VideoModel videoModel = videoModels.get(i);

        Glide.with(context)
                .load(Uri.parse(videoModel.getVideo_url()))
                .thumbnail(0.1f)
                .into(binding.thumbnail);

        binding.episodeTitle.setText(videoModel.getVideo_id());

        long totalSeconds = videoModel.getDuration() / 1000;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        long totalMinutes = totalSeconds / 60;

        String timeFormatted = (hours > 1)
                ? String.format("%dh %02dm", hours, minutes)
                : String.format("%dm", totalMinutes);

        binding.episodeDuration.setText(timeFormatted);

        return view;
    }
}
