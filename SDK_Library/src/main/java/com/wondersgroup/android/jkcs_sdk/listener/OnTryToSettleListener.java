package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.TryToSettleEntity;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:试结算的监听器
 */
public interface OnTryToSettleListener {

    void onSuccess(TryToSettleEntity body);

    void onFailed(String errCodeDes);
}
