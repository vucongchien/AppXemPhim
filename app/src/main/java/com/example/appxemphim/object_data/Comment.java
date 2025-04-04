package com.example.appxemphim.object_data;

import java.sql.Time;

public class Comment {
    private String Comment_id;
    private String User_id;
    private String Content;
    private Time Created_at;

    public Comment(String user_id, String content, Time created_at) {
        User_id = user_id;
        Content = content;
        Created_at = created_at;
    }

    public Comment() {

    }

    public String getComment_id() {
        return Comment_id;
    }


    public String getUser_id() {
        return User_id;
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


    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setCreated_at(Time created_at) {
        Created_at = created_at;
    }

}
