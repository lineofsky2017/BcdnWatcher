package com.ittianyu.bcdnwatcher.common.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 86839 on 2018/1/31.
 */

public class VersionBean implements Serializable {

    /**
     * last_version : 1
     * version_name : 1.0.0
     * content :
     * url :
     */

    @SerializedName("last_version")
    private int lastVersion;
    @SerializedName("version_name")
    private String versionName;
    private String content;
    private String url;

    public int getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(int lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "lastVersion=" + lastVersion +
                ", versionName='" + versionName + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
