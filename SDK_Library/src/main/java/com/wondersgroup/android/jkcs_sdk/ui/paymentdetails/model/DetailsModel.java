package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnUnclearedBillListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.api.Converter;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeBillService;
import com.wondersgroup.android.jkcs_sdk.net.service.LockOrderService;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.DetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.listener.OnLockOrderListener;
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
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class DetailsModel implements DetailsContract.IModel {

    private static final String TAG = "DetailsModel";
    private String mName;
    private String mIcNum;
    private String mSocialNum;

    public DetailsModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIcNum = SpUtil.getInstance().getString(SpKey.IC_NUM, "");
        mSocialNum = SpUtil.getInstance().getString(SpKey.SOCIAL_NUM, "");
    }

    @Override
    public void getUnclearedBill(HashMap<String, String> map, OnUnclearedBillListener listener) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0003);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, OrgConfig.ID_TYPE01);
        map.put(MapKey.ID_NO, mIcNum);
        map.put(MapKey.CARD_TYPE, OrgConfig.CARD_TYPE0);
        map.put(MapKey.CARD_NO, mSocialNum);
        map.put(MapKey.FEE_STATE, OrgConfig.FEE_STATE00);
        map.put(MapKey.START_DATE, OrgConfig.ORDER_START_DATE);
        map.put(MapKey.END_DATE, TimeUtil.getCurrentDate());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(FeeBillService.class)
                .getBillInfo(RequestUrl.YD0003, map)
                .enqueue(new Callback<FeeBillEntity>() {
                    @Override
                    public void onResponse(Call<FeeBillEntity> call, Response<FeeBillEntity> response) {
                        FeeBillEntity body = response.body();
                        if (body != null) {
                            String returnCode = body.getReturn_code();
                            String resultCode = body.getResult_code();
                            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                if (listener != null) {
                                    listener.onSuccess(body);
                                }
                            } else {
                                if (listener != null) {
                                    listener.onFailed();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<FeeBillEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                });
    }

    @Override
    public void lockOrder(HashMap<String, Object> map, int totalCount, OnLockOrderListener listener) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0005);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, OrgConfig.ID_TYPE01);
        map.put(MapKey.ID_NO, mIcNum);
        map.put(MapKey.CARD_TYPE, OrgConfig.CARD_TYPE0);
        map.put(MapKey.CARD_NO, mSocialNum);
        map.put(MapKey.TOTAL_COUNT, String.valueOf(totalCount));
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        RetrofitHelper
                .getInstance()
                .createService(LockOrderService.class)
                .lockOrder(RequestUrl.YD0005, Converter.toBody(map))
                .enqueue(new Callback<LockOrderEntity>() {
                    @Override
                    public void onResponse(Call<LockOrderEntity> call, Response<LockOrderEntity> response) {
                        LockOrderEntity body = response.body();
                        if (body != null) {
                            String returnCode = body.getReturn_code();
                            String resultCode = body.getResult_code();
                            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                if (listener != null) {
                                    listener.onSuccess(body);
                                }
                            } else {
                                if (listener != null) {
                                    listener.onFailed();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LockOrderEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                });
    }

}
