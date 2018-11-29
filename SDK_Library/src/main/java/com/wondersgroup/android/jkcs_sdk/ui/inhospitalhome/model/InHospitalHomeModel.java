/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0001RequestListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.Cy0001Service;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.contract.InHospitalHomeContract;
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
 * Created by x-sir on 2018/11/7 :)
 * Function:住院页面的 Model
 */
public class InHospitalHomeModel implements InHospitalHomeContract.IModel {

    private static final String TAG = "InHospitalHomeModel";

    public InHospitalHomeModel() {

    }

    @Override
    public void requestCy0001(String orgCode, String inState, OnCy0001RequestListener listener) {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");

        HashMap<String, String> param = new HashMap<>();
        param.put(MapKey.SID, ProduceUtil.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_CY0001);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        param.put(MapKey.ORG_CODE, orgCode);
        param.put(MapKey.NAME, name);
        param.put(MapKey.ID_TYPE, idType);
        param.put(MapKey.ID_NO, idNum);
        param.put(MapKey.IN_STATE, inState);
        param.put(MapKey.START_DATE, "2018-01-01");
        param.put(MapKey.END_DATE, TimeUtil.getCurrentDate());
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        RetrofitHelper
                .getInstance()
                .createService(Cy0001Service.class)
                .cy0001(RequestUrl.CY0001, param)
                .enqueue(new Callback<Cy0001Entity>() {
                    @Override
                    public void onResponse(Call<Cy0001Entity> call, Response<Cy0001Entity> response) {
                        int code = response.code();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && successful) {

                            String json = "{\"return_msg\":\"响应成功\",\"result_code\":\"SUCCESS\",\"return_code\":\"SUCCESS\",\"details\":[{\"id_type\":\"01\",\"fee_total\":\"1374.44\",\"fee_cash_total\":\"0\",\"card_no\":\"0003\",\"org_code\":\"47117169033050211A5201\",\"jzlsh\":\"160407\",\"rysj\":\"2018-11-27 13:40:00\",\"ksmc\":\"十二病区\",\"fee_yb_total\":\"0\",\"yjkze\":\"0\",\"card_type\":\"2\",\"in_state\":\"00\",\"id_no\":\"330501198804146213\",\"cysj\":\"\",\"name\":\"测试\",\"cwh\":\"1258\"}]}";

                            Cy0001Entity entity = new Gson().fromJson(json, Cy0001Entity.class);
                            if (listener != null) {
                                listener.onSuccess(entity);
                            }
//                            Cy0001Entity body = response.body();
//                            if (body != null) {
//                                String returnCode = body.getReturn_code();
//                                String resultCode = body.getResult_code();
//                                if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
//                                    if (listener != null) {
//                                        listener.onSuccess(body);
//                                    }
//                                } else {
//                                    String errCodeDes = body.getErr_code_des();
//                                    if (!TextUtils.isEmpty(errCodeDes)) {
//                                        if (listener != null) {
//                                            listener.onFailed(errCodeDes);
//                                        }
//                                    }
//                                }
//                            }
                        } else {
                            LogUtil.e("服务器异常！");
                        }
                    }

                    @Override
                    public void onFailure(Call<Cy0001Entity> call, Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            if (listener != null) {
                                listener.onFailed(error);
                            }
                        }
                    }
                });
    }
}
