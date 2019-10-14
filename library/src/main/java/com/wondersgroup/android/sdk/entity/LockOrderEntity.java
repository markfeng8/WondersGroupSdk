package com.wondersgroup.android.sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:锁单成功的响应实体类
 */
public class LockOrderEntity extends BaseEntity implements Serializable {

    @SerializedName("payplat_tradno")
    private String payPlatTradNo;
    @SerializedName("lock_start_time")
    private String lockStartTime;

    public String getPayPlatTradNo() {
        return payPlatTradNo;
    }

    public void setPayPlatTradNo(String payPlatTradNo) {
        this.payPlatTradNo = payPlatTradNo;
    }

    public String getLockStartTime() {
        return lockStartTime;
    }

    public void setLockStartTime(String lockStartTime) {
        this.lockStartTime = lockStartTime;
    }
}
