package com.wondersgroup.android.jkcs_sdk.entity;

import java.util.List;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:
 */
public class FeeBillEntity extends BaseEntity {

    /**
     * fee_total : 539.15
     * details : [{"his_order_no":"CF20839351","his_order_time":"2018-08-22 11:37:25","ordername":"处方","fee_state":"00","fee_order":"123.15"},{"his_order_no":"YJ20093631","his_order_time":"2018-08-22 11:41:17","ordername":"处置","fee_state":"00","fee_order":"416"}]
     */

    private String fee_total;
    private List<DetailsBean> details;

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
         * fee_state : 00
         * fee_order : 123.15
         */

        private String his_order_no;
        private String his_order_time;
        private String ordername;
        private String fee_state;
        private String fee_order;

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

        public String getFee_state() {
            return fee_state;
        }

        public void setFee_state(String fee_state) {
            this.fee_state = fee_state;
        }

        public String getFee_order() {
            return fee_order;
        }

        public void setFee_order(String fee_order) {
            this.fee_order = fee_order;
        }
    }
}
