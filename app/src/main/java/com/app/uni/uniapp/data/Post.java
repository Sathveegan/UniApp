package com.app.uni.uniapp.data;

/**
 * Created by Sathveegan on 23-May-18.
 */

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {
    private String title;
    private String message;
    private Date date;

    public Post(){

    }

    public Post(String title, String message, Date date) {
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
