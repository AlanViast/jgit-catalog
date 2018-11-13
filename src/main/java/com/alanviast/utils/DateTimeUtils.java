package com.alanviast.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

/**
 * @author AlanViast
 */
public class DateTimeUtils {


    /**
     * 将时间戳转成对应的LocalDateTime
     *
     * @param timestamp 时间戳
     * @return 时间
     */
    public static LocalDateTime fromTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
    }

    /**
     * 判断某个时间是否在几天内
     *
     * @param localDateTime 开始时间距离当前时间
     * @param dayCount      时间距离
     * @return 是否在几天内
     */
    public static boolean inFewDay(LocalDateTime localDateTime, int dayCount) {
        return localDateTime.plusDays(dayCount).isAfter(LocalDateTime.now());
    }
}
