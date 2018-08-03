package com.xing.imhere.base;

/**
 * @author xinG
 * @date 2018/8/2 0002 17:07
 */

public class Message {
    private Integer id;
    private String account;
    private String location_1;
    private String location_2;
    private String message;

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

    public void setLocation_1(String location_1) {
        this.location_1 = location_1;
    }

    public void setLocation_2(String location_2) {
        this.location_2 = location_2;
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

    public String getLocation_1() {
        return location_1;
    }

    public String getLocation_2() {
        return location_2;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", location_1='" + location_1 + '\'' +
                ", location_2='" + location_2 + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
