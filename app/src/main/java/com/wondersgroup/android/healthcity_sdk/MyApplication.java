package com.wondersgroup.android.healthcity_sdk;

import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.crashreport.CrashReport;
import com.wondersgroup.android.sdk.WondersImp;
import com.wondersgroup.android.sdk.WondersSdk;
import com.wondersgroup.android.sdk.entity.ConfigOption;
import com.wondersgroup.android.sdk.entity.WondersExternParams;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        ConfigOption option = new ConfigOption()
                .setDebug(true)
                //.setDebug(BuildConfig.DEBUG) // 设置是否为调试模式(调试模式可以打印日志，上线后建议设为 false)
                .setEnv("test"); // 设置环境(test 或 TEST 为测试环境，不设置或者其他默认为正式环境)
        // Application context & option parameters.
        WondersSdk.getInstance().init(this, option);

        // 测试阶段建议设置成 true，发布时设置为 false
        CrashReport.initCrashReport(getApplicationContext(), "0010f85d4e", BuildConfig.DEBUG);

    }
}
