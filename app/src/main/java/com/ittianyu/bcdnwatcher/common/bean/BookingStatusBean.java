package com.ittianyu.bcdnwatcher.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86839 on 2018/2/3.
 */

public class BookingStatusBean implements Serializable {
    public static final int TYPE_BOOKED = 3;
    public static final int TYPE_NO_ETH = 1;
    public static final int TYPE_SUCCESS = 5;
    public static final int TYPE_FAILED = 4;

    /**
     * code : 0
     * message : make persistent efforts
     * data : {"type":4,"reservationCode":"W6P32ZpvPcCIIq39o","timestamp":1517889600,"timesofperson":"30301","list":[{"time":"2018-02-02 12:00:00","nickname":"qq77520a"},{"time":"2018-02-02 12:00:00","nickname":"时尚休闲"},{"time":"2018-02-02 12:00:00","nickname":"123"}]}
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
        return "BookingStatusBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * type : 4
         * reservationCode : W6P32ZpvPcCIIq39o
         * timestamp : 1517889600
         * timesofperson : 30301
         * list : [{"time":"2018-02-02 12:00:00","nickname":"qq77520a"},{"time":"2018-02-02 12:00:00","nickname":"时尚休闲"},{"time":"2018-02-02 12:00:00","nickname":"123"}]
         */

        private int type;
        private String reservationCode;
        private long timestamp;
        @SerializedName("timesofperson")
        private String timesOfPerson;// 预约人数
        private List<WinnerBean> list;// 抽奖成功人员

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

        public List<WinnerBean> getList() {
            return list;
        }

        public void setList(List<WinnerBean> list) {
            this.list = list;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "type=" + type +
                    ", reservationCode='" + reservationCode + '\'' +
                    ", timestamp=" + timestamp +
                    ", timesOfPerson='" + timesOfPerson + '\'' +
                    ", list=" + list +
                    '}';
        }

        public static class WinnerBean {
            /**
             * time : 2018-02-02 12:00:00
             * nickname : qq77520a
             */

            private String time;
            private String nickname;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            @Override
            public String toString() {
                return "WinnerBean{" +
                        "time='" + time + '\'' +
                        ", nickname='" + nickname + '\'' +
                        '}';
            }
        }
    }
}
