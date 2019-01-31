package com.wondersgroup.android.jkcs_sdk;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.ConfigOption;
import com.wondersgroup.android.jkcs_sdk.utils.LogCatStrategy;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

/**
 * Company:WondersGroup
 * Created by x-sir on 2018/7/31 :)
 * Function:WondersSdk initialize singleton class.
 */
public class WondersSdk {

    private static final String TAG = "WondersSdk";

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
        initEpSoft(context);
        initLogger(getIsDebug(option));
    }

    private void initEpSoft(Context context) {
        AuthCall.initApplication((Application) context);
        AuthCall.initSDK(context, "6151490102",
                result -> LogUtil.e(TAG, "result===" + result));
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
            if (!TextUtils.isEmpty(env) && "test".equals(env.toLowerCase())) {
                SpUtil.getInstance().save(SpKey.SDK_ENV, env);
            } else {
                SpUtil.getInstance().save(SpKey.SDK_ENV, "");
            }
        }

        return isDebug;
    }

}
