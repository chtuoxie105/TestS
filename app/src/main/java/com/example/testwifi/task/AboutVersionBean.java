package com.example.testwifi.task;

import java.io.Serializable;

public class AboutVersionBean implements Serializable {
    public static final int VERSION_NO =-1;
    public static final int VERSION_NO_FORCE =0;
    public static final int VERSION_FORCE  =1;

    private String name;
    private int code;//版本号
    private int isUpdate;//是否强制更新 -1:没有版本 1:强制更新 0:不强制更新
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AboutVersionBean{" +
                "name='" + name + '\'' +
                ", code=" + code +
                ", isUpdate=" + isUpdate +
                ", url='" + url + '\'' +
                '}';
    }
}
