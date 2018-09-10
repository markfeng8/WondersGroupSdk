package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/9/10 :)
 * Function:缴费详情页面头部数据的Bean
 */
public class DetailHeadBean {

    private String name;
    private String socialNum;
    private String hospitalName;
    private String orderNum;

    public DetailHeadBean() {
    }

    public DetailHeadBean(String name, String socialNum, String hospitalName, String orderNum) {
        this.name = name;
        this.socialNum = socialNum;
        this.hospitalName = hospitalName;
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialNum() {
        return socialNum;
    }

    public void setSocialNum(String socialNum) {
        this.socialNum = socialNum;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "DetailHeadBean{" +
                "name='" + name + '\'' +
                ", socialNum='" + socialNum + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", orderNum='" + orderNum + '\'' +
                '}';
    }
}
