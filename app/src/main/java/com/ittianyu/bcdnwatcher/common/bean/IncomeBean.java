package com.ittianyu.bcdnwatcher.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86839 on 2018/1/27.
 */

public class IncomeBean implements Serializable {

    /**
     * code : 0
     * message : welcome
     * data : {"history":[{"machineId":"xxxx","income":100,"date":"1515914205"},{"machineId":"xxxxx","income":97.7635726928711,"date":"1516057222"},{"machineId":"WCmsDO54FxNs8Hquy","income":98.8448486328125,"date":"1516057222"},{"machineId":"WCmsDO54FxNs8Hquy","income":99.97000885009766,"date":"1516143562"},{"machineId":"StlJWLCxLu7Y9QHgR","income":107.79528045654297,"date":"1516143562"},{"machineId":"StlJWLCxLu7Y9QHgR","income":106.67237854003906,"date":"1516229958"},{"machineId":"WCmsDO54FxNs8Hquy","income":99.8453598022461,"date":"1516229958"},{"machineId":"StlJWLCxLu7Y9QHgR","income":98.06100463867188,"date":"1516316357"},{"machineId":"WCmsDO54FxNs8Hquy","income":108.37203216552734,"date":"1516316357"},{"machineId":"WCmsDO54FxNs8Hquy","income":101.18594360351562,"date":"1516402745"},{"machineId":"StlJWLCxLu7Y9QHgR","income":85.87344360351562,"date":"1516402745"},{"machineId":"WCmsDO54FxNs8Hquy","income":71.86689758300781,"date":"1516489164"},{"machineId":"StlJWLCxLu7Y9QHgR","income":95.91204833984375,"date":"1516489165"},{"machineId":"WCmsDO54FxNs8Hquy","income":107.693359375,"date":"1516575548"},{"machineId":"StlJWLCxLu7Y9QHgR","income":108.87645721435547,"date":"1516575548"},{"machineId":"WCmsDO54FxNs8Hquy","income":107.34089660644531,"date":"1516661944"},{"machineId":"StlJWLCxLu7Y9QHgR","income":109.27538299560547,"date":"1516661944"},{"machineId":"WCmsDO54FxNs8Hquy","income":107.1471939086914,"date":"1516748365"},{"machineId":"StlJWLCxLu7Y9QHgR","income":106.41417694091797,"date":"1516748365"},{"machineId":"WCmsDO54FxNs8Hquy","income":99.83739471435547,"date":"1516834742"},{"machineId":"StlJWLCxLu7Y9QHgR","income":103.26214599609375,"date":"1516834742"},{"machineId":"WCmsDO54FxNs8Hquy","income":104.3499984741211,"date":"1516921150"},{"machineId":"StlJWLCxLu7Y9QHgR","income":101.30999755859375,"date":"1516921150"},{"machineId":"WCmsDO54FxNs8Hquy","income":86.58000183105469,"date":"1517007621"},{"machineId":"StlJWLCxLu7Y9QHgR","income":87.19000244140625,"date":"1517007622"}],"totalincom":1013.0488357543945,"yestodayincom":205.65999603271484}
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
        return "IncomeBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * history : [{"machineId":"WCmsDO54FxNs8Hquy","income":100,"date":"1515914205"},{"machineId":"StlJWLCxLu7Y9QHgR","income":97.7635726928711,"date":"1516057222"},{"machineId":"WCmsDO54FxNs8Hquy","income":98.8448486328125,"date":"1516057222"},{"machineId":"WCmsDO54FxNs8Hquy","income":99.97000885009766,"date":"1516143562"},{"machineId":"StlJWLCxLu7Y9QHgR","income":107.79528045654297,"date":"1516143562"},{"machineId":"StlJWLCxLu7Y9QHgR","income":106.67237854003906,"date":"1516229958"},{"machineId":"WCmsDO54FxNs8Hquy","income":99.8453598022461,"date":"1516229958"},{"machineId":"StlJWLCxLu7Y9QHgR","income":98.06100463867188,"date":"1516316357"},{"machineId":"WCmsDO54FxNs8Hquy","income":108.37203216552734,"date":"1516316357"},{"machineId":"WCmsDO54FxNs8Hquy","income":101.18594360351562,"date":"1516402745"},{"machineId":"StlJWLCxLu7Y9QHgR","income":85.87344360351562,"date":"1516402745"},{"machineId":"WCmsDO54FxNs8Hquy","income":71.86689758300781,"date":"1516489164"},{"machineId":"StlJWLCxLu7Y9QHgR","income":95.91204833984375,"date":"1516489165"},{"machineId":"WCmsDO54FxNs8Hquy","income":107.693359375,"date":"1516575548"},{"machineId":"StlJWLCxLu7Y9QHgR","income":108.87645721435547,"date":"1516575548"},{"machineId":"WCmsDO54FxNs8Hquy","income":107.34089660644531,"date":"1516661944"},{"machineId":"StlJWLCxLu7Y9QHgR","income":109.27538299560547,"date":"1516661944"},{"machineId":"WCmsDO54FxNs8Hquy","income":107.1471939086914,"date":"1516748365"},{"machineId":"StlJWLCxLu7Y9QHgR","income":106.41417694091797,"date":"1516748365"},{"machineId":"WCmsDO54FxNs8Hquy","income":99.83739471435547,"date":"1516834742"},{"machineId":"StlJWLCxLu7Y9QHgR","income":103.26214599609375,"date":"1516834742"},{"machineId":"WCmsDO54FxNs8Hquy","income":104.3499984741211,"date":"1516921150"},{"machineId":"StlJWLCxLu7Y9QHgR","income":101.30999755859375,"date":"1516921150"},{"machineId":"WCmsDO54FxNs8Hquy","income":86.58000183105469,"date":"1517007621"},{"machineId":"StlJWLCxLu7Y9QHgR","income":87.19000244140625,"date":"1517007622"}]
         * totalincom : 1013.0488357543945
         * yestodayincom : 205.65999603271484
         */

        @SerializedName("totalincom")
        private double totalIncome;
        @SerializedName("yestodayincom")
        private double yestodayIncome;
        private List<HistoryBean> history;

        public double getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(double totalIncome) {
            this.totalIncome = totalIncome;
        }

        public double getYestodayIncome() {
            return yestodayIncome;
        }

        public void setYestodayIncome(double yestodayIncome) {
            this.yestodayIncome = yestodayIncome;
        }

        public List<HistoryBean> getHistory() {
            return history;
        }

        public void setHistory(List<HistoryBean> history) {
            this.history = history;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "totalIncome=" + totalIncome +
                    ", yestodayIncome=" + yestodayIncome +
                    ", history=" + history +
                    '}';
        }

        public static class HistoryBean implements Serializable{
            /**
             * machineId : WCmsDO54FxNs8Hquy
             * income : 100
             * date : 1515914205
             */

            private String machineId;
            private double income;
            private String date;

            public String getMachineId() {
                return machineId;
            }

            public void setMachineId(String machineId) {
                this.machineId = machineId;
            }

            public double getIncome() {
                return income;
            }

            public void setIncome(double income) {
                this.income = income;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            @Override
            public String toString() {
                return "HistoryBean{" +
                        "machineId='" + machineId + '\'' +
                        ", income=" + income +
                        ", date='" + date + '\'' +
                        '}';
            }
        }
    }
}
