package com.wondersgroup.android.sdk.entity;

import java.io.Serializable;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class LockOrderEntity extends BaseEntity implements Serializable {

    private String payplat_tradno;
    private String lock_start_time;

    public String getPayplat_tradno() {
        return payplat_tradno;
    }

    public void setPayplat_tradno(String payplat_tradno) {
        this.payplat_tradno = payplat_tradno;
    }

    public String getLock_start_time() {
        return lock_start_time;
    }

    public void setLock_start_time(String lock_start_time) {
        this.lock_start_time = lock_start_time;
    }
}
