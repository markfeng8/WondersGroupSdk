package com.wondersgroup.android.jkcs_sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:试结算、正式结算响应的 Bean
 */
public class SettleEntity extends BaseEntity {

    private String fee_total;
    private String fee_cash_total;
    private String fee_yb_total;
    @SerializedName("pay_state")
    private String payState;
    private List<DetailsBean> details;

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getFee_total() {
        return fee_total;
    }

    public void setFee_total(String fee_total) {
        this.fee_total = fee_total;
    }

    public String getFee_cash_total() {
        return fee_cash_total;
    }

    public void setFee_cash_total(String fee_cash_total) {
        this.fee_cash_total = fee_cash_total;
    }

    public String getFee_yb_total() {
        return fee_yb_total;
    }

    public void setFee_yb_total(String fee_yb_total) {
        this.fee_yb_total = fee_yb_total;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        private String his_order_no;
        private String order_name;
        private String fee_order;

        public String getHis_order_no() {
            return his_order_no;
        }

        public void setHis_order_no(String his_order_no) {
            this.his_order_no = his_order_no;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public String getFee_order() {
            return fee_order;
        }

        public void setFee_order(String fee_order) {
            this.fee_order = fee_order;
        }
    }
}
