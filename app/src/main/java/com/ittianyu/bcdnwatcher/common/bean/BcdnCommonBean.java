package com.ittianyu.bcdnwatcher.common.bean;

import java.io.Serializable;

/**
 * Created by 86839 on 2018/2/3.
 */

public class BcdnCommonBean implements Serializable {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_BIND_CODE_NOT_EXIST = 2101;
    public static final int CODE_TOKEN_TIMEOUT = 303;

    /**
     * code : 0
     * message : send success
     * data : null
     */

    private int code;
    private String message;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BcdnCommonBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
