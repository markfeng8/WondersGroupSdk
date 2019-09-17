package com.wondersgroup.android.sdk.entity;

import java.io.Serializable;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:短信验证码实体类
 */
public class SmsEntity extends BaseEntity implements Serializable {

    /**
     * iden_code : 277334
     */

    private String iden_code;

    public String getIden_code() {
        return iden_code;
    }

    public void setIden_code(String iden_code) {
        this.iden_code = iden_code;
    }
}
