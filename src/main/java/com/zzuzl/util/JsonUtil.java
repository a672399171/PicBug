package com.zzuzl.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/9.
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Logger logger = LogManager.getLogger(JsonUtil.class);

    /**
     * 转化为json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return json;
    }

    /**
     * 转化为对象
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        T t = null;
        try {
            t = objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error(e);
        }
        return t;
    }

    /**
     * 转化为对象
     * @param file
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromJson(File file, Class<T> tClass) {
        T t = null;
        try {
            t = objectMapper.readValue(file, tClass);
        } catch (IOException e) {
            logger.error(e);
        }
        return t;
    }
}
