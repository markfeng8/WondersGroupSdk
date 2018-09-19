package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:查询缴费详情信息的监听器（医后付首页 & 缴费详情页 & 缴费记录页）
 */
public interface OnFeeDetailListener {

    void onSuccess(FeeBillEntity entity);

    void onFailed(String errCodeDes);
}
