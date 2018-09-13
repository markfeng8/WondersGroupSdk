package com.wondersgroup.android.jkcs_sdk.cons;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:组织机构先关配置
 */
public class OrgConfig {

    public static final String KEY = "71F62B4CA43810A3E055000000000001"; // 生成签名时的key
    public static final String ORG_CODE = "jkhzsdk"; // 机构编码
    public static final String TRAN_CHL01 = "01"; // 交易渠道
    public static final String IDEN_CLASS1 = "1"; // 1: 注册
    public static final String IDEN_CLASS2 = "2"; // 2: 修改手机号
    public static final String IDEN_CLASS3 = "3"; // 3: 解约
    public static final String IDEN_CLASS9 = "9"; // 9: 其他
    public static final String FEE_STATE00 = "00"; // 全部未结算
    public static final String FEE_STATE01 = "01"; // 医保已结算、自费未结（作保留查询）
    public static final String FEE_STATE02 = "02"; // 02 全部已结算（仅当天查询，作保留）
    public static final String ORDER_START_DATE = "2018-01-01";
    public static final String SIGN_ORG_NAME = "签约机构名称";
    public static final String HOME_ADDRESS = "ShangHai";
    public static final String HEALTH_CARE_STATUS = "1";
}
