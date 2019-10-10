/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by x-sir on 2019/4/26 :)
 * Function:
 */
public class EleCardEntity implements Serializable {

    /**
     * 签发类回执
     */
    private String actionType;
    /**
     * 独立服务回执
     */
    private String sceneType;
    /**
     * 发卡地区行政区划代码(二级代码)
     */
    private String aab301;
    /**
     * 签发号
     */
    private String signNo;
    /**
     * 操作验证串
     */
    private String busiSeq;
    /**
     * 签发业务流水号
     */
    private String signSeq;
    /**
     * 签发等级
     */
    private String signLevel;
    /**
     * 访问地址
     */
    @SerializedName("Url")
    private String url;
    /**
     * 有效期，格式为：YYYYMMDD
     */
    private String validate;
    /**
     * 发卡日期，格式为：YYYYMMDD
     */
    private String signDate;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 人员id
     */
    @SerializedName("userID")
    private String userId;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public String getAab301() {
        return aab301;
    }

    public void setAab301(String aab301) {
        this.aab301 = aab301;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getBusiSeq() {
        return busiSeq;
    }

    public void setBusiSeq(String busiSeq) {
        this.busiSeq = busiSeq;
    }

    public String getSignSeq() {
        return signSeq;
    }

    public void setSignSeq(String signSeq) {
        this.signSeq = signSeq;
    }

    public String getSignLevel() {
        return signLevel;
    }

    public void setSignLevel(String signLevel) {
        this.signLevel = signLevel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
