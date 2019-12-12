package com.example.busticketingapp.BusList;

public class Bus implements Comparable{
    boolean standardArrive;
    String departureTerminal;
    String destinationTerminal;
    String departureDate;
    String departureTime;
    String busCompany;
    //String busGrade;
    int remainSeat;

    public String getDepartureTerminal() {
        return departureTerminal;
    }

    public void setDepartureTerminal(String departureTerminal) {
        this.departureTerminal = departureTerminal;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
    public String getDestinationTerminal() {
        return destinationTerminal;
    }

    public void setDestinationTerminal(String destinationTerminal) {
        this.destinationTerminal = destinationTerminal;
    }
    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getBusCompany() {
        return busCompany;
    }

    public void setBusCompany(String busCompany) {
        this.busCompany = busCompany;
    }
    public int getRemainSeat() {
        return remainSeat;
    }

    public void setRemainSeat(int remainSeat) {
        this.remainSeat = remainSeat;
    }

    @Override
    public int compareTo(Object o) {
        String[] time2String = departureTime.split("-");
        String[] time2StringForCompare = ((Bus)o).getDepartureTime().split("-");

        String objectString;
        String compareString;
        int hour;
        int min;
        int objectHour;
        int objectMin;

        if(!standardArrive){
            objectString = time2StringForCompare[0];
            compareString = time2String[0];
        }else{
            objectString = time2StringForCompare[1];
            compareString = time2String[1];
        }
        hour=Integer.parseInt(compareString.split(":")[0]);
        min = Integer.parseInt(compareString.split(":")[1]);
        objectHour=Integer.parseInt(objectString.split(":")[0]);
        objectMin =Integer.parseInt(objectString.split(":")[1]);
        if(hour > objectHour){
            //this가 object보다 뒷차이다.
            return 1;
        }else if(hour == objectHour){
            if(min > objectMin){
                //this가 object보다 뒷차이다.
                return 1;
            }else if(min < objectMin){
                //this가 object보다 앞차이다.
                return -1;
            }else return 0;
        }else{
            //this가 object보다 일찍이다
            return -1;
        }
    }
}
