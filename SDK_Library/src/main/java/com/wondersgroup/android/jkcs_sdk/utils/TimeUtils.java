package com.wondersgroup.android.jkcs_sdk.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by x-sir on 2018/08/02 :)
 * Function:时间处理工具类
 */
public class TimeUtils {

    public static final String TAG = "TimeUtils";
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SDF1 = new SimpleDateFormat("yyyyMMddHHmmss");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SDF2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SDF3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SDF4 = new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat SDF5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 返回如下格式的当前时间
     *
     * @return 20180803093610
     */
    public static String getSecondsTime() {
        Date date = new Date(System.currentTimeMillis());
        String format = SDF1.format(date);
        LogUtil.i(TAG, "format time===" + format);
        return format;
    }

    public static long getScrollMinTime() {
        Date date = null;
        try {
            date = SDF4.parse("2018-01-01");
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
            lockOrderTime = SDF5.parse(lockStartTime);
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
    public static long convertToMillis(SimpleDateFormat sdf, String strDate) {
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
            date = SDF4.parse(dateStr);
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
        String format = SDF4.format(date);
        LogUtil.i(TAG, "getCurrentDate()===" + format);
        return format;
    }

    /**
     * 返回如下格式的当天时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        Date date = new Date(System.currentTimeMillis());
        String format = SDF5.format(date);
        LogUtil.i(TAG, "getCurrentDate()===" + format);
        return format;
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
        String format = SDF4.format(date);
        LogUtil.i(TAG, "getBeforeDate()===" + format);
        return format;
    }

    /**
     * 比较 date1 是否比 date2 的时间小
     *
     * @param sdf   时间格式
     * @param date1 格式化的时间字符串 date1
     * @param date2 格式化的时间字符串 date1
     * @return 如果 date1 比 date2 小，返回 true，否则返回 false
     */
    public static boolean compareBefore(SimpleDateFormat sdf, String date1, String date2) {
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
    public static boolean compareAfter(SimpleDateFormat sdf, String date1, String date2) {
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
        return SDF4.format(new Date(millis));
    }

    /**
     * 返回如下格式的当天时间
     *
     * @return 2018-08-03
     */
    public static String getDate(long millis) {
        Date date = new Date(millis);
        String format = SDF4.format(date);
        LogUtil.i(TAG, "getDate()===" + format);
        return format;
    }

    public static long getMinMillis(String inHosDate) {
        return isOver90Days(inHosDate) ? getBeforeDayMillis(90) : convertToMillis(TimeUtils.SDF4, inHosDate);
    }
}
