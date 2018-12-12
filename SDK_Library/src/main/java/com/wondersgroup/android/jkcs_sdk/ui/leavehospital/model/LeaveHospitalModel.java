/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.model;

import android.app.Activity;
import android.text.TextUtils;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0007Entity;
import com.wondersgroup.android.jkcs_sdk.entity.GetTokenBean;
import com.wondersgroup.android.jkcs_sdk.entity.KeyboardBean;
import com.wondersgroup.android.jkcs_sdk.entity.OpenStatusBean;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0006RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0007RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnYiBaoOpenStatusListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnYiBaoTokenListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.Cy0006Service;
import com.wondersgroup.android.jkcs_sdk.net.service.Cy0007Service;
import com.wondersgroup.android.jkcs_sdk.net.service.GetPayParamService;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.utils.ProduceUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.lang.ref.WeakReference;
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

    @Override
    public void requestCy0007(String orgCode, String toState, String token, String xxjje,
                              String payChl, OnCy0007RequestListener listener) {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String jzlsh = SpUtil.getInstance().getString(SpKey.JZLSH, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");
        String payStartTime = SpUtil.getInstance().getString(SpKey.PAY_START_TIME, "");

        HashMap<String, String> param = new HashMap<>();
        param.put(MapKey.SID, ProduceUtil.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_CY0007);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        param.put(MapKey.ORG_CODE, orgCode);
        param.put(MapKey.TO_STATE, toState);
        param.put(MapKey.JZLSH, jzlsh);
        param.put(MapKey.NAME, name);
        param.put(MapKey.ID_TYPE, idType);
        param.put(MapKey.ID_NO, idNum);
        param.put(MapKey.TOKEN, token);
        param.put(MapKey.ADVICE_DATE_TIME, TimeUtil.getCurrentDateTime());
        param.put(MapKey.XXJJE, xxjje);
        param.put(MapKey.PAY_PLAT_TRADE_NO, payPlatTradeNo);
        param.put(MapKey.PAY_TRAN_DATE_TIME, payStartTime);
        param.put(MapKey.PAYCHL, payChl);
        param.put(MapKey.PAY_CLIENT, "01"); // 01 代表 Android APP
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        RetrofitHelper
                .getInstance()
                .createService(Cy0007Service.class)
                .cy0007(RequestUrl.CY0007, param)
                .enqueue(new Callback<Cy0007Entity>() {
                    @Override
                    public void onResponse(Call<Cy0007Entity> call, Response<Cy0007Entity> response) {
                        int code = response.code();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && successful) {
                            Cy0007Entity body = response.body();
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
                    public void onFailure(Call<Cy0007Entity> call, Throwable t) {
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

    @Override
    public void getPayParam(String orgCode, OnPayParamListener listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0010);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(GetPayParamService.class)
                .getPayParams(RequestUrl.YD0010, map)
                .enqueue(new Callback<PayParamEntity>() {
                    @Override
                    public void onResponse(Call<PayParamEntity> call, Response<PayParamEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            PayParamEntity body = response.body();
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
                            if (listener != null) {
                                listener.onFailed("服务器异常！");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PayParamEntity> call, Throwable t) {
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

    @Override
    public void getYiBaoToken(WeakReference<Activity> weakReference, OnYiBaoTokenListener listener) {
        String yiBaoToken = SpUtil.getInstance().getString(SpKey.YIBAO_TOKEN, "");
        String tokenTime = SpUtil.getInstance().getString(SpKey.TOKEN_TIME, "");

        // 判断医保 token 和 保存的 token 是否在有效期内，如果在就使用之前的获取到的，如果没有那就获取
        if (!TextUtils.isEmpty(yiBaoToken) && !TextUtils.isEmpty(tokenTime) && !TimeUtil.isOver30min(tokenTime)) {
            if (listener != null) {
                listener.onResult(yiBaoToken);
            }

        } else {
            Activity activity = weakReference.get();
            if (activity == null) {
                return;
            }

            /*
             * 点击 "立即支付" 结算时，先弹出键盘获取 token
             * 如果是第一次需要弹出医保键盘获取 token，获取之后，如果在没退出页面，则此 token 30 min 内有效，
             * 如果退出页面，则需要再次弹出键盘获取 token
             */
            AuthCall.getToken(activity, MakeArgsFactory.getKeyboardArgs(),
                    result -> {
                        LogUtil.i(TAG, "result===" + result);
                        if (!TextUtils.isEmpty(result)) {
                            KeyboardBean keyboardBean = new Gson().fromJson(result, KeyboardBean.class);
                            if (keyboardBean != null) {
                                String code = keyboardBean.getCode();
                                if ("0".equals(code)) {
                                    /*
                                     * 获取 token 后，重新保存医保 token 和 token 时间
                                     */
                                    String token = keyboardBean.getToken();
                                    SpUtil.getInstance().save(SpKey.YIBAO_TOKEN, token);
                                    SpUtil.getInstance().save(SpKey.TOKEN_TIME, TimeUtil.getCurrentMillis());

                                    if (listener != null) {
                                        listener.onResult(token);
                                    }

                                } else {
                                    String msg = keyboardBean.getMsg();
                                    WToastUtil.show(String.valueOf(msg));
                                }
                            }
                        }
                    });
        }
    }

    @Override
    public void getTryToSettleToken(WeakReference<Activity> weakReference, OnYiBaoTokenListener listener) {
        Activity activity = weakReference.get();
        if (activity == null) {
            return;
        }

        AuthCall.getToken(activity, MakeArgsFactory.getTokenArgs(), result -> {
            LogUtil.i(TAG, "result===" + result);
            if (!TextUtils.isEmpty(result)) {
                GetTokenBean bean = new Gson().fromJson(result, GetTokenBean.class);
                String siCardCode = bean.getSiCardCode();
                LogUtil.i(TAG, "siCardCode===" + siCardCode);
                if (listener != null) {
                    listener.onResult(siCardCode);
                }
            }
        });
    }

    @Override
    public void queryYiBaoOpenStatus(WeakReference<Activity> weakReference, OnYiBaoOpenStatusListener listener) {
        Activity activity = weakReference.get();
        if (activity == null) {
            return;
        }

        AuthCall.queryOpenStatus(activity, MakeArgsFactory.getOpenStatusArgs(), result -> {
            if (!TextUtils.isEmpty(result)) {
                LogUtil.i(TAG, "result===" + result);
                OpenStatusBean statusBean = new Gson().fromJson(result, OpenStatusBean.class);
                int isYbPay = statusBean.getIsYbPay();

                if (listener != null) {
                    listener.onResult(isYbPay);
                }
            }
        });
    }
}
