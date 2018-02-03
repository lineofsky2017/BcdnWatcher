package com.ittianyu.bcdnwatcher.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 86839 on 2018/2/3.
 */

public class BookingAgainBean implements Serializable {

    /**
     * code : 0
     * message : success
     * data : {"type":0,"reservationCode":"WJPcT6fBbBYDvVRE4","timestamp":0,"timesofperson":""}
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
        return "BookingAgainBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * type : 0
         * reservationCode : WJPcT6fBbBYDvVRE4
         * timestamp : 0
         * timesofperson :
         */

        private int type;
        private String reservationCode;
        private long timestamp;
        @SerializedName("timesofperson")
        private String timesOfPerson;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getReservationCode() {
            return reservationCode;
        }

        public void setReservationCode(String reservationCode) {
            this.reservationCode = reservationCode;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getTimesOfPerson() {
            return timesOfPerson;
        }

        public void setTimesOfPerson(String timesOfPerson) {
            this.timesOfPerson = timesOfPerson;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "type=" + type +
                    ", reservationCode='" + reservationCode + '\'' +
                    ", timestamp=" + timestamp +
                    ", timesOfPerson='" + timesOfPerson + '\'' +
                    '}';
        }
    }
}
