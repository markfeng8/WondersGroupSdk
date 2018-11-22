package com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.model;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeRecordListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeRecordService;
import com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.contract.FeeRecordContract;
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
 * Created by x-sir on 2018/9/18 :)
 * Function:缴费记录(门诊订单)页面数据的 Model
 */
public class FeeRecordModel implements FeeRecordContract.IModel {

    private static final String TAG = "FeeRecordModel";
    private String mName;
    private String mIdType;
    private String mIdNum;
    private String mCardType; // 包含社保卡和自费卡
    private String mCardNum;

    public FeeRecordModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
    }

    @Override
    public void getFeeRecord(String feeState, String startDate, String endDate, String pageNumber,
                             String pageSize, OnFeeRecordListener listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0008);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.FEE_STATE, feeState);
        map.put(MapKey.START_DATE, startDate);
        map.put(MapKey.END_DATE, endDate);
        map.put(MapKey.PAGE_NUMBER, pageNumber);
        map.put(MapKey.PAGE_SIZE, pageSize);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(FeeRecordService.class)
                .getFeeRecord(RequestUrl.YD0008, map)
                .enqueue(new Callback<FeeRecordEntity>() {
                    @Override
                    public void onResponse(Call<FeeRecordEntity> call, Response<FeeRecordEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            FeeRecordEntity body = response.body();
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
                    public void onFailure(Call<FeeRecordEntity> call, Throwable t) {
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