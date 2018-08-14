package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:移动支付状态返回
 */
public class MobilePayEntity extends BaseEntity {

    private String mobile_pay_status;

    public String getMobile_pay_status() {
        return mobile_pay_status;
    }

    public void setMobile_pay_status(String mobile_pay_status) {
        this.mobile_pay_status = mobile_pay_status;
    }
}
