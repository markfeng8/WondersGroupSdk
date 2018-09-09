package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.model;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.SmsEntity;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.SendSmsService;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.listener.OnOpenAfterPayListener;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.listener.OnSmsSendListener;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.ProduceUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class AfterPayModel implements AfterPayContract.IModel {

    private static final String TAG = AfterPayModel.class.getSimpleName();

    public AfterPayModel() {
    }

    @Override
    public void sendSmsCode(String phone, final OnSmsSendListener listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0006);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.PHONE, phone);
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.IDEN_CLASS, OrgConfig.IDEN_CLASS1);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(SendSmsService.class)
                .sendSmsCode(RequestUrl.XY0006, map)
                .enqueue(new Callback<SmsEntity>() {
                    @Override
                    public void onResponse(Call<SmsEntity> call, Response<SmsEntity> response) {
                        SmsEntity body = response.body();
                        if (body != null) {
                            String returnCode = body.getReturn_code();
                            String resultCode = body.getResult_code();
                            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                String idenCode = body.getIden_code();
                                if (listener != null) {
                                    listener.onSuccess();
                                }
                            } else {
                                if (listener != null) {
                                    listener.onFailed();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SmsEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                });
    }

    @Override
    public void openAfterPay(HashMap<String, String> map, final OnOpenAfterPayListener listener) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0002);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.ID_TYPE, OrgConfig.ID_TYPE01);
        map.put(MapKey.CARD_TYPE, OrgConfig.CARD_TYPE0);
        map.put(MapKey.REG_ORG_NAME, OrgConfig.SIGN_ORG_NAME);
        map.put(MapKey.HOME_ADDRESS, OrgConfig.HOME_ADDRESS);
        map.put(MapKey.HEALTH_CARE_STATUS, OrgConfig.HEALTH_CARE_STATUS);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(SendSmsService.class)
                .sendSmsCode(RequestUrl.XY0006, map)
                .enqueue(new Callback<SmsEntity>() {
                    @Override
                    public void onResponse(Call<SmsEntity> call, Response<SmsEntity> response) {
                        SmsEntity body = response.body();
                        if (body != null) {
                            String returnCode = body.getReturn_code();
                            String resultCode = body.getResult_code();
                            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                String idenCode = body.getIden_code();
                                if (listener != null) {
                                    listener.onSuccess();
                                }
                            } else {
                                if (listener != null) {
                                    listener.onFailed();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SmsEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                });
    }
}
