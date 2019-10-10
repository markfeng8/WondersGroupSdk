package com.wondersgroup.android.sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:响应体对应实体类的基类
 */
public class BaseEntity<T> implements Serializable {

    @SerializedName("return_code")
    private String returnCode = "";
    @SerializedName("return_msg")
    private String returnMsg = "";
    @SerializedName("result_code")
    private String resultCode = "";
    @SerializedName("err_code")
    private String errCode = "";
    @SerializedName("err_code_des")
    private String errCodeDes = "";
    private T data;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errCodeDes='" + errCodeDes + '\'' +
                ", data=" + data +
                '}';
    }
}
