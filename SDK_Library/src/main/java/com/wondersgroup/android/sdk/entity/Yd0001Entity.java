/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by x-sir on 2019/4/15 :)
 * Function:
 */
public class Yd0001Entity extends BaseEntity {

    @SerializedName("mobile_pay_status")
    private String mobilePayStatus;
    @SerializedName("sdk_status")
    private String eleCardStatus;
    @SerializedName("signno")
    private String signNo;

    public String getMobilePayStatus() {
        return mobilePayStatus;
    }

    public void setMobilePayStatus(String mobilePayStatus) {
        this.mobilePayStatus = mobilePayStatus;
    }

    public String getEleCardStatus() {
        return eleCardStatus;
    }

    public void setEleCardStatus(String eleCardStatus) {
        this.eleCardStatus = eleCardStatus;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }
}
