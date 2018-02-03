package com.ittianyu.bcdnwatcher.common.bean;

import java.io.Serializable;

/**
 * Created by 86839 on 2018/2/3.
 */

public class BindEthBean implements Serializable {
    public static final int CODE_NO_ENOUGH_COINS = 2000;
    public static final int CODE_WRONG_ETH = 2002;

    /**
     * code : 2000
     * message : sorry，you don`t have enough coins
     * data : {"coins":"0"}
     */

    private int code;// 2000: 没有足够的币   2002: 地址错误
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BindEthBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * coins : 0
         */

        private String coins;

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "coins='" + coins + '\'' +
                    '}';
        }
    }
}
