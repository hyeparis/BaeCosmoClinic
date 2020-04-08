package com.bean;

public class Days {

    TimeSlots monday, tuesday, wednesday, thursday, friday;

    public Days(TimeSlots monday, TimeSlots tuesday, TimeSlots wednesday, TimeSlots thursday, TimeSlots friday) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
    }

    public Days() {
    }

    public TimeSlots getMonday() {
        return monday;
    }

    public void setMonday(TimeSlots monday) {
        this.monday = monday;
    }

    public TimeSlots getTuesday() {
        return tuesday;
    }

    public void setTuesday(TimeSlots tuesday) {
        this.tuesday = tuesday;
    }

    public TimeSlots getWednesday() {
        return wednesday;
    }

    public void setWednesday(TimeSlots wednesday) {
        this.wednesday = wednesday;
    }

    public TimeSlots getThursday() {
        return thursday;
    }

    public void setThursday(TimeSlots thursday) {
        this.thursday = thursday;
    }

    public TimeSlots getFriday() {
        return friday;
    }

    public void setFriday(TimeSlots friday) {
        this.friday = friday;
    }
}
