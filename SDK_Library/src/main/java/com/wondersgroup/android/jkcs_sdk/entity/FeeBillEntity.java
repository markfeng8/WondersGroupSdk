package com.wondersgroup.android.jkcs_sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:缴费详情、缴费记录展开 Item 响应的 Bean
 */
public class FeeBillEntity extends BaseEntity {

    /**
     * fee_total : 539.15
     * details : [{"his_order_no":"CF20839351","his_order_time":"2018-08-22 11:37:25","ordername":"处方","fee_state":"00","fee_order":"123.15"},{"his_order_no":"YJ20093631","his_order_time":"2018-08-22 11:41:17","ordername":"处置","fee_state":"00","fee_order":"416"}]
     */

    @SerializedName("fee_total")
    private String feeTotal;
    @SerializedName("pay_state")
    private String payState;
    private List<FeeBillDetailsBean> details;

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

    public List<FeeBillDetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<FeeBillDetailsBean> details) {
        this.details = details;
    }
}
