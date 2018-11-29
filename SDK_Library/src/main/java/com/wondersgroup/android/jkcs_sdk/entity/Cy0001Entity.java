/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.entity;

import java.util.List;

/**
 * Created by x-sir on 2018/11/29 :)
 * Function:
 */
public class Cy0001Entity extends BaseEntity {

    private List<DetailsBean> details;

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        private String org_code;
        private String in_state;
        private String name;
        private String card_type;
        private String card_no;
        private String id_type;
        private String id_no;
        private String jzlsh;
        private String cwh;
        private String rysj;
        private String cysj;
        private String ksmc;
        private String yjkze;
        private String fee_total;
        private String fee_cash_total;
        private String fee_yb_total;

        public String getOrg_code() {
            return org_code;
        }

        public void setOrg_code(String org_code) {
            this.org_code = org_code;
        }

        public String getIn_state() {
            return in_state;
        }

        public void setIn_state(String in_state) {
            this.in_state = in_state;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCard_type() {
            return card_type;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }

        public String getCard_no() {
            return card_no;
        }

        public void setCard_no(String card_no) {
            this.card_no = card_no;
        }

        public String getId_type() {
            return id_type;
        }

        public void setId_type(String id_type) {
            this.id_type = id_type;
        }

        public String getId_no() {
            return id_no;
        }

        public void setId_no(String id_no) {
            this.id_no = id_no;
        }

        public String getJzlsh() {
            return jzlsh;
        }

        public void setJzlsh(String jzlsh) {
            this.jzlsh = jzlsh;
        }

        public String getCwh() {
            return cwh;
        }

        public void setCwh(String cwh) {
            this.cwh = cwh;
        }

        public String getRysj() {
            return rysj;
        }

        public void setRysj(String rysj) {
            this.rysj = rysj;
        }

        public String getCysj() {
            return cysj;
        }

        public void setCysj(String cysj) {
            this.cysj = cysj;
        }

        public String getKsmc() {
            return ksmc;
        }

        public void setKsmc(String ksmc) {
            this.ksmc = ksmc;
        }

        public String getYjkze() {
            return yjkze;
        }

        public void setYjkze(String yjkze) {
            this.yjkze = yjkze;
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
    }
}