package com.epsoft.hzauthsdk.bean;

/**
 * Created by x-sir on 2018/9/16 :)
 * Function:弹出键盘时的Bean
 */
public class KeyboardBean {

    /**
     * expires : 2018-10-11 09:45:45
     * token : 330599544B4E0254D0B974E600000000
     * code : 0
     * msg :
     */

    private String expires;
    private String token;
    private String code;
    private String msg;

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
