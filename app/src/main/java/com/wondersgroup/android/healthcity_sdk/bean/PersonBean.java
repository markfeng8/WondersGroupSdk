package com.wondersgroup.android.healthcity_sdk.bean;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class PersonBean {

    private String name;
    private String phone;
    private String idType;
    private String idNum;
    private String cardType;
    private String cardNum;
    private String address;

    public PersonBean() {
    }

    public PersonBean(String name, String phone, String idType, String idNum, String cardType,
                      String cardNum, String address) {
        this.name = name;
        this.phone = phone;
        this.idType = idType;
        this.idNum = idNum;
        this.cardType = cardType;
        this.cardNum = cardNum;
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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
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
                ", idType='" + idType + '\'' +
                ", idNum='" + idNum + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
