package com.alanviast.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author AlanViast
 */
public class JsonUtils {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
    }

    /**
     * 格式化对象为字符串
     *
     * @param object 对象
     * @param <T>    数据类型
     * @return JSON字符串
     */
    public static <T> String format(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON对象解析成一个对象
     *
     * @param content 内容
     * @param tClass  对应的类型
     * @param <T>     对应的泛型
     * @return 解析成功后的对象
     */
    public static <T> T parse(String content, Class<T> tClass) {
        try {
            return objectMapper.readValue(content, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
