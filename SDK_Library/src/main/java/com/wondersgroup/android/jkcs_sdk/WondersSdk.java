package com.wondersgroup.android.jkcs_sdk;

import android.app.Application;
import android.content.Context;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.tencent.bugly.crashreport.CrashReport;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

/**
 * Company:WondersGroup
 * Created by x-sir on 2018/7/31 :)
 * Function:WondersSdk initialize singleton class.
 */
public class WondersSdk {

    private WondersSdk() {
    }

    public static WondersSdk getInstance() {
        return WondersGroupSdkHolder.holder;
    }

    private static class WondersGroupSdkHolder {
        private static WondersSdk holder = new WondersSdk();
    }

    public void init(Context context, boolean isDebug) {
        WondersApplication.sContext = context.getApplicationContext();
        initLog(isDebug);
        initEpSoft(context);
    }

    private void initEpSoft(Context context) {
        CrashReport.initCrashReport(context, "060d9fa097", false);
        AuthCall.initApplication((Application) context);
    }

    private void initLog(boolean isDebug) {
        LogUtil.setIsNeedPrintLog(isDebug);
    }

}
