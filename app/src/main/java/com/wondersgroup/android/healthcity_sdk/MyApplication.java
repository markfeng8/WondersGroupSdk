package com.wondersgroup.android.healthcity_sdk;

import android.app.Application;

import com.wondersgroup.android.jkcs_sdk.WondersSdk;
import com.wondersgroup.android.jkcs_sdk.entity.ConfigOption;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ConfigOption option = new ConfigOption()
                .setDebug(true) // 设置是否为调试模式(调试模式可以打印日志，上线后建议设为 false)
                .setEnv("test"); // 设置环境(test 或 TEST 为测试环境，不设置或者其他默认为正式环境)
        // Application context & option parameters.
        WondersSdk.getInstance().init(this, option);
    }
}
