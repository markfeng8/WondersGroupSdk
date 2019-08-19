package com.wondersgroup.android.sdk;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.ConfigOption;
import com.wondersgroup.android.sdk.utils.LogCatStrategy;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;

import java.util.Locale;

import cn.com.epsoft.zjessc.ZjEsscSDK;

/**
 * Company:WondersGroup
 * Created by x-sir on 2018/7/31 :)
 * Function:WondersSdk initialize singleton class.
 */
public class WondersSdk {

    private static final String TAG = "WondersSdk";
    private static final String CHANNEL_NO = "3305000003";
    private static final String TEST = "test";

    private WondersSdk() {
    }

    public static WondersSdk getInstance() {
        return WondersGroupSdkHolder.holder;
    }

    private static class WondersGroupSdkHolder {
        private static WondersSdk holder = new WondersSdk();
    }

    public void init(Context context, ConfigOption option) {
        WondersApplication.sContext = context.getApplicationContext();
        // 处理过渡到加密数据
        SpUtil.getInstance().handleTransition();
        //initEpSoft(context, getIsDebug(option));
        initEpSoft(context, false);
        initLogger(getIsDebug(option));
        LogUtil.i(TAG, "WondersSdk initialize success~");
    }

    /**
     * 集成省电子社保卡
     */
    private void initEpSoft(Context context, boolean isDebug) {
        // isDebug 为 true 时是测试环境，false 时为正式环境
        ZjEsscSDK.init(isDebug, (Application) context, CHANNEL_NO);
        // 设置主题颜色
        ZjEsscSDK.setTitleColor("#1E90FF");
        // 设置主题字体颜色
        ZjEsscSDK.setTextColor("#FFFFFF");
        // 设置是否打印日志
        ZjEsscSDK.setLogDebug(isDebug);
    }

    private void initLogger(final boolean isDebug) {
        PrettyFormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(new LogCatStrategy())
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(strategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });

        LogUtil.setIsNeedPrintLog(isDebug);
    }

    private boolean getIsDebug(ConfigOption option) {
        boolean isDebug = false;
        if (option != null) {
            isDebug = option.isDebug();
            String env = option.getEnv();
            SpUtil.getInstance().save(SpKey.SDK_DEBUG, isDebug);
            if (!TextUtils.isEmpty(env) && TEST.equals(env.toLowerCase(Locale.US))) {
                SpUtil.getInstance().save(SpKey.SDK_ENV, env);
            } else {
                SpUtil.getInstance().save(SpKey.SDK_ENV, "");
            }
        }

        return isDebug;
    }

    public static String getChannelNo() {
        return CHANNEL_NO;
    }
}
