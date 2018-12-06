package com.wondersgroup.android.jkcs_sdk.cons;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:SharedPreference storage's key name.
 */
public class SpKey {

    public static final String NAME = "name";
    public static final String PHONE = "phone"; // 平台上的手机号
    public static final String PASS_PHONE = "passPhone"; // 传过来的手机号
    public static final String ID_TYPE = "idType"; // 证件类型 01：身份证
    public static final String ID_NUM = "idNum";
    public static final String SIGN_DATE = "SignDate";
    public static final String NOTICE_PHONE = "noticePhone"; // 通知手机号
    public static final String FEE_TOTAL = "feeTotal";
    public static final String CARD_TYPE = "cardType"; // 就诊卡类型 0：社保卡 2：自费卡
    public static final String CARD_NUM = "cardNum";
    public static final String YIBAO_ENABLE = "yiBaoEnable"; // 是否允许医保支付
    public static final String LOCK_START_TIME = "lockStartTime"; // 锁单时间
    public static final String HOME_ADDRESS = "homeAddress";
    public static final String PAY_PLAT_TRADE_NO = "payPlatTradeNo";
    public static final String SIGNING_STATUS = "signingStatus"; // 医后付签约状态
    public static final String PAYMENT_STATUS = "paymentStatus"; // 医后付付费状态
    public static final String MOB_PAY_STATUS = "mobPayStatus";  // 移动支付状态
    public static final String AFTER_PAY_OPEN_SUCCESS = "afterPayOpenSuccess";
    public static final String SDK_DEBUG = "sdkDebug"; // sdk 是否时调试模式
    public static final String SDK_ENV = "sdkEnv"; // sdk 环境
    public static final String YIBAO_TOKEN = "yiBaoToken"; // 医保 token
    public static final String TOKEN_TIME = "tokenTime";// 医保 token 时间
    public static final String JZLSH = "jzlsh";// 就诊流水号
}
