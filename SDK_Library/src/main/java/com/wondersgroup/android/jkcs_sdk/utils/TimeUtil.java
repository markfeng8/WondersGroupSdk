package com.wondersgroup.android.jkcs_sdk.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by x-sir on 2018/08/02 :)
 * Function:获取当前时间工具类
 */
public class TimeUtil {

    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdf1 =
            new SimpleDateFormat("yyyyMMddHHmmss");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdf2 =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdf3 =
            new SimpleDateFormat("yyyy/MM/dd HH:mm");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdf4 =
            new SimpleDateFormat("yyyy-MM-dd");
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdf5 =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 2018-09-13 16:55:11
    private static final String TAG = TimeUtil.class.getSimpleName();


    /**
     * 返回如下格式的当前时间
     *
     * @return 20180803093610
     */
    public static String getSecondsTime() {
        Date date = new Date(System.currentTimeMillis());
        String format = sdf1.format(date);
        LogUtil.i(TAG, "format time===" + format);
        return format;
    }

    public static long getScrollMinTime() {
        Date date = null;
        try {
            date = sdf4.parse("2018-01-01");
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
            lockOrderTime = sdf5.parse(lockStartTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lockOrderTime != null) {
            long time = lockOrderTime.getTime();
            reduceTime = (30 * 60 * 1000) - (currentTimeMillis - time);
        }

        return reduceTime;
    }

    public static long getMillis(String strDate) {
        long millis = 0;
        try {
            Date date = sdf4.parse(strDate);
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
            date = sdf4.parse(dateStr);
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
        String format = sdf4.format(date);
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
        String format = sdf5.format(date);
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
        String format = sdf4.format(date);
        LogUtil.i(TAG, "getBeforeDate()===" + format);
        return format;
    }

    /**
     * 获取昨天的日期
     *
     * @return "yyyy-MM-dd"
     */
    public static String getLastDay(long todayMillis) {
        long millis = todayMillis - 1000L * 60L * 60L * 24L;
        return sdf4.format(new Date(millis));
    }

    /**
     * 返回如下格式的当天时间
     *
     * @return 2018-08-03
     */
    public static String getDate(long millis) {
        Date date = new Date(millis);
        String format = sdf4.format(date);
        LogUtil.i(TAG, "getDate()===" + format);
        return format;
    }

    public static String getMinMillis(String inHosDate) {
        return isOver90Days(inHosDate) ? String.valueOf(getBeforeDayMillis(90)) : String.valueOf(getMillis(inHosDate));
    }
}
