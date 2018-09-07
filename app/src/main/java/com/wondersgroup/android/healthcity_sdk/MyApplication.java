package com.wondersgroup.android.healthcity_sdk;

import android.app.Application;

import com.wondersgroup.android.jkcs_sdk.WondersSdk;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * @param context Application context
         * @param isDebug 是否需要打印日志信息
         */
        WondersSdk.getInstance().init(this, true);
    }
}
