package com.ittianyu.bcdnwatcher.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 86839 on 2018/1/27.
 */

public class MinerBean implements Serializable {

    /**
     * RetCode : 500200
     * Description : get arrayMinnerCode success
     * Err :
     * data : {"CodeList":[{"code":"xxxxx","onlineStatus":1},{"code":"xxxx","onlineStatus":1}]}
     */

    @SerializedName("RetCode")
    private int retCode;
    @SerializedName("Description")
    private String description;
    @SerializedName("Err")
    private String err;
    private DataBean data;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        @SerializedName("CodeList")
        private List<CodeListBean> codeList;

        public List<CodeListBean> getCodeList() {
            return codeList;
        }

        public void setCodeList(List<CodeListBean> codeList) {
            this.codeList = codeList;
        }

        public static class CodeListBean {
            /**
             * code : WCmsDO54FxNs8Hquy
             * onlineStatus : 1
             */

            private String code;
            private int onlineStatus;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getOnlineStatus() {
                return onlineStatus;
            }

            public void setOnlineStatus(int onlineStatus) {
                this.onlineStatus = onlineStatus;
            }
        }
    }
}
