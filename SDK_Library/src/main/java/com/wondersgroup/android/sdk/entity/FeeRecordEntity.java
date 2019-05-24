package com.wondersgroup.android.sdk.entity;

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

        /**
         * "org_name": "湖州市中心医院",
         * "fee_total": "21.80",
         * "fee_cash_total": "10.00",
         * "fee_yb_total": "11.80",
         * "payplat_tradno": "b3d5cf9fea624dd4a06c54cd6c7bd16d",
         * "shop_order_time": "2018-10-10 14:42:10",
         * "fee_state": "01",
         * "org_code": "47117170333050211A1001"
         */

        private String org_name;
        private String org_code;
        private String fee_total;
        private String fee_cash_total;
        private String fee_yb_total;
        private String payplat_tradno;
        private String shop_order_time;
        private String fee_state;

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
