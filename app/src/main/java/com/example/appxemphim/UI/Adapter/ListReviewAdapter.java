package com.example.appxemphim.UI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.DTO.ReviewDTO;
import com.example.appxemphim.Model.VideoModel;
import com.example.appxemphim.databinding.RatingItemBinding;
import com.example.appxemphim.databinding.VideoItemBinding;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListReviewAdapter extends BaseAdapter {
    private List<ReviewDTO> reviewDTOS;
    private Context context;

    public ListReviewAdapter(Context context, List<ReviewDTO> reviewDTOS) {
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
        ReviewDTO reviewDTO = reviewDTOS.get(i);
        Glide.with(context)
                .load(reviewDTO.getAvata())
                .into(binding.avatar);
        binding.userName.setText(reviewDTO.getName());
        binding.userRating.setRating(reviewDTO.getRating());
        Timestamp timestamp = reviewDTO.getCreated_at();
        Date date = new Date(timestamp.getSeconds() * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        binding.time.setText(sdf.format(date)) ;
        binding.userReview.setText(reviewDTO.getDescription());
        return view;
    }
}
