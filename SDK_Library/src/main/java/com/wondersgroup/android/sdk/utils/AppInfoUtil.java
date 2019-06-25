package com.wondersgroup.android.sdk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by x-sir on 2018/8/6 :)
 * Function:
 */
public class AppInfoUtil {

    /**
     * get package name.
     *
     * @param context application context
     * @return current app's package name.
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

    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isWeChatAppInstalled(Context context) {
        if (context != null) {
            Context appContext = context.getApplicationContext();
            // 获取packageManager
            final PackageManager packageManager = appContext.getPackageManager();
            // 获取所有已安装程序的包信息
            List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
            if (pInfo != null) {
                for (int i = 0; i < pInfo.size(); i++) {
                    String pn = pInfo.get(i).packageName;
                    if (pn.equalsIgnoreCase("com.tencent.mm")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
