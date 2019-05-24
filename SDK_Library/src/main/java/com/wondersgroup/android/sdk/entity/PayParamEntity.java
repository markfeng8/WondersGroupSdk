package com.wondersgroup.android.sdk.entity;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:
 */
public class PayParamEntity extends BaseEntity {

    private String version;
    private String appid;
    private String submerno;
    private String apikey;

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

    public String getSubmerno() {
        return submerno;
    }

    public void setSubmerno(String submerno) {
        this.submerno = submerno;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
