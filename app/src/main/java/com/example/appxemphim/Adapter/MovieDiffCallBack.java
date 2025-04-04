package com.example.appxemphim.Adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.example.appxemphim.Model.MovieUIModel;

import java.util.List;

public class MovieDiffCallBack extends DiffUtil.Callback {
    private final List<MovieUIModel> oldList;
    private final List<MovieUIModel> newList;

    public MovieDiffCallBack(List<MovieUIModel> oldList, List<MovieUIModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getMovieId()==newList.get(newItemPosition).getMovieId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}
