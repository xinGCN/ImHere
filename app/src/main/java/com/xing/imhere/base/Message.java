package com.xing.imhere.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinG
 * @date 2018/8/2 0002 17:07
 */

public class Message {
    private Integer id;
    private String account;
    private double lat;
    private double lon;
    private String location;
    private String message;
    private String time;

    public Message(){}

//    public Message(int id, String account, String location_1, String location_2, String message) {
//        this.id = id;
//        this.account = account;
//        this.location_1 = location_1;
//        this.location_2 = location_2;
//        this.message = message;
//    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {

        return id;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", location='" + location + '\'' +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public boolean hasNull(){
        if(this.account == null || this.location == null ||
                this.message == null)
            return true;
        return false;
    }

    public CardBase toCardBase(){
        return new CardBase(this.getMessage(),this.getTime());
    }

    public static List<CardBase> toCardBases(List<Message> msgs){
        List<CardBase> cardBases = new ArrayList<>();
        for (Message msg:msgs) {
            cardBases.add(new CardBase(msg.getMessage(),msg.getTime()));
        }
        return cardBases;
    }
}
