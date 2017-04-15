package com.zzuzl.util;

import com.zzuzl.dto.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by zhanglei53 on 2016/8/2.
 */
public class NetUtil {

    public static Result connect(String url, String schoolNum, String password, String selec) {
        Result result = new Result();
        String nianji = schoolNum.substring(0, 4);
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .timeout(10 * 1000)
                    .data("nianji", nianji)
                    .data("xuehao", schoolNum)
                    .data("mima", password)
                    .data("selec", selec)
                    .post();
        } catch (IOException e) {
            result.setSuccess(false);
            result.setError(e.getMessage());
            e.printStackTrace();
        }

        if (document != null) {
            Elements elements = document.body().select(selec);
            if (elements != null && elements.size() == 1) {
                result.setSuccess(true);
                // 身份有效
            } else {
                result.setSuccess(false);
                result.setError("学号或密码错误");
            }
        } else {
            result.setSuccess(false);
            result.setError("连接错误");
        }

        return result;
    }
}
