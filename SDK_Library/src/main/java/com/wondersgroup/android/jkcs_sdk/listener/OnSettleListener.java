package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:试结算、正式结算的监听器
 */
public interface OnSettleListener {

    void onSuccess(SettleEntity body);

    void onFailed(String errCodeDes);
}
