package com.xing.imhere.base;

/**
 * @author xinG
 * @date 2018/8/9 0009 15:25
 */
public class RegisterResult {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int ENSURECODE_ERROR = 2;
    public static final int ENSURECODE_OUT_TIME = 3;
    public static final int ACCOUNT_NOT_EXIST = 4;

    private int code;
    private User user;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RegisterResult{" +
                "code=" + code +
                ", user=" + user +
                '}';
    }
}
