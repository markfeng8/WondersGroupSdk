package com.wondersgroup.android.jkcs_sdk.cons;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:request url constants.
 */
public class RequestUrl {

    private static final String TAG = "RequestUrl";

    public static final String HOST = isTestEnv() ? "http://122.225.124.34:39008" : "http://115.238.228.2:7001";

    /**
     * 门诊部分接口
     */
    public static final String XY0002 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0002";
    public static final String XY0001 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0001";
    public static final String XY0003 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0003";
    public static final String XY0004 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0004";
    public static final String XY0005 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0005";
    public static final String XY0006 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0006";
    public static final String XY0008 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0008";

    /**
     * 账单部分接口
     */
    public static final String YD0001 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0001";
    public static final String YD0002 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0002";
    public static final String YD0003 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0003";
    public static final String YD0004 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0004";
    public static final String YD0005 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0005";
    public static final String YD0006 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0006";
    public static final String YD0007 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0007";
    public static final String YD0008 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0008";
    public static final String YD0009 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0009";
    public static final String YD0010 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0010";

    /**
     * 住院部分接口
     */
    public static final String CY0001 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/cy0001";
    public static final String CY0002 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/cy0002";
    public static final String CY0003 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/cy0003";
    public static final String CY0004 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/cy0004";
    public static final String CY0005 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/cy0005";
    public static final String CY0006 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/cy0006";
    public static final String CY0007 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/cy0007";

    /**
     * 统一支付回调地址
     */
    public static final String SDK_TO_BILL = (isTestEnv() ? "test/" : "") + "huzh_credit/sdk/sdktobill";

    private static boolean isTestEnv() {
        boolean isTestEnv = false;
        String env = SpUtil.getInstance().getString(SpKey.SDK_ENV, "");
        if (!TextUtils.isEmpty(env) && "test".equals(env)) {
            isTestEnv = true;
        }
        LogUtil.i(TAG, "WondersGroup sdk 当前的环境是===" + (isTestEnv ? "[测试环境]" : "[正式环境]"));
        return isTestEnv;
    }

}
