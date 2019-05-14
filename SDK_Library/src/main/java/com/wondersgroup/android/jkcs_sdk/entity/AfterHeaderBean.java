package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/8/24 :)
 * Function:医后付主页 RecyclerView 头部数据封装的 Bean 实体类
 */
public class AfterHeaderBean {

    private String name;
    private String socialNum;
    private String hospitalName;
    /**
     * 医后付付费状态(已签约)
     */
    private String paymentStatus;
    /**
     * 医后付签约状态
     */
    private String signingStatus;
    private String eleCardStatus;
    private String feeTotal;
    private String orgCode;
    private String orgName;

    public AfterHeaderBean() {
    }

    public AfterHeaderBean(String name, String socialNum, String hospitalName, String paymentStatus,
                           String signingStatus, String mobPayStatus, String feeTotal, String orgCode,
                           String orgName, String feeState, String feeTotals, String feeCashTotal,
                           String feeYbTotal, String feeOrgName, String feeOrgCode, int yd0008Size) {
        this.name = name;
        this.socialNum = socialNum;
        this.hospitalName = hospitalName;
        this.paymentStatus = paymentStatus;
        this.signingStatus = signingStatus;
        this.eleCardStatus = mobPayStatus;
        this.feeTotal = feeTotal;
        this.orgCode = orgCode;
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(String feeTotal) {
        this.feeTotal = feeTotal;
    }

    public String getSigningStatus() {
        return signingStatus;
    }

    public void setSigningStatus(String signingStatus) {
        this.signingStatus = signingStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialNum() {
        return socialNum;
    }

    public void setSocialNum(String socialNum) {
        this.socialNum = socialNum;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getEleCardStatus() {
        return eleCardStatus;
    }

    public void setEleCardStatus(String eleCardStatus) {
        this.eleCardStatus = eleCardStatus;
    }

    @Override
    public String toString() {
        return "AfterHeaderBean{" +
                "name='" + name + '\'' +
                ", socialNum='" + socialNum + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", signingStatus='" + signingStatus + '\'' +
                ", eleCardStatus='" + eleCardStatus + '\'' +
                ", feeTotal='" + feeTotal + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
