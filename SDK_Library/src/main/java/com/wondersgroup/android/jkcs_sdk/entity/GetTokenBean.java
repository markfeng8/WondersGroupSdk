package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/9/14 :)
 * Function:获取试结算时的Token
 */
public class GetTokenBean {

    /**
     * siCardCode : 330599D15600000501101653581D8DB2
     * bankNo : 6217256200009232399
     * bankCode : 0433059900
     * code : 0
     * msg :
     */

    private String siCardCode;
    private String bankNo;
    private String bankCode;
    private String code;
    private String msg;

    public String getSiCardCode() {
        return siCardCode;
    }

    public void setSiCardCode(String siCardCode) {
        this.siCardCode = siCardCode;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
