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
import com.example.appxemphim.Model.DTO.EpisodeInfoDTO;
import com.example.appxemphim.R;

public class ShowtimeAdapter extends ListAdapter<EpisodeInfoDTO, ShowtimeAdapter.ShowtimeViewHolder> {

    public ShowtimeAdapter() {
        super(new DiffUtil.ItemCallback<EpisodeInfoDTO>() {
            @Override
            public boolean areItemsTheSame(@NonNull EpisodeInfoDTO oldItem, @NonNull EpisodeInfoDTO newItem) {
                return oldItem.getMovieId() == newItem.getMovieId(); // hoặc ID nào bạn có
            }

            @Override
            public boolean areContentsTheSame(@NonNull EpisodeInfoDTO oldItem, @NonNull EpisodeInfoDTO newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagePoster;
        private final TextView textTitle;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.showtime_item_posterURL);
            textTitle = itemView.findViewById(R.id.showtime_item_name);
        }

        void bind(EpisodeInfoDTO episode) {
            textTitle.setText(episode.getEpisodeTitle());

            // Nếu bạn có URL hoặc path poster
            Glide.with(itemView.getContext())
                    .load(episode.getPosterURL()) // giả sử có hàm getPosterUrl()
                    .placeholder(R.drawable.placeholder_poster) // ảnh mặc định nếu đang load
                    .error(R.drawable.placeholder_poster)       // ảnh nếu load fail
                    .into(imagePoster);
        }
    }

}

