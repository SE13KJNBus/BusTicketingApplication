package com.example.busticketingapp.Cart;

public class CartData {
    String startPlace;
    String arrivePlace;
    String startTime;
    String arriveTime;
    String movingTime;
    String date;
    String busCompany;
    int seatNum;
    boolean checkBoxVal;

    public CartData(String startPlace, String arrivePlace, String date,String startTime, String arriveTime, String busCompany, int seatNum, boolean checkBoxVal) {
        this.startPlace = startPlace;
        this.arrivePlace = arrivePlace;
        this.date = date;
        this.startTime = startTime;
        this.arriveTime = arriveTime;
        this.busCompany = busCompany;
        this.seatNum = seatNum;
        this.checkBoxVal = checkBoxVal;
        int hour = (Integer.valueOf(this.arriveTime.split(":")[0]) - Integer.valueOf(this.startTime.split(":")[0]));
        int min = (Integer.valueOf(this.arriveTime.split(":")[1]) - Integer.valueOf(this.startTime.split(":")[1]));
        if (Integer.toString(hour).length() == 1) {
            this.movingTime = "0".concat(Integer.toString(hour)).concat(":");
        } else {
            this.movingTime = Integer.toString(hour).concat(":");
        }
        if (Integer.toString(min).length() == 1) {
            this.movingTime = this.movingTime.concat("0".concat(Integer.toString(min)));
        } else {
            this.movingTime = this.movingTime.concat(Integer.toString(min));
        }
    }
}
