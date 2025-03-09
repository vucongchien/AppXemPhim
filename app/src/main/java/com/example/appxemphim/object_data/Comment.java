package com.example.appxemphim.object_data;

import java.sql.Time;

public class Comment {
    private int Comment_id;
    private int Parent_comment_id;
    private int User_id;
    private int Movie_id;
    private String Content;
    private Time Created_at;

    public Comment(int comment_id, int parent_comment_id, int user_id, int movie_id, String content, Time created_at) {
        Comment_id = comment_id;
        Parent_comment_id = parent_comment_id;
        User_id = user_id;
        Movie_id = movie_id;
        Content = content;
        Created_at = created_at;
    }

    public int getComment_id() {
        return Comment_id;
    }

    public int getParent_comment_id() {
        return Parent_comment_id;
    }

    public int getUser_id() {
        return User_id;
    }

    public int getMovie_id() {
        return Movie_id;
    }

    public String getContent() {
        return Content;
    }

    public Time getCreated_at() {
        return Created_at;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setCreated_at(Time created_at) {
        Created_at = created_at;
    }
}
