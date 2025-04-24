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
        Toast.makeText(context, String.valueOf(i), Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater.inflate(R.layout.video_item, null);
        }
        ImageView imageView =  view.findViewById(R.id.thumbnail);
        TextView title = view.findViewById(R.id.episode_title);
        TextView duration = view.findViewById(R.id.episode_duration);
        VideoModel videoModel = videoModels.get(i);
        Glide.with(context)
                .load(Uri.parse(videoModel.getVideo_url()))
                .thumbnail(0.1f)
                .into(imageView);
        title.setText(videoModel.getVideo_id());
        long totalSeconds = videoModel.getDuration() / 1000;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        long totalMinutes = totalSeconds / 60;
        String timeFormatted;
        if (hours > 1) {
            timeFormatted = String.format("%dh %02dm", hours, minutes);
        } else {
            timeFormatted = String.format("%dm", totalMinutes);
        }
        duration.setText(timeFormatted);
        return view;
    }
}
