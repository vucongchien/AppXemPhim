package com.example.appxemphim.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.appxemphim.Model.MovieDetailModel;
import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.Repository.FavouriteRepository;
import com.example.appxemphim.Repository.MovieRepository;
import com.example.appxemphim.Repository.ReviewRepositytory;
import com.example.appxemphim.Request.ReviewRequest;
import com.example.appxemphim.UI.Utils.Resource;

import java.util.List;

public class ReviewViewModel {
    private  final ReviewRepositytory repository;
    public ReviewViewModel(Context context){
        repository = new ReviewRepositytory(context);
    }

    private  final MutableLiveData<Resource<List<ReviewModel>>> _getReaview = new MutableLiveData<>();

    public LiveData<Resource<List<ReviewModel>>> getDataReview = _getReaview;

    private final MutableLiveData<Resource<String>> _addReview = new MutableLiveData<>();

    public LiveData<Resource<String>> addDataReview= _addReview;

    public  void addReview(ReviewRequest reviewRequest){
        repository.addReview(reviewRequest,_addReview);
    }

    public void getReview(String movie_id){
        repository.getReview(movie_id,_getReaview);
    }
}
