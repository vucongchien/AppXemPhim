package com.example.appxemphim.Model;

import java.util.List;
import java.util.Objects;

public class Comment {
    private String commentId;
    private String user_id;
    private String name;
    private String avatar;
    private String parent_comment_id;
    private String created_at;
    private int like;
    private  String content;
    private boolean isExpanded=false;
    private List<Comment> comments;
    private  Boolean isReply=false;


    public Comment() {
    }

    public Comment(String user_id, String parent_comment_id, String created_at, int like, String content, String commentId) {
        this.user_id = user_id;
        this.parent_comment_id = parent_comment_id;
        this.created_at = created_at;
        this.like = like;
        this.content = content;
        this.commentId = commentId;
    }

    public Comment(String commentId, String user_id, String name, String avatar, String parent_comment_id, String created_at, int like, String content) {
        this.commentId = commentId;
        this.user_id = user_id;
        this.name = name;
        this.avatar = avatar;
        this.parent_comment_id = parent_comment_id;
        this.created_at = created_at;
        this.like = like;
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Comment comment = (Comment) obj;
        return commentId.equals(comment.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId);
    }


    public String getUser_id() {
        return user_id;
    }

    public void setComment_Id(String uid) {
        this.user_id = uid;
    }

    public String getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(String parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int checklike) {
        this.like = checklike;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getReply() {
        return isReply;
    }

    public void setReply(Boolean reply) {
        isReply = reply;
    }
}
