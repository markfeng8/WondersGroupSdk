package com.wondersgroup.android.jkcs_sdk.net.interceptor;

import com.wondersgroup.android.jkcs_sdk.utils.JsonUtil;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by x-sir on 2018/8/3 :)
 * Function:LoggerInterceptor
 */
public class LoggerInterceptor implements HttpLoggingInterceptor.Logger {

    private StringBuilder mStringBuilder = new StringBuilder();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mStringBuilder.setLength(0);
        }
        // 以 {} 或者 [] 形式的说明是响应结果的 json 数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}")) || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(message);
        }
        mStringBuilder.append(message.concat("\n"));
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            LogUtil.d(mStringBuilder.toString());
        }
    }
}
