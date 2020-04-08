package com.bean;

public class Appointment {

Days days;


    public Appointment() {
    }

    public Appointment(Days days) {
        this.days = days;
    }

    public Days getDays() {
        return days;
    }

    public void setDays(Days days) {
        this.days = days;
    }
}
