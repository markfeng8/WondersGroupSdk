package com.wondersgroup.android.sdk.constants;

/**
 * Created by x-sir on 2018/8/2 :)
 * Function:Map collection's key name constants.
 */
public class MapKey {

    public static final String SID = "sid";
    public static final String SIGN = "sign";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    /**
     * 证件类型 01:身份证
     */
    public static final String ID_TYPE = "id_type";
    /**
     * 证件号码
     */
    public static final String ID_NO = "id_no";
    /**
     * 加密的证件号码
     */
    public static final String SIGN_ID_NO = "signIdNo";
    public static final String CARD_NO = "card_no";
    public static final String CARD_TYPE = "card_type";
    public static final String TRAN_CHL = "tran_chl";
    public static final String TRAN_ORG = "tran_org";
    /**
     * 医院代码
     */
    public static final String ORG_CODE = "org_code";
    public static final String TRAN_CODE = "tran_code";
    public static final String TIMESTAMP = "timestamp";
    public static final String IN_STATE = "in_state";
    /**
     * 需要签名的 json 串
     */
    public static final String JSON_STR = "json_str";
    /**
     * 接口版本号控制
     */
    public static final String VERSION = "version";
    /**
     * 医院列表接口类型字段
     */
    public static final String TYPE = "type";
    /**
     * 调用状态 1 保存token 2 正式结算
     */
    public static final String TO_STATE = "to_state";
    public static final String START_DATE = "startdate";
    public static final String END_DATE = "enddate";
    /**
     * 就诊流水号
     */
    public static final String JZLSH = "jzlsh";
    /**
     * 需现金金额
     */
    public static final String XXJJE = "xxjje";
    /**
     * 支付渠道
     */
    public static final String PAY_CHL = "pay_chl";
    /**
     * 第三方支付交易流水号
     */
    public static final String CHL_PAY_NO = "chl_pay_no";
    /**
     * 支付客户端
     */
    public static final String PAY_CLIENT = "pay_client";
    public static final String PAY_TRAN_DATE_TIME = "pay_trandatetime";
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
    /**
     * 通知类别
     */
    public static final String IDEN_CLASS = "iden_class";
    /**
     * 验证码
     */
    public static final String IDEN_CODE = "iden_code";
    public static final String REG_ORG_CODE = "reg_org_code";
    public static final String REG_ORG_NAME = "reg_org_name";
    public static final String HOME_ADDRESS = "home_address";
    public static final String MOBILE_PAY_TIME = "mobile_pay_time";
    public static final String PAY_PLAT_TRADE_NO = "payplat_tradno";
    public static final String MOBILE_PAY_STATUS = "mobile_pay_status";
    /**
     * 电子社保卡开通状态
     */
    public static final String ELE_CARD_STATUS = "sdk_status";
    /**
     * 签发号
     */
    public static final String SIGNATURE_NO = "signno";
    /**
     * 医保卡状态
     */
    public static final String HEALTH_CARE_STATUS = "health_care_status";
    /**
     * 渠道编号
     */
    public static final String CHANNEL_NO = "channelNo";
    /**
     * 社会保账号
     */
    public static final String AAC002 = "aac002";
    /**
     * 真实姓名
     */
    public static final String AAC003 = "aac003";
    /**
     * 手机号
     */
    public static final String AAC067 = "aac067";
    /**
     * 业务流水号
     */
    public static final String SIGN_SEQ = "signSeq";
    /**
     * 签发级别
     */
    public static final String SIGN_LEVEL = "signLevel";
    /**
     * 签发号
     */
    public static final String SIGN_NO = "signNo";
    /**
     * 合法日期
     */
    public static final String VALID_DATE = "validDate";
    /**
     * 发卡地区编号
     */
    public static final String AAB301 = "aab301";
    /**
     * 是否是独立服务（传1：为独立服务，独立服务下必传，不传：为申领流程）
     * 独立服务有：密码认证、短信认证、人脸认证、身份二维码
     */
    public static final String IS_INDEP = "isIndep";
    /**
     * 签发日期
     */
    public static final String SIGN_DATE = "signDate";
    /**
     * 监听器
     */
    public static final String LISTENTER = "listenter";
    /**
     * 监听器
     */
    public static final String ACTION_TYPE = "actionType";
    /**
     * 操作验证串
     */
    public static final String BUSI_SEQ = "busiSeq";
    /**
     * 历史数据标记
     */
    public static final String HISTORY_FLAG = "historyFlag";
    /**
     * 独立扫码登录服务
     */
    public static final String QR_CODE = "qrCode";
    /**
     * 业务类型
     */
    public static final String SER_TYPE = "serType";
    /**
     * 业务系统编号
     */
    public static final String SYS_ID = "sysId";
    /**
     * 社会保障号码
     */
    public static final String CERT_NO = "certNo";
    /**
     * 触发功能编号
     */
    public static final String SCENE_TYPE = "sceneType";
    /**
     * 接口代码
     */
    public static final String FUNCTION = "function";
    /**
     * 接口代码
     */
    public static final String SIGN_TYPE = "signType";
    /**
     * 接口代码
     */
    public static final String RANDOM_STR = "randomStr";
}
