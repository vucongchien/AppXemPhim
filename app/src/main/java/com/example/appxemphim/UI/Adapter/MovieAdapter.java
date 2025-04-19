//package com.example.appxemphim.UI.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.recyclerview.widget.ListAdapter;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.appxemphim.Model.MovieOverviewModel;
//import com.example.appxemphim.R;
//import com.example.appxemphim.UI.Interface.OnMovieClickListener;
//
//public class MovieAdapter extends ListAdapter<MovieOverviewModel, MovieAdapter.MovieViewHolder> {
//    private final Context context;
//    private final OnMovieClickListener listener;
//
//    public MovieAdapter(Context context, OnMovieClickListener clickListener) {
//        super(DIFF_CALLBACK);
//        this.context = context;
//        this.listener = clickListener;
//    }
//
//    // DiffUtil để so sánh các item
//    private static final DiffUtil.ItemCallback<MovieOverviewModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<MovieOverviewModel>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull MovieOverviewModel oldItem, @NonNull MovieOverviewModel newItem) {
//            return oldItem.getMovieId() == newItem.getMovieId(); // So sánh dựa trên movieId
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull MovieOverviewModel oldItem, @NonNull MovieOverviewModel newItem) {
//            return oldItem.equals(newItem); // So sánh nội dung (cần override equals trong MovieOverviewModel nếu cần)
//        }
//    };
//
//    @NonNull
//    @Override
//    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_for_home_fragment, parent, false);
//        return new MovieViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
//        MovieOverviewModel movie = getItem(position);
//
//        // Gán dữ liệu cho view
//        holder.textTitle.setText(movie.getTitle());
//        holder.textRating.setText(movie.getRating());
//        holder.description.setText(movie.getDescription());
//
//        // Tải ảnh bằng Glide
//        Glide.with(context)
//                .load(movie.getPosterUrl())
//                .placeholder(R.drawable.placeholder_poster) // Ảnh hiển thị khi đang tải
//                .error(R.drawable.placeholder_poster)      // Ảnh hiển thị khi lỗi
//                .override(100, 150)                       // Resize ảnh để tối ưu hiệu suất
//                .into(holder.imgPoster);
//
//        // Xử lý click
//        holder.itemView.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.OnMovieClick(movie);
//            }
//        });
//    }
//
//    public static class MovieViewHolder extends RecyclerView.ViewHolder {
//        ImageView imgPoster;
//        TextView textTitle, textRating, description;
//
//        public MovieViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imgPoster = itemView.findViewById(R.id.imgMovie);
//            textTitle = itemView.findViewById(R.id.textNameFilm);
//            textRating = itemView.findViewById(R.id.textRating);
//            description = itemView.findViewById(R.id.textDetails);
//        }
//    }
//}