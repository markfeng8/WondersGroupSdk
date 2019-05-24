package com.wondersgroup.android.sdk.entity;

import java.io.Serializable;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付状态的返回
 */
public class AfterPayStateEntity extends BaseEntity implements Serializable {

    /**
     * signing_status : 01
     * one_payment_status : 1
     * ct_date : 2018-08-03
     * fee_total :
     * org_name :
     * phone : 15957271207
     * org_code :
     * fee_date :
     */

    private String signing_status;
    private String one_payment_status;
    private String ct_date;
    private String fee_total;
    private String org_name;
    private String phone;
    private String org_code;
    private String fee_date;

    public String getSigning_status() {
        return signing_status;
    }

    public void setSigning_status(String signing_status) {
        this.signing_status = signing_status;
    }

    public String getOne_payment_status() {
        return one_payment_status;
    }

    public void setOne_payment_status(String one_payment_status) {
        this.one_payment_status = one_payment_status;
    }

    public String getCt_date() {
        return ct_date;
    }

    public void setCt_date(String ct_date) {
        this.ct_date = ct_date;
    }

    public String getFee_total() {
        return fee_total;
    }

    public void setFee_total(String fee_total) {
        this.fee_total = fee_total;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getFee_date() {
        return fee_date;
    }

    public void setFee_date(String fee_date) {
        this.fee_date = fee_date;
    }
}
