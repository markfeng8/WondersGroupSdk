package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:
 */
public interface OnHospitalListListener {

    void onSuccess(HospitalEntity body);

    void onFailed(String errCodeDes);
}
