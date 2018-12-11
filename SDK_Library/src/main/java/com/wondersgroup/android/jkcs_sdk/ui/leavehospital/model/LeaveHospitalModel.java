/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0006RequestListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.Cy0006Service;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
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
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class LeaveHospitalModel implements LeaveHospitalContract.IModel {

    private static final String TAG = "LeaveHospitalModel";

    public LeaveHospitalModel() {
    }

    @Override
    public void requestCy0006(String orgCode, String token, OnCy0006RequestListener listener) {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String jzlsh = SpUtil.getInstance().getString(SpKey.JZLSH, "");

        HashMap<String, String> param = new HashMap<>();
        param.put(MapKey.SID, ProduceUtil.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_CY0006);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        param.put(MapKey.ORG_CODE, orgCode);
        param.put(MapKey.JZLSH, jzlsh);
        param.put(MapKey.NAME, name);
        param.put(MapKey.ID_TYPE, idType);
        param.put(MapKey.ID_NO, idNum);
        param.put(MapKey.TOKEN, token);
        param.put(MapKey.ADVICE_DATE_TIME, TimeUtil.getCurrentDateTime());
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        RetrofitHelper
                .getInstance()
                .createService(Cy0006Service.class)
                .cy0006(RequestUrl.CY0006, param)
                .enqueue(new Callback<Cy0006Entity>() {
                    @Override
                    public void onResponse(Call<Cy0006Entity> call, Response<Cy0006Entity> response) {
                        int code = response.code();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && successful) {

                            String json = "{\n" +
                                    "  \"result_code\" : \"SUCCESS\",\n" +
                                    "  \"payplat_tradno\" : \"1276951cf68845ad8f8b97c29c333bb9\",\n" +
                                    "  \"return_code\" : \"SUCCESS\",\n" +
                                    "  \"pay_start_time\" : \"2018-11-10 14:51:07\",\n" +
                                    "  \"return_msg\" : \"响应成功\",\n" +
                                    "  \"fee_total\" : \"1021.00\",\n" +
                                    "  \"fee_cash_total\" : \"860.20\",\n" +
                                    "  \"yjkze\" : \"860.00\",\n" +
                                    "  \"xxjje\" : \"0.20\",\n" +
                                    "  \"fee_yb_total\" : \"160.80\"\n" +
                                    "}";

                            Cy0006Entity body = new Gson().fromJson(json, Cy0006Entity.class);

                            //Cy0006Entity body = response.body();
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
                        } else {
                            LogUtil.e("服务器异常！");
                        }
                    }

                    @Override
                    public void onFailure(Call<Cy0006Entity> call, Throwable t) {
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
