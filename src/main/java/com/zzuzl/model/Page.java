package com.zzuzl.model;

import com.zzuzl.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */
public class Page {
    private String url;
    private WebSite webSite;
    private Logger logger = LogManager.getLogger(getClass());

    public Page(String url, WebSite webSite) {
        this.url = url;
        this.webSite = webSite;
    }

    public List<String> start() {
        List<String> list = new ArrayList<String>();
        Elements imgUrls = null;
        try {
            imgUrls = Utils.parseHtml(webSite.getDomain() + url, webSite.getImgSelector());
        } catch (Exception e) {
            logger.error("错误：picPage=" + webSite.getDomain() + url, e);
        }

        if (imgUrls != null) {
            for (int i = 0; i < imgUrls.size(); i++) {
                list.add(imgUrls.eq(i).attr("src"));
            }
        }
        return list;
    }
}
