/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import android.text.TextUtils;

/**
 * Created by x-sir on 2019-08-22 :)
 * Function:
 */
public class StringUtils {

    /**
     * 获取打马赛克的身份证号码
     *
     * @param idNum 身份证号码
     * @return result
     */
    public static String getMosaicIdNum(String idNum) {
        if (!TextUtils.isEmpty(idNum) && idNum.length() == 18) {
            String start = idNum.substring(0, 10);
            String end = idNum.substring(idNum.length() - 4);
            return start + "****" + end;
        } else {
            return "";
        }
    }
}
