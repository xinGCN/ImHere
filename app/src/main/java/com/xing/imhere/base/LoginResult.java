package com.xing.imhere.base;

import java.util.List;

/**
 * @author xinG
 * @date 2018/8/9 0009 12:00
 *
 * code ：请求返回值 1为成功 0为失败
 * user : 请求成功会附带用户信息
 * msgs ：求情成功会附带用户的消息信息
 */
public class LoginResult {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;

    private int code;
    private User user;
    private List<Message> msgs;

    public void setCode(int code) {
        this.code = code;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMsgs(List<Message> msgs) {
        this.msgs = msgs;
    }

    public int getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }

    public List<Message> getMsgs() {
        return msgs;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "code=" + code +
                ", user=" + user +
                ", msgs=" + msgs +
                '}';
    }
}
