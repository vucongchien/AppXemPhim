package com.example.appxemphim.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private List<MovieUIModel> movieList;

    public MovieAdapter(List<MovieUIModel> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        MovieUIModel movie=movieList.get(position);


        holder.textTitle.setText(movie.getTitle());
        holder.textRating.setText(movie.getRating());
        holder.description.setText(movie.getDescription());

        Glide.with(context).load(movie.getPosterUrl()).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPoster;
        TextView textTitle,textRating,description;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster=itemView.findViewById(R.id.imgMovie);
            textTitle=itemView.findViewById(R.id.textNameFilm);
            textRating=itemView.findViewById(R.id.textRating);
            description=itemView.findViewById(R.id.textDetails);
        }
    }
}
