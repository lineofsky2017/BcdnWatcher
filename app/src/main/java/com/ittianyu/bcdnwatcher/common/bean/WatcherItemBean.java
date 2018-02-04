package com.ittianyu.bcdnwatcher.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86839 on 2018/1/27.
 */

public class WatcherItemBean implements Serializable {
    private List<MinerBean> miners;
    private double totalIncome;
    private double yesterdayIncome;
    private String areaCode;
    private String phone;
    private List<IncomeBean.DataBean.HistoryBean> incomeHistory;
    private boolean login;
    private String token;

    public List<MinerBean> getMiners() {
        return miners;
    }

    public void setMiners(List<MinerBean> miners) {
        this.miners = miners;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getYesterdayIncome() {
        return yesterdayIncome;
    }

    public void setYesterdayIncome(double yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<IncomeBean.DataBean.HistoryBean> getIncomeHistory() {
        return incomeHistory;
    }

    public void setIncomeHistory(List<IncomeBean.DataBean.HistoryBean> incomeHistory) {
        this.incomeHistory = incomeHistory;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "WatcherItemBean{" +
                "miners=" + miners +
                ", totalIncome=" + totalIncome +
                ", yesterdayIncome=" + yesterdayIncome +
                ", areaCode='" + areaCode + '\'' +
                ", phone='" + phone + '\'' +
                ", incomeHistory=" + incomeHistory +
                ", login=" + login +
                ", token='" + token + '\'' +
                '}';
    }

    public static class MinerBean implements Serializable{
        private String code;
        private boolean isOnline;

        public MinerBean() {
        }

        public MinerBean(String code, boolean isOnline) {
            this.code = code;
            this.isOnline = isOnline;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isOnline() {
            return isOnline;
        }

        public void setOnline(boolean online) {
            isOnline = online;
        }

        @Override
        public String toString() {
            return "MinerBean{" +
                    "code='" + code + '\'' +
                    ", isOnline=" + isOnline +
                    '}';
        }
    }
}
