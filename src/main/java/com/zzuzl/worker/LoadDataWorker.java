package com.zzuzl.worker;

import com.zzuzl.dao.PicDao;
import com.zzuzl.model.Area;
import com.zzuzl.model.JsonVo;
import com.zzuzl.model.WebSite;
import com.zzuzl.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时加载数据
 */
public class LoadDataWorker {
    @Resource
    private PicDao picDao;
    private Logger logger = LogManager.getLogger(getClass());

    public void execute() {
        logger.info("load start...");
        try {
            List<JsonVo> list = picDao.listJson();
            if (list != null && list.size() > 0) {
                for (JsonVo vo : list) {
                    WebSite webSite = JsonUtil.fromJson(vo.getJson(), WebSite.class);
                    if (webSite != null && webSite.getAreas() != null && webSite.getAreas().size() > 0) {
                        webSite.setPicDao(picDao);
                        for (Area area : webSite.getAreas()) {
                            area.setWebSite(webSite);
                            area.start();
                        }
                    } else {
                        logger.error("website:{} error!", vo.getKey());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("load error:" + e.getMessage());
        }

        logger.info("load end...");
    }
}
