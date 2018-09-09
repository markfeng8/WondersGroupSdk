package com.wondersgroup.android.jkcs_sdk.utils;

import android.annotation.SuppressLint;

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

}
