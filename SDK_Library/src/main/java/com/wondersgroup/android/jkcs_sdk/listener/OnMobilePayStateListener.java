package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public interface OnMobilePayStateListener {

    void onSuccess(MobilePayEntity entity);

    void onFailed();
}
