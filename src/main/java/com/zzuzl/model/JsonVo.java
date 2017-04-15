package com.zzuzl.model;

/**
 * Created by Administrator on 2017/4/15.
 */
public class JsonVo {
    private String key;
    private String json;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return "JsonVo{" +
                "key='" + key + '\'' +
                ", json='" + json + '\'' +
                '}';
    }
}
