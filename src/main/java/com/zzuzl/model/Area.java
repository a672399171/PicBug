package com.zzuzl.model;

import com.zzuzl.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Area {
    private String name;
    private String url;
    private String key;
    private int page;
    private WebSite webSite;
    private static ExecutorService service;
    private Logger logger = LogManager.getLogger(getClass());

    public Area(String name, String url, String key, int page) {
        this.name = name;
        this.url = url;
        this.key = key;
        this.page = page;
    }

    public Area() {
    }

    public void start() {
        if (service == null) {
            service = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
        }

        logger.info(getName() + " start.");
        List<String> urls = generateUrls(webSite.getPageNoSelector(), webSite.getDomain());
        for (String url : urls) {
            final SubPage subPage = new SubPage(url, webSite);
            service.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        subPage.start(key);
                    } catch (Exception e) {
                        logger.error(e);
                    }
                }
            });
        }
    }

    // 生成所有url的列表
    public List<String> generateUrls(String selector, String domain) {
        List<String> urls = new ArrayList<String>();
        urls.add(url);

        Elements elements = null;
        try {
            elements = Utils.parseHtml(url, selector);
        } catch (Exception e) {
            logger.error(e.getMessage() + url);
        }

        int index = 1;
        if (elements != null && elements.size() > 0) {
            for (int i = 0; i < elements.size() && index < page; i++) {
                if (Utils.isNumber(elements.get(i).text())) {
                    urls.add(domain + elements.get(i).attr("href"));
                    index++;
                }
            }
        } else {
            logger.error("elements is null! " + url);
        }

        return urls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public WebSite getWebSite() {
        return webSite;
    }

    public void setWebSite(WebSite webSite) {
        this.webSite = webSite;
    }

}
