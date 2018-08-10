package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener;

import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public interface OnAfterPayStateListener {

    void onSuccess(AfterPayStateEntity entity);

    void onFailed();
}
