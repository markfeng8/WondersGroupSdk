package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;

/**
 * Created by x-sir on 2018/9/13 :)
 * Function:
 */
public interface OnOrderDetailListener {

    void onSuccess(OrderDetailsEntity entity);

    void onFailed(String errCodeDes);
}
