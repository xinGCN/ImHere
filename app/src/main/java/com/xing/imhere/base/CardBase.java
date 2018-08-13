package com.xing.imhere.base;

/**
 * Created by xinG on 2018/8/6 0006.
 *
 */
public class CardBase {

    private String message;
    private String time;
    private boolean isExpand;

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public CardBase(String message, String time) {
        this.message = message;
        this.time = time;
    }

    public CardBase() {
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public String toString() {
        return "CardBase{" +
                "message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
