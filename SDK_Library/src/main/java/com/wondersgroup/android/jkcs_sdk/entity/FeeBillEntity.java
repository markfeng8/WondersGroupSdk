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

    private String fee_total;
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

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * his_order_no : CF20839351
         * his_order_time : 2018-08-22 11:37:25
         * ordername : 处方
         * fee_order : 123.15
         */

        private String his_order_no;
        private String org_code;
        private String his_order_time;
        private String ordername;
        private String order_name;
        private String fee_order;

        public String getOrg_code() {
            return org_code;
        }

        public String getOrder_name() {
            return order_name;
        }

        public void setOrder_name(String order_name) {
            this.order_name = order_name;
        }

        public void setOrg_code(String org_code) {
            this.org_code = org_code;
        }

        public String getHis_order_no() {
            return his_order_no;
        }

        public void setHis_order_no(String his_order_no) {
            this.his_order_no = his_order_no;
        }

        public String getHis_order_time() {
            return his_order_time;
        }

        public void setHis_order_time(String his_order_time) {
            this.his_order_time = his_order_time;
        }

        public String getOrdername() {
            return ordername;
        }

        public void setOrdername(String ordername) {
            this.ordername = ordername;
        }

        public String getFee_order() {
            return fee_order;
        }

        public void setFee_order(String fee_order) {
            this.fee_order = fee_order;
        }
    }
}
