package com.wondersgroup.android.healthcity_sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by xpf on 2017/5/20 :)
 * Function:
 */
public class AppInfoUtil {

    public static String getVersionName(Context context) {
        String versionName = "";
        if (context != null) {
            try {
                PackageInfo packInfo = null;
                Context applicationContext = context.getApplicationContext();
                PackageManager packageManager = applicationContext.getPackageManager();
                packInfo = packageManager.getPackageInfo(applicationContext.getPackageName(), 0);
                versionName = packInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("context is null!");
        }
        return versionName;
    }

}
