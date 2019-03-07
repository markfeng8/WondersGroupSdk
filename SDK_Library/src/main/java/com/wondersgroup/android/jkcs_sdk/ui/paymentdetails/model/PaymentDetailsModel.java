package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.api.Converter;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeBillService;
import com.wondersgroup.android.jkcs_sdk.net.service.GetPayParamService;
import com.wondersgroup.android.jkcs_sdk.net.service.LockOrderService;
import com.wondersgroup.android.jkcs_sdk.net.service.OrderDetailsService;
import com.wondersgroup.android.jkcs_sdk.net.service.SettleService;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.jkcs_sdk.utils.DateUtils;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.ProduceUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SignUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面的 Model 类
 */
public class PaymentDetailsModel implements PaymentDetailsContract.IModel {

    private static final String TAG = "PaymentDetailsModel";
    private String mName;
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;

    public PaymentDetailsModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
    }

    @Override
    public void requestYd0003(String orgCode, HttpRequestCallback<FeeBillEntity> callback) {
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
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.FEE_STATE, OrgConfig.FEE_STATE00);
        map.put(MapKey.START_DATE, OrgConfig.ORDER_START_DATE);
        map.put(MapKey.END_DATE, DateUtils.getCurrentDate());
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

    @Override
    public void lockOrder(HashMap<String, Object> map, int totalCount, HttpRequestCallback<LockOrderEntity> callback) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0005);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.NAME, mName);
        map.put(MapKey.ID_TYPE, mIdType);
        map.put(MapKey.ID_NO, mIdNum);
        map.put(MapKey.CARD_TYPE, mCardType);
        map.put(MapKey.CARD_NO, mCardNum);
        map.put(MapKey.TOTAL_COUNT, String.valueOf(totalCount));
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        RetrofitHelper
                .getInstance()
                .createService(LockOrderService.class)
                .lockOrder(RequestUrl.YD0005, Converter.toBody(map))
                .enqueue(new Callback<LockOrderEntity>() {
                    @Override
                    public void onResponse(Call<LockOrderEntity> call, Response<LockOrderEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            LockOrderEntity body = response.body();
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
                    public void onFailure(Call<LockOrderEntity> call, Throwable t) {
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
     * 获取账单明细
     *
     * @param hisOrderNo
     * @param callback
     */
    @Override
    public void getOrderDetails(String hisOrderNo, String orgCode, HttpRequestCallback<OrderDetailsEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0004);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.HIS_ORDER_NO, hisOrderNo);
        map.put(MapKey.SIGN, SignUtil.getSign(map));

        RetrofitHelper
                .getInstance()
                .createService(OrderDetailsService.class)
                .getOrderDetails(RequestUrl.YD0004, map)
                .enqueue(new Callback<OrderDetailsEntity>() {
                    @Override
                    public void onResponse(Call<OrderDetailsEntity> call, Response<OrderDetailsEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            OrderDetailsEntity body = response.body();
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
                    public void onFailure(Call<OrderDetailsEntity> call, Throwable t) {
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
    public void tryToSettle(String token, String orgCode, HashMap<String, Object> map, HttpRequestCallback<SettleEntity> callback) {
        String adviceDateTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0006);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.TOKEN, token);
        map.put(MapKey.ADVICE_DATE_TIME, adviceDateTime);
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        RetrofitHelper
                .getInstance()
                .createService(SettleService.class)
                .toSettle(RequestUrl.YD0006, Converter.toBody(map))
                .enqueue(new Callback<SettleEntity>() {
                    @Override
                    public void onResponse(Call<SettleEntity> call, Response<SettleEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            SettleEntity body = response.body();
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
                    public void onFailure(Call<SettleEntity> call, Throwable t) {
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
    public void getPayParam(String orgCode, HttpRequestCallback<PayParamEntity> callback) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0010);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
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
                    public void onFailure(Call<PayParamEntity> call, Throwable t) {
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
    public void sendOfficialPay(boolean isPureYiBao, String toState, String token, String orgCode, HashMap<String, Object> map, HttpRequestCallback<SettleEntity> callback) {
        String adviceDateTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");
        LogUtil.i(TAG, "adviceDateTime===" + adviceDateTime + ",payPlatTradeNo===" + payPlatTradeNo);

        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0007);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, DateUtils.getTheNearestSecondTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.TO_STATE, toState);
        map.put(MapKey.TOKEN, token);
        map.put(MapKey.ADVICE_DATE_TIME, adviceDateTime);
        // 如果现金支付为 0时，锁单号固定传 0，如果不为0，就传真实锁单号
        map.put(MapKey.PAY_PLAT_TRADE_NO, isPureYiBao ? "0" : payPlatTradeNo);
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        RetrofitHelper
                .getInstance()
                .createService(SettleService.class)
                .toSettle(RequestUrl.YD0007, Converter.toBody(map))
                .enqueue(new Callback<SettleEntity>() {
                    @Override
                    public void onResponse(Call<SettleEntity> call, Response<SettleEntity> response) {
                        int code = response.code();
                        String message = response.message();
                        boolean successful = response.isSuccessful();
                        if (code == 200 && "OK".equals(message) && successful) {
                            SettleEntity body = response.body();
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
                    public void onFailure(Call<SettleEntity> call, Throwable t) {
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
