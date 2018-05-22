package com.app.uni.uniapp.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sathveegan on 30-Apr-18.
 */

public class JoinList implements Serializable{
    private String username;
    private String email;
    private String title;
    private Date date;

    public JoinList(){

    }

    public JoinList(String username, String email, String title, Date date) {
        this.username = username;
        this.email = email;
        this.title = title;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
