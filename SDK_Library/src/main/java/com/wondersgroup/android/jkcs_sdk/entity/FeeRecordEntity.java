package com.wondersgroup.android.jkcs_sdk.entity;

import java.util.List;

/**
 * Created by x-sir on 2018/9/18 :)
 * Function:缴费记录的响应的 Bean
 */
public class FeeRecordEntity extends BaseEntity {

    private List<DetailsBean> details;

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        private String org_name;
        private String org_code;
        private String fee_total;
        private String payplat_tradno;
        private String shop_order_time;
        private String fee_state;

        public String getOrg_code() {
            return org_code;
        }

        public void setOrg_code(String org_code) {
            this.org_code = org_code;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public String getFee_total() {
            return fee_total;
        }

        public void setFee_total(String fee_total) {
            this.fee_total = fee_total;
        }

        public String getPayplat_tradno() {
            return payplat_tradno;
        }

        public void setPayplat_tradno(String payplat_tradno) {
            this.payplat_tradno = payplat_tradno;
        }

        public String getShop_order_time() {
            return shop_order_time;
        }

        public void setShop_order_time(String shop_order_time) {
            this.shop_order_time = shop_order_time;
        }

        public String getFee_state() {
            return fee_state;
        }

        public void setFee_state(String fee_state) {
            this.fee_state = fee_state;
        }
    }

}
