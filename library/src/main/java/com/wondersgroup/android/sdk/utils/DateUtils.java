package com.wondersgroup.android.sdk.utils;

import android.annotation.SuppressLint;
import android.os.Build;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Created by x-sir on 2018/08/02 :)
 * Function:日期时间处理工具类
 */
public class DateUtils {

    private static final String TAG = "DateUtils";
    private static final String PATTERN_YYYY_MM_DD_HH_MM_SS1 = "yyyyMMddHHmmss";
    private static final String PATTERN_YYYY_MM_DD_HH_MM_SS2 = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN_YYYY_MM_DD_HH_MM_SSS3 = "yyyyMMddHHmmsss";
    private static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 如果是 JDK8 的应用，可以使用 instant 代替 Date，LocalDatetime 代替 Calendar，
     * DateTimeFormatter 代替 SimpleDateFormatter，官方给出的解释：simple beautiful strong immutable thread-safe
     */
    private static final ThreadLocal<DateFormat> THREAD_LOCAL_DATE_FORMAT1 = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM_SS1);
        }
    };

    private static final ThreadLocal<DateFormat> THREAD_LOCAL_DATE_FORMAT2 = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM_SS2);
        }
    };

    private static final ThreadLocal<DateFormat> THREAD_LOCAL_DATE_FORMAT4 = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_YYYY_MM_DD_HH_MM_SSS3);
        }
    };

    private static final ThreadLocal<DateFormat> THREAD_LOCAL_DATE_FORMAT3 = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(PATTERN_YYYY_MM_DD);
        }
    };

    /**
     * 返回如下格式的当前时间（精确到秒）
     *
     * @return 20180803093610
     */
    public static String getTheNearestSecondTime() {
        Date date = new Date(System.currentTimeMillis());
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT1).format(date);
    }

    /**
     * 返回如下格式的当前时间（精确到秒）
     *
     * @return 20180803093610
     */
    public static String getTheNearestSecondTime2() {
        Date date = new Date(System.currentTimeMillis());
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT4).format(date);
    }

    public static long getScrollMinTime() {
        Date date = null;
        try {
            date = getDateFormat(THREAD_LOCAL_DATE_FORMAT3).parse("2018-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date == null ? 0L : date.getTime();
    }

    public static long getBefore90DayTime() {
        long beforeTime = 1000L * 60L * 60L * 24L * 90L;
        return System.currentTimeMillis() - beforeTime;
    }

    public static long getBeforeDayMillis(int day) {
        long beforeTime = 1000L * 60L * 60L * 24L * day;
        return System.currentTimeMillis() - beforeTime;
    }

    /**
     * 返回倒计时毫秒数
     *
     * @param lockStartTime
     */
    public static long getCountDownMillis(String lockStartTime) {
        long reduceTime = 0;
        long currentTimeMillis = System.currentTimeMillis();
        Date lockOrderTime = null;
        try {
            lockOrderTime = getDateFormat(THREAD_LOCAL_DATE_FORMAT2).parse(lockStartTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lockOrderTime != null) {
            long time = lockOrderTime.getTime();
            reduceTime = (30 * 60 * 1000) - (currentTimeMillis - time);
        }

        return reduceTime;
    }

    /**
     * 将格式化的时间字符串转为对应的毫秒数
     *
     * @param sdf     时间格式
     * @param strDate 字符串日期
     * @return 毫秒数
     */
    public static long convertToMillis(DateFormat sdf, String strDate) {
        long millis = 0L;
        try {
            Date date = sdf.parse(strDate);
            millis = date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return millis;
    }

    /**
     * 返回当前时间的毫秒数
     */
    public static String getCurrentMillis() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 判断传入的时间是否超过当前时间 30 min
     */
    public static boolean isOver30min(String time) {
        long millis = Long.parseLong(time);
        long curTime = System.currentTimeMillis();
        return (curTime - millis) > (30 * 60 * 1000);
    }

    public static boolean isOver90Days(String dateStr) {
        Date date = null;
        try {
            date = getDateFormat(THREAD_LOCAL_DATE_FORMAT3).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date != null && date.before(new Date(getBeforeDayMillis(90)));
    }

    /**
     * 返回如下格式的当天时间
     *
     * @return 2018-08-03
     */
    public static String getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT3).format(date);
    }

    /**
     * 返回如下格式的当天时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        Date date = new Date(System.currentTimeMillis());
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT2).format(date);
    }

    /**
     * 返回当前时间向前推 30 天
     *
     * @return 2018-08-03
     */
    public static String getBefore30Date() {
        return getBeforeDate(30);
    }

    @SuppressWarnings("NumericOverflow")
    public static String getBeforeDate(int day) {
        long beforeTime = 1000L * 60L * 60L * 24L * day;
        long millis = System.currentTimeMillis() - beforeTime;
        Date date = new Date(millis);
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT3).format(date);
    }

    /**
     * 比较 date1 是否比 date2 的时间小
     *
     * @param sdf   时间格式
     * @param date1 格式化的时间字符串 date1
     * @param date2 格式化的时间字符串 date1
     * @return 如果 date1 比 date2 小，返回 true，否则返回 false
     */
    public static boolean compareBefore(DateFormat sdf, String date1, String date2) {
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = sdf.parse(date1);
            d2 = sdf.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (d1 == null || d2 == null) {
            return false;
        }

        return d1.before(d2);
    }

    /**
     * 比较 date1 是否比 date2 的时间大
     *
     * @param sdf   时间格式
     * @param date1 格式化的时间字符串 date1
     * @param date2 格式化的时间字符串 date1
     * @return 如果 date1 比 date2 大，返回 true，否则返回 false
     */
    public static boolean compareAfter(DateFormat sdf, String date1, String date2) {
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = sdf.parse(date1);
            d2 = sdf.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (d1 == null || d2 == null) {
            return false;
        }

        return d1.after(d2);
    }

    /**
     * 获取昨天的日期
     *
     * @return "yyyy-MM-dd"
     */
    public static String getLastDay(long todayMillis) {
        long millis = todayMillis - 1000L * 60L * 60L * 24L;
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT3).format(new Date(millis));
    }

    /**
     * 返回如下格式的当天时间
     *
     * @return 2018-08-03
     */
    public static String getDate(long millis) {
        Date date = new Date(millis);
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT3).format(date);
    }

    public static long getMinMillis(String inHosDate) {
        return isOver90Days(inHosDate) ? getBeforeDayMillis(90) : convertToMillis(getDateFormat(THREAD_LOCAL_DATE_FORMAT3), inHosDate);
    }

    /**
     * 获取当前Date
     *
     * @return Date object
     */
    public static Date getNowDateObject() {
        if (Build.VERSION.SDK_INT >= 28) {
            Instant instant = Instant.now();
            return Date.from(instant);
        } else {
            return new Date(System.currentTimeMillis());
        }
    }

    /**
     * 获取 DateFormat
     *
     * @param format ThreadLocal
     * @return DateFormat
     */
    private static DateFormat getDateFormat(ThreadLocal<DateFormat> format) {
        return format.get();
    }

    public static DateFormat getDateFormat3() {
        return getDateFormat(THREAD_LOCAL_DATE_FORMAT3);
    }
}
