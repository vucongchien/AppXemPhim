package com.example.appxemphim.UI.Adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.example.appxemphim.Model.MovieOverviewModel;

import java.util.List;

public class MovieDiffCallBack extends DiffUtil.Callback {
    private final List<MovieOverviewModel> oldList;
    private final List<MovieOverviewModel> newList;

    public MovieDiffCallBack(List<MovieOverviewModel> oldList, List<MovieOverviewModel> newList) {
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
