package com.epsoft.hzauthsdk.bean;

/**
 * Created by x-sir on 2018/9/11 :)
 * Function:移动医保状态的 Bean
 */
public class OpenStatusBean {

    /**
     * isHmd : 0
     * isYbPay : 0
     * authStatus : 0
     * code : 0
     * msg :
     */

    private int isHmd;
    private int isYbPay;
    private int authStatus;
    private String code;
    private String msg;

    public int getIsHmd() {
        return isHmd;
    }

    public void setIsHmd(int isHmd) {
        this.isHmd = isHmd;
    }

    public int getIsYbPay() {
        return isYbPay;
    }

    public void setIsYbPay(int isYbPay) {
        this.isYbPay = isYbPay;
    }

    public int getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(int authStatus) {
        this.authStatus = authStatus;
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
