package com.wondersgroup.android.jkcs_sdk.cons;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

/**
 * Company:WondersGroup
 * Created by x-sir on 2018/8/1 :)
 * Function:request url constants.
 */
public class RequestUrl {

    private static final String TAG = "RequestUrl";

    public static final String HOST = "http://122.225.124.34:39008";

    // 签约状态查询
    public static final String XY0002 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0002";
    public static final String XY0001 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0001";
    public static final String XY0003 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0003";
    public static final String XY0004 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0004";
    public static final String XY0005 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0005";
    // 获取验证码
    public static final String XY0006 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0006";
    // 获取医院列表
    public static final String XY0008 = (isTestEnv() ? "/test" : "") + "/huzh_credit/ct/xy0008";
    // 移动支付开通状态
    public static final String YD0002 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0002";
    public static final String YD0001 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0001";
    public static final String YD0003 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0003";
    public static final String YD0004 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0004";
    public static final String YD0005 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0005";
    public static final String YD0006 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0006";
    public static final String YD0007 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0007";
    public static final String YD0008 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0008";
    public static final String YD0009 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0009";
    public static final String YD0010 = (isTestEnv() ? "/test" : "") + "/huzh_credit/sdk/yd0010";

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
