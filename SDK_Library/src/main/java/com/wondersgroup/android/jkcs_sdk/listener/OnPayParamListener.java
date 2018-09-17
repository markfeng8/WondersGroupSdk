package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;

/**
 * Created by x-sir on 2018/9/13 :)
 * Function:
 */
public interface OnPayParamListener {

    void onSuccess(PayParamEntity entity);

    void onFailed(String errCodeDes);
}
