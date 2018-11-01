/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/8/24 :)
 * Function:自费主页 RecyclerView 头部数据封装的 Bean 实体类
 */
public class SelfPayHeaderBean {

    private String name;
    private String icNum;
    private String hospitalName;
    private String orgCode;
    private String orgName;

    public SelfPayHeaderBean() {
    }

    public SelfPayHeaderBean(String name, String icNum, String hospitalName, String orgCode, String orgName) {
        this.name = name;
        this.icNum = icNum;
        this.hospitalName = hospitalName;
        this.orgCode = orgCode;
        this.orgName = orgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcNum() {
        return icNum;
    }

    public void setIcNum(String icNum) {
        this.icNum = icNum;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    @Override
    public String toString() {
        return "SelfPayHeaderBean{" +
                "name='" + name + '\'' +
                ", icNum='" + icNum + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
