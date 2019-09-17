/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by x-sir on 2018/12/6 :)
 * Function:
 */
public class Cy0007Entity extends BaseEntity implements Serializable {

    @SerializedName("pay_state")
    private String payState;
    @SerializedName("fee_total")
    private String feeTotal;
    @SerializedName("fee_cash_total")
    private String feeCashTotal;
    @SerializedName("fee_yb_total")
    private String feeYbTotal;
    @SerializedName("his_tradno")
    private String hisTradeNo;

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

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

    public String getHisTradeNo() {
        return hisTradeNo;
    }

    public void setHisTradeNo(String hisTradeNo) {
        this.hisTradeNo = hisTradeNo;
    }
}
