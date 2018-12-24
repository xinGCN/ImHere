package com.xing.imhere.base;

/**
 * Created by xinG on 2018/8/22 0022.
 */
public class ChatBase {
    private String date;
    private String content;
    private Type type;

    public enum Type
    {
        FROM,TO
    }

    public ChatBase(String content, Type type) {
        this.content = content;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
