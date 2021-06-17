package com.wondersgroup.android.sdk.net.interceptor;

import android.support.annotation.NonNull;

import com.wondersgroup.android.sdk.utils.JsonUtil;
import com.wondersgroup.android.sdk.utils.LogUtil;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by x-sir on 2018/8/3 :)
 * Function:LoggerInterceptor
 */
public class LoggerInterceptor implements HttpLoggingInterceptor.Logger {

    private static final String TAG = "LoggerInterceptor";

    @Override
    public void log(@NonNull String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            LogUtil.d(TAG, message);
        }
        if (message.startsWith("<-- HTTP FAILED")) {
            LogUtil.d(TAG, message);
        }
// 以 {} 或者 [] 形式的说明是响应结果的 json 数据，需要进行格式化
        boolean flag1 = message.startsWith("{") && message.endsWith("}");
        boolean flag2 = message.startsWith("[") && message.endsWith("]");
        if (flag1) {
            message = JsonUtil.formatJson(message);
            LogUtil.d(TAG, message);
        }
        if (flag2) {
            message = JsonUtil.formatJson(message);
            LogUtil.d(TAG, message);
        }
// 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LogUtil.d(TAG, "\n----------- END -----------");
        }
    }
}
