package com.example.appxemphim.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.appxemphim.Model.Comment;
import com.example.appxemphim.Repository.CommentRepository;
import com.example.appxemphim.Request.CommentRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentViewModel extends ViewModel {
    private CommentRepository repo;
    private LiveData<List<Comment>> allComments;

    public void init(Context context,String movieId) {
        if (repo != null) return;
        repo = new CommentRepository(context,movieId);
        repo.start();
        allComments = repo.getCommentsLiveData();
    }

    public LiveData<List<Comment>> getAllComments() {
        return allComments;
    }

    public List<Comment> getParentComments(List<Comment> list) {
        return list.stream()
                .filter(c -> c.getParent_comment_id() == null || c.getParent_comment_id().isEmpty())
                .collect(Collectors.toList());
    }

    public List<Comment> getDescendants(String parentId, List<Comment> list) {
        List<Comment> result = list.stream()
                .filter(c -> parentId.equals(c.getParent_comment_id()))
                .collect(Collectors.toList());
        List<Comment> descendants = new ArrayList<>(result);
        for (Comment child : result) {
            descendants.addAll(getDescendants(child.getCommentId(), list));
        }
        return descendants;
    }

    public void like(String movieId, String commentId, boolean isLike){
        repo.setLike(movieId,commentId,isLike);
    }

    public void sendComment(CommentRequest commentRequest, Runnable onSuccess, Runnable onFailure){
        repo.sendComment(commentRequest,onSuccess,onFailure);
    }
}
