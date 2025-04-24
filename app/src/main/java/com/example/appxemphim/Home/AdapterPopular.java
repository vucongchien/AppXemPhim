package com.example.appxemphim.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appxemphim.R;

import java.util.List;

public class AdapterPopular extends RecyclerView.Adapter<AdapterPopular.ViewHolder> {
    private List<Integer> images;
    public AdapterPopular(List<Integer> images) {
        this.images = images;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton imageButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.img_popular_item);
        }
    }

    @NonNull
    @Override
    public AdapterPopular.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_popular_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPopular.ViewHolder holder, int position) {
        holder.imageButton.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}

