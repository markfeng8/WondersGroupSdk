package com.wondersgroup.android.jkcs_sdk.entity;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:
 */
public class PayParamEntity extends BaseEntity {

    private String version;
    private String appid;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
