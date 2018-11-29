package com.wondersgroup.android.jkcs_sdk.cons;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:Map collection's key name constants.
 */
public class MapKey {

    public static final String SID = "sid";
    public static final String SIGN = "sign";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ID_TYPE = "id_type"; // 证件类型 01:身份证
    public static final String ID_NO = "id_no"; // 证件号码
    public static final String CARD_NO = "card_no";
    public static final String CARD_TYPE = "card_type";
    public static final String TRAN_CHL = "tran_chl";
    public static final String TRAN_ORG = "tran_org";
    public static final String ORG_CODE = "org_code";// 医院代码
    public static final String TRAN_CODE = "tran_code";
    public static final String TIMESTAMP = "timestamp";
    public static final String IN_STATE = "in_state";
    /**
     * 调用状态 1 保存token 2 正式结算
     */
    public static final String TO_STATE = "to_state";
    public static final String START_DATE = "startdate";
    public static final String END_DATE = "enddate";
    public static final String PAGE_NUMBER = "pagenumber";
    public static final String PAGE_SIZE = "pagesize";
    public static final String FEE_TOTAL = "fee_total";
    /**
     * 00 全部未结算 01 医保已结算、自费未结（作保留查询）02 全部已结算（仅当天查询，作保留）
     */
    public static final String FEE_STATE = "fee_state";
    public static final String TOTAL_COUNT = "total_count";
    public static final String TOKEN = "token";
    public static final String ADVICE_DATE_TIME = "advice_datetime";
    public static final String DETAILS = "details";
    public static final String HIS_ORDER_NO = "his_order_no";
    public static final String HIS_ORDER_TIME = "his_order_time";
    public static final String FEE_ORDER = "fee_order";
    public static final String ORDER_NO = "order_no";
    public static final String ORDER_NAME = "order_name";
    // 通知类别
    public static final String IDEN_CLASS = "iden_class";
    // 验证码
    public static final String IDEN_CODE = "iden_code";
    public static final String REG_ORG_CODE = "reg_org_code";
    public static final String REG_ORG_NAME = "reg_org_name";
    public static final String HOME_ADDRESS = "home_address";
    public static final String MOBILE_PAY_TIME = "mobile_pay_time";
    public static final String PAY_PLAT_TRADE_NO = "payplat_tradno";
    public static final String MOBILE_PAY_STATUS = "mobile_pay_status";
    public static final String HEALTH_CARE_STATUS = "health_care_status"; // 医保卡状态
}
