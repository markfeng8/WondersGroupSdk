/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/11/15 :)
 * Function:用户信息建造者类
 */
public class UserBuilder {

    private String name;
    private String phone;
    private String idType;
    private String idNum;
    private String cardType;
    private String cardNum;
    private String address;

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder setIdType(String idType) {
        this.idType = idType;
        return this;
    }

    public UserBuilder setIdNum(String idNum) {
        this.idNum = idNum;
        return this;
    }

    public UserBuilder setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public UserBuilder setCardNum(String cardNum) {
        this.cardNum = cardNum;
        return this;
    }

    public UserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdType() {
        return idType;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getAddress() {
        return address;
    }
}
