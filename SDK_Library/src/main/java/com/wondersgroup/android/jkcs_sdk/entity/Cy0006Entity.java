/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by x-sir on 2018/12/6 :)
 * Function:
 */
public class Cy0006Entity extends BaseEntity {

    @SerializedName("fee_total")
    private String feeTotal;
    @SerializedName("fee_cash_total")
    private String feeCashTotal;
    @SerializedName("fee_yb_total")
    private String feeYbTotal;
    @SerializedName("yjkze")
    private String feePrepayTotal;
    @SerializedName("xxjje")
    private String feeNeedCashTotal;
    @SerializedName("payplat_tradno")
    private String payPlatTradeNo;
    @SerializedName("pay_start_time")
    private String payStartTime;

    public String getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(String feeTotal) {
        this.feeTotal = feeTotal;
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

    public String getFeePrepayTotal() {
        return feePrepayTotal;
    }

    public void setFeePrepayTotal(String feePrepayTotal) {
        this.feePrepayTotal = feePrepayTotal;
    }

    public String getFeeNeedCashTotal() {
        return feeNeedCashTotal;
    }

    public void setFeeNeedCashTotal(String feeNeedCashTotal) {
        this.feeNeedCashTotal = feeNeedCashTotal;
    }

    public String getPayPlatTradeNo() {
        return payPlatTradeNo;
    }

    public void setPayPlatTradeNo(String payPlatTradeNo) {
        this.payPlatTradeNo = payPlatTradeNo;
    }

    public String getPayStartTime() {
        return payStartTime;
    }

    public void setPayStartTime(String payStartTime) {
        this.payStartTime = payStartTime;
    }
}
