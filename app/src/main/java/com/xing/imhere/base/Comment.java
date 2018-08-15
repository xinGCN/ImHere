package com.xing.imhere.base;

/**
 * Created by xinG on 2018/8/14 0014.
 */
public class Comment {
    private String message;

    public Comment(String message) {
        this.message = message;
    }

    public Comment() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
