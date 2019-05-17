package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.model;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.constants.MapKey;
import com.wondersgroup.android.jkcs_sdk.constants.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.constants.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.constants.SpKey;
import com.wondersgroup.android.jkcs_sdk.constants.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.SmsEntity;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.net.service.SendSmsService;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.OpenAfterPayContract;
import com.wondersgroup.android.jkcs_sdk.utils.DateUtils;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.RandomUtils;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:开通医后付页面的 Model 类
 */
public class OpenAfterPayModel implements OpenAfterPayContract.IModel {

    private static final String TAG = OpenAfterPayModel.class.getSimpleName();
    private String mName;
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;
    private String mPhone;
    private String mHomeAddress;

    public OpenAfterPayModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mPhone = SpUtil.getInstance().getString(SpKey.PHONE, "");
        mHomeAddress = SpUtil.getInstance().getString(SpKey.HOME_ADDRESS, "");
    }

    @Override
    public void sendSmsCode(String phone, HttpRequestCallback<SmsEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0006);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
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
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            SmsEntity body = response.body();
                            if (body != null) {
                                String returnCode = body.getReturn_code();
                                String resultCode = body.getResult_code();
                                if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                    if (callback != null) {
                                        callback.onSuccess(body);
                                    }
                                } else {
                                    String errCodeDes = body.getErr_code_des();
                                    if (callback != null) {
                                        callback.onFailed(errCodeDes);
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
                    public void onFailure(Call<SmsEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (callback != null) {
                            callback.onFailed(error);
                        }
                    }
                });
    }

    @Override
    public void openAfterPay(String phone, String idenCode, HttpRequestCallback<SmsEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, RandomUtils.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0002);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.REG_ORG_CODE, OrgConfig.ORG_CODE);
        map.put(MapKey.REG_ORG_NAME, OrgConfig.SIGN_ORG_NAME);
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.PHONE, phone);
        map.put(MapKey.HOME_ADDRESS, mHomeAddress);
        map.put(MapKey.IDEN_CODE, idenCode);
        map.put(MapKey.HEALTH_CARE_STATUS, OrgConfig.HEALTH_CARE_STATUS);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(SendSmsService.class)
                .sendSmsCode(RequestUrl.XY0002, map)
                .enqueue(new Callback<SmsEntity>() {
                    @Override
                    public void onResponse(Call<SmsEntity> call, Response<SmsEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            SmsEntity body = response.body();
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
                    public void onFailure(Call<SmsEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (callback != null) {
                            callback.onFailed(error);
                        }
                    }
                });
    }
}
