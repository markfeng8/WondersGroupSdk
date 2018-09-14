package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:查询未缴清账单信息的监听器（医后付首页 & 缴费详情页）
 */
public interface OnUnclearedBillListener {

    void onSuccess(FeeBillEntity entity);

    void onFailed(String errCodeDes);
}
