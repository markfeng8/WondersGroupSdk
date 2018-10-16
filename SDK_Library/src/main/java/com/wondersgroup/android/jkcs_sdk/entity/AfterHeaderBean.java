package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/8/24 :)
 * Function:医后付主页 RecyclerView 头部数据封装的 Bean 实体类
 */
public class AfterHeaderBean {

    private String name;
    private String socialNum;
    private String hospitalName;
    private String paymentStatus; // 医后付付费状态(已签约)
    private String signingStatus; // 医后付签约状态
    private String mobPayStatus;
    private String feeTotal;
    private String orgCode;
    private String orgName;
    private String feeState;
    private String feeTotals;
    private String feeCashTotal;
    private String feeYbTotal;
    private String feeOrgName;

    public AfterHeaderBean() {
    }

    public AfterHeaderBean(String name, String socialNum, String hospitalName, String paymentStatus,
                           String signingStatus, String mobPayStatus, String feeTotal, String orgCode,
                           String orgName, String feeState, String feeTotals, String feeCashTotal,
                           String feeYbTotal, String feeOrgName) {
        this.name = name;
        this.socialNum = socialNum;
        this.hospitalName = hospitalName;
        this.paymentStatus = paymentStatus;
        this.signingStatus = signingStatus;
        this.mobPayStatus = mobPayStatus;
        this.feeTotal = feeTotal;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.feeState = feeState;
        this.feeTotals = feeTotals;
        this.feeCashTotal = feeCashTotal;
        this.feeYbTotal = feeYbTotal;
        this.feeOrgName = feeOrgName;
    }

    public String getFeeOrgName() {
        return feeOrgName;
    }

    public void setFeeOrgName(String feeOrgName) {
        this.feeOrgName = feeOrgName;
    }

    public String getFeeState() {
        return feeState;
    }

    public void setFeeState(String feeState) {
        this.feeState = feeState;
    }

    public String getFeeTotals() {
        return feeTotals;
    }

    public void setFeeTotals(String feeTotals) {
        this.feeTotals = feeTotals;
    }

    public String getFeeCashTotal() {
        return feeCashTotal;
    }

    public void setFeeCashTotal(String feeCashTotal) {
        this.feeCashTotal = feeCashTotal;
    }

    public String getFeeYbTotal() {
        return feeYbTotal;
    }

    public void setFeeYbTotal(String feeYbTotal) {
        this.feeYbTotal = feeYbTotal;
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

    public String getMobPayStatus() {
        return mobPayStatus;
    }

    public void setMobPayStatus(String mobPayStatus) {
        this.mobPayStatus = mobPayStatus;
    }

    @Override
    public String toString() {
        return "AfterHeaderBean{" +
                "name='" + name + '\'' +
                ", socialNum='" + socialNum + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", signingStatus='" + signingStatus + '\'' +
                ", mobPayStatus='" + mobPayStatus + '\'' +
                ", feeTotal='" + feeTotal + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", orgName='" + orgName + '\'' +
                ", feeState='" + feeState + '\'' +
                ", feeTotals='" + feeTotals + '\'' +
                ", feeCashTotal='" + feeCashTotal + '\'' +
                ", feeYbTotal='" + feeYbTotal + '\'' +
                ", feeOrgName='" + feeOrgName + '\'' +
                '}';
    }
}
