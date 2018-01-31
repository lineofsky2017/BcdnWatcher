package com.ittianyu.bcdnwatcher.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 86839 on 2018/1/27.
 */

public class UserBean implements Serializable {

    /**
     * code : 0
     * message : success
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTUzOTU2NDYsImlzcyI6InRlc3QiLCJuYmYiOjE1MTUzOTM2NDZ9.Z-ZxChV35mWGv5rmdiTQu1T5LCx7Ulb2xCL_nblesO8","phonenumber":"123456677","userid":"6d5d5ae5-00b0-448a-9f42-9999912sdafafasd","imgurl":"http://47.52.207.212:9090/user/imghead/6d5d5ae5-00b0-448a-9f42-9999912sdafafasd/user.img","areacode":"86","ethAddress":"no EthAddress"}
     */

    private int code;
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

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTUzOTU2NDYsImlzcyI6InRlc3QiLCJuYmYiOjE1MTUzOTM2NDZ9.Z-ZxChV35mWGv5rmdiTQu1T5LCx7Ulb2xCL_nblesO8
         * phonenumber : 123456677
         * userid : 6d5d5ae5-00b0-448a-9f42-9999912sdafafasd
         * imgurl : http://47.52.207.212:9090/user/imghead/6d5d5ae5-00b0-448a-9f42-9999912sdafafasd/user.img
         * areacode : 86
         * ethAddress : no EthAddress
         */

        private String token;
        @SerializedName("phonenumber")
        private String phoneNumber;
        @SerializedName("userid")
        private String userId;
        @SerializedName("imgurl")
        private String imgUrl;
        @SerializedName("areacode")
        private String areaCode;
        private String ethAddress;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getEthAddress() {
            return ethAddress;
        }

        public void setEthAddress(String ethAddress) {
            this.ethAddress = ethAddress;
        }
    }
}
