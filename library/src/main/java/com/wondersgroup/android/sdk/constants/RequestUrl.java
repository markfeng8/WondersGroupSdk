package com.wondersgroup.android.sdk.constants;

import com.wondersgroup.android.sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:request url constants.
 */
public class RequestUrl {

    private static final String TAG = "RequestUrl";

    //测试环境
//    public static final String HOST = "http://61.153.209.242:8083";
    //正式环境
    public static final String HOST = "http://61.153.209.242:8085";

    /**
     * 门诊部分接口
     */
    public static final String XY0001 = (isTestEnv() ? "/test" : "") + "/yhf/ct/xy0001";
    public static final String XY0002 = (isTestEnv() ? "/test" : "") + "/yhf/ct/xy0002";
    public static final String XY0003 = (isTestEnv() ? "/test" : "") + "/yhf/ct/xy0003";
    public static final String XY0004 = (isTestEnv() ? "/test" : "") + "/yhf/ct/xy0004";
    public static final String XY0005 = (isTestEnv() ? "/test" : "") + "/yhf/ct/xy0005";
    public static final String XY0006 = (isTestEnv() ? "/test" : "") + "/yhf/ct/xy0006";
    public static final String XY0008 = (isTestEnv() ? "/test" : "") + "/yhf/ct/xy0008";
    public static final String SIGN = (isTestEnv() ? "/test" : "") + "/yhf/ct/sign";

    /**
     * 移动支付状态查询
     */
    public static final String YD0001 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0001";
    public static final String YD0002 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0002";
    public static final String YD0003 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0003";
    public static final String YD0004 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0004";
    public static final String YD0005 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0005";
    public static final String YD0006 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0006";
    public static final String YD0007 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0007";
    public static final String YD0008 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0008";
    public static final String YD0009 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0009";
    public static final String YD0010 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/yd0010";

    /**
     * 住院部分接口
     */
    public static final String CY0001 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/cy0001";
    public static final String CY0002 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/cy0002";
    public static final String CY0003 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/cy0003";
    public static final String CY0004 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/cy0004";
    public static final String CY0005 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/cy0005";
    public static final String CY0006 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/cy0006";
    public static final String CY0007 = (isTestEnv() ? "/test" : "") + "/yhf/sdk/cy0007";

    /**
     * 统一支付回调地址
     */
    public static final String SDK_TO_BILL = (isTestEnv() ? "test/" : "") + "yhf/sdk/sdktobill";

    /**
     * 恩普参数签名接口
     */
    public static final String EPSOFT_SIGN_API = (isTestEnv() ? "http://115.236.191.153:9090/" : "http://dzsbk.zjhrss.gov.cn:8080/") + "sdk/csb/signTest";

    /**
     * 市平台获取试结算、正式结算 Token 的接口
     */
    //public static final String CHECK_SIGN_API = (isTestEnv() ? "http://61.153.183.132:9025" : "http://61.153.183.130:8026") + "/access/api/gateway";
    public static final String CHECK_SIGN_API = HOST + "/yhf/ct/getToken";
//    public static final String CHECK_SIGN_API = "http://172.16.161.81:9090/access/api/gateway";

    private static boolean isTestEnv() {
        boolean isTestEnv = false;

//        String env = SpUtil.getInstance().getString(SpKey.SDK_ENV, "");
//        if (!TextUtils.isEmpty(env) && "test".equals(env)) {
//            isTestEnv = true;
//        }
        LogUtil.i(TAG, "WondersGroup sdk 当前的环境是===" + (isTestEnv ? "[测试环境]" : "[正式环境]"));
        return isTestEnv;
    }

}
