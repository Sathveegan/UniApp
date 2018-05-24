package com.app.uni.uniapp.data;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sathveegan on 02-May-18.
 */

public class MyLocation implements Serializable {

    private String username;
    private Date date;
    private int timeHours;
    private double latitude;
    private double longitude;
    private  int timeMinute;
    private String contact;

    public MyLocation() {
    }

    public MyLocation(String username, Date date, int timeHours, int timeMinute, double latitude, double longitude, String contact) {
        this.date = date;
        this.timeHours = timeHours;
        this.timeMinute = timeMinute;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contact = contact;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getDate() {
        return date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getTimeHours() {
        return timeHours;
    }

    public void setTimeHours(int timeHours) {
        this.timeHours = timeHours;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }
}
