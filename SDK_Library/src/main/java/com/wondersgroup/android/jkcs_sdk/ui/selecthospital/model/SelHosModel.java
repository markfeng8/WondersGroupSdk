package com.wondersgroup.android.jkcs_sdk.ui.selecthospital.model;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.HospitalService;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.contract.SelHosContract;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.listener.OnHospitalListListener;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.ProduceUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:
 */
public class SelHosModel implements SelHosContract.IModel {

    private static final String TAG = SelHosModel.class.getSimpleName();

    public SelHosModel() {
    }

    @Override
    public void getHospitalList(OnHospitalListListener listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0008);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(HospitalService.class)
                .getHosList(RequestUrl.XY0008, map)
                .enqueue(new Callback<HospitalEntity>() {
                    @Override
                    public void onResponse(Call<HospitalEntity> call, Response<HospitalEntity> response) {
                        HospitalEntity body = response.body();
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
                    public void onFailure(Call<HospitalEntity> call, Throwable t) {
                        String error = t.getMessage();
                        LogUtil.e(TAG, error);
                        if (listener != null) {
                            listener.onFailed();
                        }
                    }
                });
    }
}
