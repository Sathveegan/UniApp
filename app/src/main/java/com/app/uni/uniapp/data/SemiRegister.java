package com.app.uni.uniapp.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sathveegan on 23-May-18.
 */

public class SemiRegister implements Serializable {

    private String itnumber;
    private String name;
    private Date date;
    private String contact;
    private String email;
    private int year;
    private int semester;
    private double amount;

    public SemiRegister(){

    }

    public SemiRegister(String itnumber, String name, Date date, String contact, String email, int year, int semester, double amount) {
        this.itnumber = itnumber;
        this.name = name;
        this.date = date;
        this.contact = contact;
        this.email = email;
        this.year = year;
        this.semester = semester;
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

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
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

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
