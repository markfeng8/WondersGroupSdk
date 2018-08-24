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

    public AfterHeaderBean() {
    }

    public AfterHeaderBean(String name, String socialNum, String hospitalName, String paymentStatus,
                           String signingStatus, String mobPayStatus, String feeTotal) {
        this.name = name;
        this.socialNum = socialNum;
        this.hospitalName = hospitalName;
        this.paymentStatus = paymentStatus;
        this.signingStatus = signingStatus;
        this.mobPayStatus = mobPayStatus;
        this.feeTotal = feeTotal;
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
                '}';
    }
}
