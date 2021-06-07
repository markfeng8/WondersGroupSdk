package com.wondersgroup.android.sdk.entity;

/**
 * code by markfeng
 * <p>
 * create on 2021-05-20 10:09
 */
public class WondersOutParams {

    // 0：获取渠道信息，渠道号
    // 1：申领社保卡
    // 2：支付验证
    // 3:万达SDK申领社保卡后，将省卡管返回的数据输出（包含签发号 ）
    private String type;
    private String socialSecurityNum;//社保卡号
    private String name;//姓名
    private String signNo;//签发号

    private String zjEsscSDKResult;//申领社保卡的时候，sdk返回result

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSocialSecurityNum() {
        return socialSecurityNum;
    }

    public void setSocialSecurityNum(String socialSecurityNum) {
        this.socialSecurityNum = socialSecurityNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getZjEsscSDKResult() {
        return zjEsscSDKResult;
    }

    public void setZjEsscSDKResult(String zjEsscSDKResult) {
        this.zjEsscSDKResult = zjEsscSDKResult;
    }
}
