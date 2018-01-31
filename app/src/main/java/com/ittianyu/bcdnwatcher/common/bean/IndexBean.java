package com.ittianyu.bcdnwatcher.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86839 on 2018/1/27.
 */

public class IndexBean implements Serializable {

    /**
     * code : 0
     * message : index
     * data : {"Total":"1.0130488357543945E+03","Yestod":"2.0565999603271484E+02","Current":"1.36E-01","Value":"1.37663E+02","advertising":[{"name":"defalt","imgurl":"null"},{"name":"defalt","imgurl":"null"},{"name":"defalt","imgurl":"null"}]}
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
         * Total : 1.0130488357543945E+03   总收益
         * Yestod : 2.0565999603271484E+02
         * Current : 1.36E-01   当前价格
         * Value : 1.37663E+02  总价值(USD)
         * advertising : [{"name":"defalt","imgurl":"null"},{"name":"defalt","imgurl":"null"},{"name":"defalt","imgurl":"null"}]
         */

        @SerializedName("Total")
        private String total;
        @SerializedName("Yestod")
        private String yestod;
        @SerializedName("Current")
        private String current;
        @SerializedName("Value")
        private String value;
        private List<AdvertisingBean> advertising;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getYestod() {
            return yestod;
        }

        public void setYestod(String yestod) {
            this.yestod = yestod;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<AdvertisingBean> getAdvertising() {
            return advertising;
        }

        public void setAdvertising(List<AdvertisingBean> advertising) {
            this.advertising = advertising;
        }

        public static class AdvertisingBean {
            /**
             * name : defalt
             * imgurl : null
             */

            private String name;
            @SerializedName("imgurl")
            private String imgUrl;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
