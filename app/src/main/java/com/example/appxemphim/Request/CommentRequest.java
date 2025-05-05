package com.example.appxemphim.Request;

public class CommentRequest {
    private String movie_id;
    private String parent_comment_id;
    private  String content;

    public CommentRequest(String movie_id, String Parent_comment_id, String content) {
        this.movie_id = movie_id;
        this.parent_comment_id = Parent_comment_id;
        this.content = content;
    }

    public CommentRequest() {
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }


    public String getParent_comment_id() {
        return parent_comment_id;
    }

    public void setParent_comment_id(String parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
