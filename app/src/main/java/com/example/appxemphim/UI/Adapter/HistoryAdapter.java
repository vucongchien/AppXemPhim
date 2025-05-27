package com.example.appxemphim.UI.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.HistoryWithMovie;
import com.example.appxemphim.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryWithMovie> data = new ArrayList<>();

    public void setData(List<HistoryWithMovie> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, watchedAt;
        ImageView poster;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            watchedAt = itemView.findViewById(R.id.txtTime);
            poster = itemView.findViewById(R.id.imgPoster); // thêm dòng này
        }
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryWithMovie item = data.get(position);
        holder.title.setText(item.getMovie().getTitle());

        double percentViewed = item.getHistory().getPerson_view();
        String text = String.format("Đã xem %.0f%%", percentViewed);
        holder.watchedAt.setText(text);

        // Load ảnh poster
        String posterUrl = item.getMovie().getPoster_url(); // bạn cần đảm bảo có phương thức này
        Glide.with(holder.poster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

