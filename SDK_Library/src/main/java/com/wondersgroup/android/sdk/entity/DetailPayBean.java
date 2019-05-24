package com.wondersgroup.android.sdk.entity;

/**
 * Created by x-sir on 2018/9/10 :)
 * Function:试结算发起完成后数据的 Bean 类
 */
public class DetailPayBean {

    private String totalPay;
    private String personalPay;
    private String yibaoPay;

    public DetailPayBean() {
    }

    public DetailPayBean(String totalPay, String personalPay, String yibaoPay) {
        this.totalPay = totalPay;
        this.personalPay = personalPay;
        this.yibaoPay = yibaoPay;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public String getPersonalPay() {
        return personalPay;
    }

    public void setPersonalPay(String personalPay) {
        this.personalPay = personalPay;
    }

    public String getYibaoPay() {
        return yibaoPay;
    }

    public void setYibaoPay(String yibaoPay) {
        this.yibaoPay = yibaoPay;
    }

    @Override
    public String toString() {
        return "DetailPayBean{" +
                "totalPay='" + totalPay + '\'' +
                ", personalPay='" + personalPay + '\'' +
                ", yibaoPay='" + yibaoPay + '\'' +
                '}';
    }
}
