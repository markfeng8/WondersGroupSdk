package com.wondersgroup.android.healthcity_sdk.bean;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class PersonBean {

    private String name;
    private String phone;
    private String icNum;
    private String socialNum;
    private String address;

    public PersonBean() {
    }

    public PersonBean(String name, String phone, String icNum, String socialNum, String address) {
        this.name = name;
        this.phone = phone;
        this.icNum = icNum;
        this.socialNum = socialNum;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIcNum() {
        return icNum;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public String getSocialNum() {
        return socialNum;
    }

    public void setSocialNum(String socialNum) {
        this.socialNum = socialNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", icNum='" + icNum + '\'' +
                ", socialNum='" + socialNum + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
