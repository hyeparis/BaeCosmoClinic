package com.bean;

public class TimeSlots {

public String time, date, username;

    public TimeSlots(String time, String date, String username) {
        this.time = time;
        this.date = date;
        this.username = username;
    }

    public TimeSlots() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
