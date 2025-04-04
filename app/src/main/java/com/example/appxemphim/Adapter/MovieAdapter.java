package com.example.appxemphim.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.MovieUIModel;
import com.example.appxemphim.R;
import com.example.appxemphim.Interface.OnMovieClickListener;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private final List<MovieUIModel> movieList;
    private final OnMovieClickListener listener;

    public MovieAdapter(Context context,List<MovieUIModel> initialList,OnMovieClickListener clickListener) {
        this.movieList = new ArrayList<>(initialList);
        this.context = context;
        this.listener=clickListener;
    }

    public void updateMovieList(List<MovieUIModel> newMovieList){
        if (newMovieList == null) {
            movieList.clear();
            notifyDataSetChanged();
            return;
        }
        DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(new MovieDiffCallBack(movieList,newMovieList));
        movieList.clear();
        movieList.addAll(newMovieList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_movie_for_home_fragment,parent,false);
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
        return movieList!=null?movieList.size():0;
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
