package com.ittianyu.bcdnwatcher.common.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by 86839 on 2018/1/28.
 */
@Entity(tableName = "account")
public class AccountBean implements Serializable {
    @PrimaryKey
    @NonNull
    private String phone;
    private String areaCode = "86";
    private String password; // md5 加密后
    private String token;

    public AccountBean() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AccountBean{" +
                "phone='" + phone + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
