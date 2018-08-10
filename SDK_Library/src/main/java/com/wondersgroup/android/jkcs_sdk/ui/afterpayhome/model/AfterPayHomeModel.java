package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.AfterPayStateService;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.ProduceUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public class AfterPayHomeModel implements AfterPayHomeContract.IModel {

    private static final String TAG = AfterPayHomeModel.class.getSimpleName();

    public AfterPayHomeModel() {
    }

    @Override
    public void getAfterPayState(HashMap<String, String> map, final OnAfterPayStateListener listener) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0001);
        map.put(MapKey.ID_TYPE, "01"); // 证件类型 01：身份证
        map.put(MapKey.TRAN_CHL, "01");
        map.put(MapKey.CARD_TYPE, "0"); // 就诊卡类型 0：社保卡
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(AfterPayStateService.class)
                .findAfterPayState(RequestUrl.XY0001, map)
                .enqueue(new Callback<AfterPayStateEntity>() {
                    @Override
                    public void onResponse(Call<AfterPayStateEntity> call, Response<AfterPayStateEntity> response) {
                        AfterPayStateEntity body = response.body();
                        if (body != null) {
                            String return_code = body.getReturn_code();
                            if ("SUCCESS".equals(return_code)) {
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
                    public void onFailure(Call<AfterPayStateEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                });
    }
}
