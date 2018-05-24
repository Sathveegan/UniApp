package com.app.uni.uniapp.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sathveegan on 23-May-18.
 */

public class RepeatRegister implements Serializable {

    private String itnumber;
    private String name;
    private Date date;
    private String subject;
    private double amount;

    RepeatRegister(){

    }

    public RepeatRegister(String itnumber, String name, Date date, String subject, double amount) {
        this.itnumber = itnumber;
        this.name = name;
        this.date = date;
        this.subject = subject;
        this.amount = amount;
    }

    public String getItnumber() {
        return itnumber;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public double getAmount() {
        return amount;
    }

    public void setItnumber(String itnumber) {
        this.itnumber = itnumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
