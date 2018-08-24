package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:
 */
public interface OnUnclearedBillListener {

    void onSuccess(FeeBillEntity entity);

    void onFailed();
}
