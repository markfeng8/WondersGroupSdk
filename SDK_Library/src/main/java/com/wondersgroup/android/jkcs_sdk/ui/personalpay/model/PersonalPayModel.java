package com.wondersgroup.android.jkcs_sdk.ui.personalpay.model;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.api.Converter;
import com.wondersgroup.android.jkcs_sdk.net.service.SettleService;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.contract.PersonalPayContract;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.ProduceUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:
 */
public class PersonalPayModel implements PersonalPayContract.IModel {

    private static final String TAG = "PersonalPayModel";

    public PersonalPayModel() {
    }

    @Override
    public void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener) {
        String adviceDateTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0007);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.TOKEN, token);
        map.put(MapKey.ADVICE_DATE_TIME, adviceDateTime);
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        RetrofitHelper
                .getInstance()
                .createService(SettleService.class)
                .toSettle(RequestUrl.YD0007, Converter.toBody(map))
                .enqueue(new Callback<SettleEntity>() {
                    @Override
                    public void onResponse(Call<SettleEntity> call, Response<SettleEntity> response) {
                        SettleEntity body = response.body();
                        if (body != null) {
                            String returnCode = body.getReturn_code();
                            String resultCode = body.getResult_code();
                            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                if (listener != null) {
                                    listener.onSuccess(body);
                                }
                            } else {
                                String errCodeDes = body.getErr_code_des();
                                if (!TextUtils.isEmpty(errCodeDes)) {
                                    if (listener != null) {
                                        listener.onFailed(errCodeDes);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SettleEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (listener != null) {
                            listener.onFailed(error);
                        }
                    }
                });
    }
}
