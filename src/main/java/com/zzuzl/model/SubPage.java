package com.zzuzl.model;

import com.zzuzl.dao.PicDao;
import com.zzuzl.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.select.Elements;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */
public class SubPage {
    private String url;
    private WebSite webSite;
    private Logger logger = LogManager.getLogger(getClass());

    public SubPage(String url, WebSite webSite) {
        this.url = url;
        this.webSite = webSite;
    }

    public void start(String key) {
        logger.info(url + " start.");
        Elements elements = null;
        try {
            elements = Utils.parseHtml(url, webSite.getPageListSelector());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        List<Pic> picList = new ArrayList<Pic>();
        if (elements != null && elements.size() > 0) {
            for (int i = 0; i < elements.size(); i++) {
                List<String> list = new Page(elements.eq(i).attr("href"), webSite).start();
                if (list != null && list.size() > 0) {
                    for (String s : list) {
                        Pic pic = new Pic();
                        pic.setUrl(s);
                        pic.setKey(key);
                        picList.add(pic);
                    }
                }
            }
        }

        int count = webSite.getPicDao().batchInsert(picList);
        logger.info("insert [" + key + "],size:" + count);
    }
}
