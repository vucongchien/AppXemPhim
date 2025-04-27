package com.example.appxemphim.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.ReviewModel;
import com.example.appxemphim.databinding.RatingItemBinding;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListReviewAdapter extends BaseAdapter {
    private List<ReviewModel> reviewDTOS;
    private Context context;

    public ListReviewAdapter(Context context, List<ReviewModel> reviewDTOS) {
        this.context = context;
        this.reviewDTOS = reviewDTOS;
    }
    @Override
    public int getCount() {
        return reviewDTOS.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewDTOS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RatingItemBinding binding;
        if (view == null) {
            binding = RatingItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding); // lưu binding lại
        } else {
            binding = (RatingItemBinding) view.getTag();
        }
        ReviewModel reviewModel = reviewDTOS.get(i);
        Glide.with(context)
                .load(reviewModel.getAvatar())
                .into(binding.avatar);
        binding.userName.setText(reviewModel.getName());
        binding.userRating.setRating(reviewModel.getRating());
        Timestamp timestamp = reviewModel.getCreated_at();
        Date date = new Date(timestamp.getSeconds() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        binding.time.setText(sdf.format(date)) ;
        binding.userReview.setText(reviewModel.getDescription());
        return view;
    }
}
