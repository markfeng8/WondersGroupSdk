package com.wondersgroup.android.sdk.constants;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:组织机构相关配置
 */
public class OrgConfig {

    /**
     * 生成签名时的 key
     */
    public static final String KEY = "123456789";
    /**
     * 机构编码
     */
    public static final String ORG_CODE = "zsyhfsdk";
    /**
     * 交易渠道
     */
    public static final String TRAN_CHL01 = "01";
    /**
     * 1: 注册
     */
    public static final String IDEN_CLASS1 = "1";
    /**
     * 2: 修改手机号
     */
    public static final String IDEN_CLASS2 = "2";
    /**
     * 3: 解约
     */
    public static final String IDEN_CLASS3 = "3";
    /**
     * 9: 其他
     */
    public static final String IDEN_CLASS9 = "9";
    /**
     * 全部未结算
     */
    public static final String FEE_STATE00 = "00";
    /**
     * 医保已结算、自费未结（作保留查询）
     */
    public static final String FEE_STATE01 = "01";
    /**
     * 开通状态
     */
    public static final String STATE_OPEN = "01";
    /**
     * 解约/未开通状态
     */
    public static final String STATE_CLOSE = "00";
    /**
     * 02 全部已结算（仅当天查询，作保留）
     */
    public static final String FEE_STATE02 = "02";
    public static final String ORDER_START_DATE = "2018-01-01";
    public static final String SIGN_ORG_NAME = "舟山医后付SDK";
    public static final String HOME_ADDRESS = "ShangHai";
    public static final String HEALTH_CARE_STATUS = "1";
    /**
     * 全局 api 接口版本号
     */
    public static final String GLOBAL_API_VERSION = "V1.2";

    /**
     * 0在院（包含预出院）
     */
    public static final String IN_STATE0 = "0";
    /**
     * 已出院
     */
    public static final String IN_STATE1 = "1";
    /**
     * 全部
     */
    public static final String IN_STATE2 = "2";
    /**
     * 结算
     */
    public static final String SRJ = "SRJ";
    /**
     * 预结算
     */
    public static final String SRY = "SRY";
    /**
     * 退费
     */
    public static final String SRT = "SRT";
    /**
     * 业务系统编号(1001 代表医保系统)
     */
    public static final String YIBAO_SYS_ID = "1001";
    /**
     * 密码验证
     */
    public static final String SCENE_TYPE_PWD = "004";
    /**
     * 手机短信验证
     */
    public static final String SCENE_TYPE_SMS = "005";
    /**
     * 人脸识别验证
     */
    public static final String SCENE_TYPE_FACE = "008";
    /**
     * 电子社保卡身份校验接口
     */
    public static final String IDENTITY_CHECK = "com.esicard.identity.check";
    /**
     * 获取预结算 token 接口
     */
    public static final String CREATE_TOKEN = "com.esicard.token.create";

}
