package com.wondersgroup.android.sdk.entity;

import java.io.Serializable;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:
 */
public class PayParamEntity extends BaseEntity implements Serializable {

    private String version;
    private String appid;
    private String submerno;
    private String apikey;
    private String orgName;
    private String payPlatTradeNo;
    private String feeNeedCashTotal;
    private int paymentType;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSubmerno() {
        return submerno;
    }

    public void setSubmerno(String submerno) {
        this.submerno = submerno;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPayPlatTradeNo() {
        return payPlatTradeNo;
    }

    public void setPayPlatTradeNo(String payPlatTradeNo) {
        this.payPlatTradeNo = payPlatTradeNo;
    }

    public String getFeeNeedCashTotal() {
        return feeNeedCashTotal;
    }

    public void setFeeNeedCashTotal(String feeNeedCashTotal) {
        this.feeNeedCashTotal = feeNeedCashTotal;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public static PayParamEntity from(PayParamEntity body, String orgName, String payPlatTradeNo,
                                      int paymentType, String feeNeedCashTotal) {
        body.setOrgName(orgName);
        body.setPayPlatTradeNo(payPlatTradeNo);
        body.setPaymentType(paymentType);
        body.setFeeNeedCashTotal(feeNeedCashTotal);
        return body;
    }
}
