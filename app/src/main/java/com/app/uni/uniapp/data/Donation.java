package com.app.uni.uniapp.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sathveegan on 23-May-18.
 */

public class Donation implements Serializable {

    private String name;
    private Date date;
    private double amount;

    public Donation(){

    }

    public Donation(String name, Date date, double amount) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
