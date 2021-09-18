package com.shine.common.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/8/29 11:18
 * fileName：DateUtils
 * Use：
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_START_PATTERN = "yyyy-MM-dd 00:00:00";
    private static final String DATE_TIME_END_PATTERN = "yyyy-MM-dd 23:59:59";
    private static final String DATE_TIME_PATTERN_STR = "HHmmssSSS";
    private static final String YEAR_MONTH_PATTERN = "yyyyMM";
    private static final String DAY_PATTERN = "dd";

    public static String getTodayStartTime() {
        return DateTime.now().toString(DATE_TIME_START_PATTERN);
    }

    public static String getTodayEndTime() {
        return DateTime.now().toString(DATE_TIME_END_PATTERN);
    }

    public static String getNowDateString() {
        return DateTime.now().toString(DATE_PATTERN);
    }

    public static String getDateTimeString() {
        return DateTime.now().toString(DATE_TIME_PATTERN);
    }

    public static String getYearAndMonthTimeString() {
        return DateTime.now().toString(YEAR_MONTH_PATTERN);
    }

    public static String getDayTimeString() {
        return DateTime.now().toString(DAY_PATTERN);
    }

    public static String getDatePath() {
        DateTime now = DateTime.now();
        return now.toString(YEAR_MONTH_PATTERN) + "/" + now.toString(DAY_PATTERN);
    }

    public static String getNowTimeString() {
        SimpleDateFormat myFormat = new SimpleDateFormat(DATE_TIME_PATTERN_STR);
        return myFormat.format(new Date());
    }
}
