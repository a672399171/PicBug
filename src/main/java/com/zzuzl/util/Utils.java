package com.zzuzl.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
public class Utils {
    private static final String[] ARRAY = {".jpg", ".png", ".gif"};
    private static Logger logger = LogManager.getLogger(Utils.class);

    public static Elements parseHtml(String url, String selector) throws Exception {
        if (!url.startsWith("http") && !url.startsWith("https")) {
            return null;
        }
        Document doc = null;
        Elements elements = null;
        doc = Jsoup.connect(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
                .timeout(5000)
                .get();
        elements = doc.select(selector);
        return elements;
    }

    public static boolean isNumber(String s) {
        if (s == null) {
            return false;
        }
        Pattern p = Pattern.compile("\\d+");
        Matcher matcher = p.matcher(s);
        return matcher.find();
    }
}
