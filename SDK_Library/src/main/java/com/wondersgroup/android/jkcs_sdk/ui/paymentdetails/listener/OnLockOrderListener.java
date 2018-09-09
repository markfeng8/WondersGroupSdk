package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.listener;

import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:锁单的监听器
 */
public interface OnLockOrderListener {

    void onSuccess(LockOrderEntity entity);

    void onFailed();
}
