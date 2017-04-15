package com.zzuzl.model;

import com.zzuzl.dao.PicDao;
import com.zzuzl.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.*;

/**
 * 站点类
 */
public class WebSite {
    private String domain;
    private String pageNoSelector;
    private String pageListSelector;
    private String imgSelector;
    private List<Area> areas;
    private PicDao picDao;
    // 日志
    private Logger logger = LogManager.getLogger(getClass());

    public WebSite(String domain, String pageNoSelector, String pageListSelector, String imgSelector) {
        this.domain = domain;
        this.pageNoSelector = pageNoSelector;
        this.pageListSelector = pageListSelector;
        this.imgSelector = imgSelector;
    }

    public WebSite() {
    }

    // 开始
    public void start() {
        if(picDao == null) {
            logger.error("picDao is null");
            return;
        }
        logger.info(getDomain() + " start download.");
        if (areas != null && areas.size() > 0) {
            for(Area area : areas) {
                area.start();
            }
        }
    }

    public String getPageNoSelector() {
        return pageNoSelector;
    }

    public String getPageListSelector() {
        return pageListSelector;
    }

    public String getImgSelector() {
        return imgSelector;
    }

    public String getDomain() {
        return domain;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public PicDao getPicDao() {
        return picDao;
    }

    public void setPicDao(PicDao picDao) {
        this.picDao = picDao;
    }
}
