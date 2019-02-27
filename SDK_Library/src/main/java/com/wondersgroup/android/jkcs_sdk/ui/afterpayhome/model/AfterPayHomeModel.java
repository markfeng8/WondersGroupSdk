package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.model;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.entity.Maps;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.net.service.AfterPayStateService;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeBillService;
import com.wondersgroup.android.jkcs_sdk.net.service.HospitalService;
import com.wondersgroup.android.jkcs_sdk.net.service.MobilePayService;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.ProduceUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtils;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

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

    public AfterPayHomeModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
    }

    @SuppressWarnings("RedundantCollectionOperation")
    @Override
    public void getAfterPayState(HashMap<String, String> map, HttpRequestCallback<AfterPayStateEntity> callback) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0001);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtils.getSecondsTime());
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
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            AfterPayStateEntity body = response.body();
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
                    public void onFailure(Call<AfterPayStateEntity> call, Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            if (callback != null) {
                                callback.onFailed(error);
                            }
                        }
                    }
                });
    }

    @Override
    public void uploadMobilePayState() {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        HashMap<String, String> param = Maps.newHashMapWithExpectedSize();
        param.put(MapKey.SID, ProduceUtil.getSid());
        param.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0002);
        param.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        param.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        param.put(MapKey.TIMESTAMP, TimeUtils.getSecondsTime());
        param.put(MapKey.NAME, mName);
        param.put(MapKey.ID_NO, mIdNum);
        param.put(MapKey.CARD_NO, cardNum);
        param.put(MapKey.ID_TYPE, mIdType);
        param.put(MapKey.CARD_TYPE, cardType);
        param.put(MapKey.MOBILE_PAY_TIME, TimeUtils.getCurrentDate());
        param.put(MapKey.MOBILE_PAY_STATUS, "01");// 01 代表开通
        param.put(MapKey.SIGN, SignUtil.getSign(param));

        RetrofitHelper
                .getInstance()
                .createService(MobilePayService.class)
                .findMobilePayState(RequestUrl.YD0002, param)
                .enqueue(new Callback<MobilePayEntity>() {
                    @Override
                    public void onResponse(Call<MobilePayEntity> call, Response<MobilePayEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
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
                        } else {
                            LogUtil.e("服务器异常！");
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
    public void requestYd0003(String orgCode, HttpRequestCallback<FeeBillEntity> callback) {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        String pageNumber = "1"; // 页数
        String pageSize = "100"; // 每页的条数
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.PAGE_NUMBER, pageNumber);
        map.put(MapKey.PAGE_SIZE, pageSize);
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0003);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtils.getSecondsTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, cardType);
        map.put(MapKey.CARD_NO, cardNum);
        map.put(MapKey.FEE_STATE, OrgConfig.FEE_STATE00);
        map.put(MapKey.START_DATE, OrgConfig.ORDER_START_DATE);
        map.put(MapKey.END_DATE, TimeUtils.getCurrentDate());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(FeeBillService.class)
                .getBillInfo(RequestUrl.YD0003, map)
                .enqueue(new Callback<FeeBillEntity>() {
                    @Override
                    public void onResponse(Call<FeeBillEntity> call, Response<FeeBillEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            FeeBillEntity body = response.body();
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
                    public void onFailure(Call<FeeBillEntity> call, Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            if (callback != null) {
                                callback.onFailed(error);
                            }
                        }
                    }
                });
    }

    /**
     * 旧版本获取医院列表接口
     */
    public void getHospitalListOld(HttpRequestCallback<HospitalEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0008);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtils.getSecondsTime());
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(HospitalService.class)
                .getHosList(RequestUrl.XY0008, map)
                .enqueue(new Callback<HospitalEntity>() {
                    @Override
                    public void onResponse(Call<HospitalEntity> call, Response<HospitalEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            HospitalEntity body = response.body();
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
                    public void onFailure(Call<HospitalEntity> call, Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            if (callback != null) {
                                callback.onFailed(error);
                            }
                        }
                    }
                });
    }

    @Override
    public void getHospitalList(String version, String type, HttpRequestCallback<HospitalEntity> callback) {
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize(8);
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_XY0008);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtils.getSecondsTime());
        map.put(MapKey.VERSION, version);
        map.put(MapKey.TYPE, type);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(HospitalService.class)
                .getHosList(RequestUrl.XY0008, map)
                .enqueue(new Callback<HospitalEntity>() {
                    @Override
                    public void onResponse(Call<HospitalEntity> call, Response<HospitalEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            HospitalEntity body = response.body();
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
                    public void onFailure(Call<HospitalEntity> call, Throwable t) {
                        String error = t.getMessage();
                        if (!TextUtils.isEmpty(error)) {
                            LogUtil.e(TAG, error);
                            if (callback != null) {
                                callback.onFailed(error);
                            }
                        }
                    }
                });
    }

}
