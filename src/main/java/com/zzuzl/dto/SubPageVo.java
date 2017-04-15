package com.zzuzl.dto;

import com.zzuzl.model.Pic;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */
public class SubPageVo {
    private String key;
    private List<Pic> picList;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Pic> getPicList() {
        return picList;
    }

    public void setPicList(List<Pic> picList) {
        this.picList = picList;
    }
}
