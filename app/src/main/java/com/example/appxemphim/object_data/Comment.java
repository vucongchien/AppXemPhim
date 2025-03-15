package com.example.appxemphim.object_data;

import java.sql.Time;

public class Comment {
    private String Comment_id;
    private String Parent_comment_id;
    private String User_id;
    private String Movie_id;
    private String Content;
    private Time Created_at;

    public Comment(String user_id, String movie_id, String content, Time created_at) {
        User_id = user_id;
        Movie_id = movie_id;
        Content = content;
        Created_at = created_at;
    }

    public Comment(String parent_comment_id, String comment_id, String user_id, String movie_id, String content, Time created_at) {
        Parent_comment_id = parent_comment_id;
        Comment_id = comment_id;
        User_id = user_id;
        Movie_id = movie_id;
        Content = content;
        Created_at = created_at;
    }

    public String getComment_id() {
        return Comment_id;
    }

    public String getParent_comment_id() {
        return Parent_comment_id;
    }

    public String getUser_id() {
        return User_id;
    }

    public String getMovie_id() {
        return Movie_id;
    }

    public String getContent() {
        return Content;
    }

    public Time getCreated_at() {
        return Created_at;
    }

    public void setComment_id(String comment_id) {
        Comment_id = comment_id;
    }

    public void setParent_comment_id(String parent_comment_id) {
        Parent_comment_id = parent_comment_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setCreated_at(Time created_at) {
        Created_at = created_at;
    }

    public void setMovie_id(String movie_id) {
        Movie_id = movie_id;
    }
}
