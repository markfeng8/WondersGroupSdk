package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model;

import android.app.Activity;
import android.text.TextUtils;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OpenStatusBean;
import com.wondersgroup.android.jkcs_sdk.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnHospitalListListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnYiBaoMobStatusListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.service.AfterPayStateService;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeBillService;
import com.wondersgroup.android.jkcs_sdk.net.service.HospitalService;
import com.wondersgroup.android.jkcs_sdk.net.service.MobilePayService;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
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
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页数据的 Model 类
 */
public class AfterPayHomeModel implements AfterPayHomeContract.IModel {

    private static final String TAG = AfterPayHomeModel.class.getSimpleName();
    private String mName;
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;

    public AfterPayHomeModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
    }

    @SuppressWarnings("RedundantCollectionOperation")
    @Override
    public void getAfterPayState(HashMap<String, String> map, final OnAfterPayStateListener listener) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0001);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        // 此处时为了判断是否已经请求过一次，是否将 sign 加入 map 中，第二次请求时需要剔除 sign，因为 sign 不参与签名！
        if (map.containsKey(MapKey.SIGN)) {
            map.remove(MapKey.SIGN);
        }
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
                    public void onFailure(Call<AfterPayStateEntity> call, Throwable t) {
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

    private void uploadMobilePayState() {
        HashMap<String, String> param = new HashMap<>();
        param.put(MapKey.SID, ProduceUtil.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0002);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        param.put(MapKey.NAME, mName);
        param.put(MapKey.ID_NO, mIdNum);
        param.put(MapKey.CARD_NO, mCardNum);
        param.put(MapKey.ID_TYPE, mIdType);
        param.put(MapKey.CARD_TYPE, mCardType);
        param.put(MapKey.MOBILE_PAY_TIME, TimeUtil.getCurrentDate());
        param.put(MapKey.MOBILE_PAY_STATUS, "01");// 01 代表开通
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        RetrofitHelper
                .getInstance()
                .createService(MobilePayService.class)
                .findMobilePayState(RequestUrl.YD0002, param)
                .enqueue(new Callback<MobilePayEntity>() {
                    @Override
                    public void onResponse(Call<MobilePayEntity> call, Response<MobilePayEntity> response) {
                        MobilePayEntity body = response.body();
                        if (body != null) {
                            String returnCode = body.getReturn_code();
                            String resultCode = body.getResult_code();
                            if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
                                LogUtil.i(TAG, "移动医保状态上报成功~");
                            } else {
                                String errCodeDes = body.getErr_code_des();
                                if (!TextUtils.isEmpty(errCodeDes)) {
                                    LogUtil.e(TAG, "移动医保状态上报失败~");
                                    WToastUtil.show(errCodeDes);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MobilePayEntity> call, Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            LogUtil.e(TAG, "移动医保状态上报失败~");
                            WToastUtil.show(error);
                        }
                    }
                });
    }

    @Override
    public void requestYd0003(String orgCode, OnFeeDetailListener listener) {
        String pageNumber = "1"; // 页数
        String pageSize = "100"; // 每页的条数
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.PAGE_NUMBER, pageNumber);
        map.put(MapKey.PAGE_SIZE, pageSize);
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0003);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
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
                    public void onFailure(Call<FeeBillEntity> call, Throwable t) {
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
    public void getHospitalList(OnHospitalListListener listener) {
        // 先从缓存中取，如果没有再去网上获取
        String hospitalJson = SpUtil.getInstance().getString(SpKey.HOSPITAL_JSON, "");
        if (!TextUtils.isEmpty(hospitalJson)) {
            HospitalEntity entity = new Gson().fromJson(hospitalJson, HospitalEntity.class);
            if (entity != null) {
                if (listener != null) {
                    listener.onSuccess(entity);
                    return;
                }
            }
        }

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

                                // 缓存到本地
                                String hospitalJson = new Gson().toJson(body);
                                SpUtil.getInstance().save(SpKey.HOSPITAL_JSON, hospitalJson);

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
                    public void onFailure(Call<HospitalEntity> call, Throwable t) {
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
    public void queryYiBaoOpenStatus(WeakReference<Activity> weakReference, OnYiBaoMobStatusListener listener) {
        Activity activity = weakReference.get();
        if (activity == null) {
            return;
        }

        AuthCall.queryOpenStatus(activity, MakeArgsFactory.getOpenStatusArgs(), result -> {
            String mobPayStatus = "00";
            if (!TextUtils.isEmpty(result)) {
                LogUtil.i(TAG, "result===" + result);
                OpenStatusBean statusBean = new Gson().fromJson(result, OpenStatusBean.class);
                int isYbPay = statusBean.getIsYbPay();
                if (isYbPay == 1) { // 已开通
                    mobPayStatus = "01";
                    uploadMobilePayState();
                } else { // 未开通
                    mobPayStatus = "00";
                }

                if (listener != null) {
                    listener.onResult(mobPayStatus);
                }
            }

            // 保存医保移动支付开通状态
            SpUtil.getInstance().save(SpKey.MOB_PAY_STATUS, mobPayStatus);
        });
    }

}
