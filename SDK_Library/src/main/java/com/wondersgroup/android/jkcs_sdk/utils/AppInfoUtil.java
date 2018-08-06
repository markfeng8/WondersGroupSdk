package com.wondersgroup.android.jkcs_sdk.utils;

import android.content.Context;

/**
 * Created by x-sir on 2018/8/6 :)
 * Function:
 */
public class AppInfoUtil {

    public static final String APPLICATION_ID = "android.support.v7.appcompat";

    /**
     * get package name.
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        String packageName = "";
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            packageName = applicationContext.getPackageName();
        } else {
            throw new RuntimeException("context is null!");
        }
        return packageName;
    }
}
