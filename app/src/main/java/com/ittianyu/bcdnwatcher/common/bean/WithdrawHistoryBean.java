package com.ittianyu.bcdnwatcher.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86839 on 2018/2/3.
 */

public class WithdrawHistoryBean implements Serializable {

    /**
     * code : 0
     * message : welcome
     * data : {"history":[{"date":"1516256910","amount":690.8909912109375,"status":2},{"date":"1516581450","amount":834,"status":2},{"date":"1517187581","amount":1351.3800048828125,"status":2}]}
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

    @Override
    public String toString() {
        return "WithdrawHistoryBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        private List<HistoryBean> history;

        public List<HistoryBean> getHistory() {
            return history;
        }

        public void setHistory(List<HistoryBean> history) {
            this.history = history;
        }

        public static class HistoryBean {
            /**
             * date : 1516256910
             * amount : 690.8909912109375
             * status : 2
             */

            private String date;
            private double amount;
            private int status;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "history=" + history +
                    '}';
        }
    }
}
