package com.example.appxemphim.UI.Adapter;

import static android.view.View.GONE;

import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appxemphim.Model.Comment;
import com.example.appxemphim.databinding.CommentItemBinding;

import java.util.List;

public class CommentAdapter extends ListAdapter<Comment, CommentAdapter.CommentViewHolder> {

    public interface OnCommentClickListener {
        void onLikeClick(Comment comment, int position);
        void onReplyClick(Comment comment, int position);
        void onShowMoreClick(Comment comment, int position);
        void onSendClick(Comment comment,int positon, String message);
    }

    private final OnCommentClickListener listener;

    public CommentAdapter(OnCommentClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    public static final DiffUtil.ItemCallback<Comment> DIFF_CALLBACK = new DiffUtil.ItemCallback<Comment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.getCommentId().equals(newItem.getCommentId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding binding = CommentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = getItem(position);
        holder.bind(comment, listener);
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final CommentItemBinding binding;

        public CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Comment comment, OnCommentClickListener listener) {
            Glide.with(binding.getRoot().getContext())
                    .load(comment.getAvatar())
                    .into(binding.avatar);
            binding.userName.setText(comment.getName());
            binding.userReview.setText(comment.getContent());
            binding.time.setText(comment.getCreated_at().toString());
            if (comment.getParent_comment_id() != null && !comment.getParent_comment_id().isEmpty()) {
                binding.tvShowmore.setVisibility(GONE);
            }

            if(comment.getLike()==0){
                binding.reactionCount.setVisibility(GONE);
                binding.iconlike.setVisibility(GONE);
            }else{
                binding.reactionCount.setVisibility(View.VISIBLE);
                binding.iconlike.setVisibility(View.VISIBLE);
                binding.reactionCount.setText(String.valueOf(comment.getLike()));
            }

            if (comment.isExpanded()) {
                binding.tvShowmore.setText("Ẩn bớt");
                binding.listcommetchill.setVisibility(View.VISIBLE);
                binding.listcommetchill.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
                CommentAdapter childAdapter = new CommentAdapter(listener);
                binding.listcommetchill.setAdapter(childAdapter);
                Log.d("NONO","oke: "+ comment.getComments().size());
                childAdapter.submitList(comment.getComments());
            } else {
                binding.tvShowmore.setText("Xem thêm");
                binding.listcommetchill.setVisibility(View.GONE);
            }

            if(comment.getReply()){
                binding.tvReply.setText("Hủy");
                binding.sendMess.setVisibility(View.VISIBLE);
                binding.editMessage.setText(comment.getName());
            }else{
                binding.tvReply.setText("Phản hồi");
                binding.sendMess.setVisibility(GONE);
                binding.editMessage.setText("");
            }

            // Set sự kiện click cho các nút
            binding.tvLike.setOnClickListener(v -> listener.onLikeClick(comment, getAdapterPosition()));
            binding.tvReply.setOnClickListener(v -> listener.onReplyClick(comment, getAdapterPosition()));
            binding.tvShowmore.setOnClickListener(v -> listener.onShowMoreClick(comment, getAdapterPosition()));
            binding.btnSend.setOnClickListener(v -> {
                String message = binding.editMessage.getText().toString().trim();
                binding.editMessage.setText("");
                binding.tvReply.setText("Phản hồi");
                binding.sendMess.setVisibility(GONE);
                listener.onSendClick(comment, getAdapterPosition(), message);
            });
        }

    }



}
