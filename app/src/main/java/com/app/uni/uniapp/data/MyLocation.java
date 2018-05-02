package com.app.uni.uniapp.data;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Sathveegan on 02-May-18.
 */

public class MyLocation {

    private Date date;
    private int timeHours;
    private double latitude;
    private double longitude;
    private  int timeMinute;

    public MyLocation() {
    }

    public MyLocation(Date date, int timeHours, int timeMinute, double latitude, double longitude) {
        this.date = date;
        this.timeHours = timeHours;
        this.timeMinute = timeMinute;
        this.latitude = latitude;
        this.longitude = longitude;
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
