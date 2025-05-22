package com.example.appxemphim.UI.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appxemphim.Model.Comment;
import com.example.appxemphim.Request.CommentRequest;
import com.example.appxemphim.UI.Adapter.CommentAdapter;
import com.example.appxemphim.ViewModel.CommentViewModel;
import com.example.appxemphim.databinding.ActivityTestterBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class testter extends AppCompatActivity {
    private ActivityTestterBinding binding;
    private android.app.ProgressDialog progressDialog;
    private CommentViewModel commentViewModel;
    private CommentAdapter commentAdapter;
    private List<Comment> fullCommentList = new ArrayList<>();
    private final Map<String,Boolean> userLikedComments = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new android.app.ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(true);

        commentViewModel = new CommentViewModel();
        commentViewModel.init(this, "rMlXfo9TGonjR8NuwNGE");
        binding.listComment.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter(new CommentAdapter.OnCommentClickListener() {
            @Override
            public void onLikeClick(Comment comment, int position) {
                boolean currentState = userLikedComments.getOrDefault(comment.getCommentId(), false);
                boolean newState = !currentState;
                userLikedComments.put(comment.getCommentId(), newState);
                Log.d("NONO", "Comment ID: " + comment.getCommentId() + " - New Like State: " + newState);
                if (newState) {
                    comment.setLike(comment.getLike() + 1);
                } else {
                    comment.setLike(comment.getLike() - 1);
                }
                commentViewModel.like("rMlXfo9TGonjR8NuwNGE", comment.getCommentId(), newState);
                commentAdapter.notifyItemChanged(position);
            }
            @Override
            public void onReplyClick(Comment comment,int position) {
                comment.setReply(!comment.getReply());
                commentAdapter.notifyItemChanged(position);
            }
            @Override
            public void onShowMoreClick(Comment comment, int position) {
                List<Comment> replies = commentViewModel.getDescendants(comment.getCommentId(), fullCommentList);
                comment.setExpanded(!comment.isExpanded());
                if(comment.isExpanded()) {
                    comment.setComments(replies);
                } else {
                    comment.setComments(new ArrayList<>());
                }

                commentAdapter.notifyItemChanged(position);
            }

            @Override
            public void onSendClick(Comment comment, int positon,String message) {
                CommentRequest commentRequest = new CommentRequest();
                commentRequest.setMovie_id("rMlXfo9TGonjR8NuwNGE");
                commentRequest.setParent_comment_id(comment.getCommentId());
                commentRequest.setContent(message);
                Log.d("NONO",commentRequest.getParent_comment_id());
                commentViewModel.sendComment(commentRequest,
                        () -> { // onSuccess
                            Toast.makeText(testter.this, "ngon", Toast.LENGTH_SHORT).show();
                        },
                        () -> { // onFailure
                            Toast.makeText(testter.this, "dell giòn", Toast.LENGTH_SHORT).show();
                        });
            }

        });

        binding.listComment.setAdapter(commentAdapter);
        commentViewModel.getAllComments().observe(this, comments -> {
            if (comments != null) {
                fullCommentList = comments;
                for (Comment comment : comments) {
                    if (!userLikedComments.containsKey(comment.getCommentId())) {
                        userLikedComments.put(comment.getCommentId(), false);
                    }
                }
                List<Comment> parentComments = new ArrayList<>(commentViewModel.getParentComments(comments));
                for(Comment comment: parentComments){
                    List<Comment> replies = commentViewModel.getDescendants(comment.getCommentId(), fullCommentList);
                    comment.setComments(replies);
                }
                commentAdapter.submitList(parentComments);
            }
        });
    }
}
