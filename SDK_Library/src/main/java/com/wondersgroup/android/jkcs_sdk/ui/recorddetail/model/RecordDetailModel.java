/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.recorddetail.model;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.constants.MapKey;
import com.wondersgroup.android.jkcs_sdk.constants.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.constants.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.constants.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeBillService;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.contract.RecordDetailContract;
import com.wondersgroup.android.jkcs_sdk.utils.DateUtils;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.RandomUtils;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:
 */
public class RecordDetailModel implements RecordDetailContract.IModel {

    private static final String TAG = "RecordDetailModel";

    public RecordDetailModel() {
    }

    @Override
    public void requestYd0009(String tradeNo, HttpRequestCallback<FeeBillEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0009);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.PAY_PLAT_TRADE_NO, tradeNo);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(FeeBillService.class)
                .getBillInfo(RequestUrl.YD0009, map)
                .enqueue(new Callback<FeeBillEntity>() {
                    @Override
                    public void onResponse(Call<FeeBillEntity> call, Response<FeeBillEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            FeeBillEntity body = response.body();
                            if (body != null) {
                                String returnCode = body.getReturn_code();
                                String resultCode = body.getResult_code();
                                if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                    if (callback != null) {
                                        callback.onSuccess(body);
                                    }
                                } else {
                                    String errCodeDes = body.getErr_code_des();
                                    if (!TextUtils.isEmpty(errCodeDes)) {
                                        if (callback != null) {
                                            callback.onFailed(errCodeDes);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (callback != null) {
                                callback.onFailed("服务器异常！");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FeeBillEntity> call, Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            if (callback != null) {
                                callback.onFailed(error);
                            }
                        }
                    }
                });
    }
}
