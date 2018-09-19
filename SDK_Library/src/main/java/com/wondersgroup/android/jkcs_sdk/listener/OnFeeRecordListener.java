package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;

/**
 * Created by x-sir on 2018/9/13 :)
 * Function:缴费记录的监听器（已完成和未完成订单）
 */
public interface OnFeeRecordListener {

    void onSuccess(FeeRecordEntity entity);

    void onFailed(String errCodeDes);
}
