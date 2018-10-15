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
import com.wondersgroup.android.jkcs_sdk.listener.OnLockOrderListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnOrderDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.api.Converter;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeBillService;
import com.wondersgroup.android.jkcs_sdk.net.service.GetPayParamService;
import com.wondersgroup.android.jkcs_sdk.net.service.LockOrderService;
import com.wondersgroup.android.jkcs_sdk.net.service.OrderDetailsService;
import com.wondersgroup.android.jkcs_sdk.net.service.SettleService;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.DetailsContract;
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
    private String mIdType;
    private String mIdNum;
    private String mCardType;
    private String mCardNum;

    public DetailsModel() {
        mName = SpUtil.getInstance().getString(SpKey.NAME, "");
        mIdType = SpUtil.getInstance().getString(SpKey.ID_TYPE, "");
        mIdNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        mCardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        mCardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
    }

    @Override
    public void getUnclearedBill(HashMap<String, String> map, OnFeeDetailListener listener) {
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
    public void lockOrder(HashMap<String, Object> map, int totalCount, OnLockOrderListener listener) {
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0005);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
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
                        LockOrderEntity body = response.body();
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
                    public void onFailure(Call<LockOrderEntity> call, Throwable t) {
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

    /**
     * 获取账单明细
     *
     * @param hisOrderNo
     * @param listener
     */
    @Override
    public void getOrderDetails(String hisOrderNo, String orgCode, OnOrderDetailListener listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0004);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
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
                        OrderDetailsEntity body = response.body();
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
                    public void onFailure(Call<OrderDetailsEntity> call, Throwable t) {
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
    public void tryToSettle(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener) {
        String adviceDateTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0006);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
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
                        SettleEntity body = response.body();
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
                    public void onFailure(Call<SettleEntity> call, Throwable t) {
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
    public void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener) {
        String adviceDateTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");
        LogUtil.i(TAG, "adviceDateTime===" + adviceDateTime + ",payPlatTradeNo===" + payPlatTradeNo);

        map.put(MapKey.SID, ProduceUtil.getSid());
        map.put(MapKey.TRAN_CODE, TranCode.TRAN_YD0007);
        map.put(MapKey.TRAN_CHL, OrgConfig.TRAN_CHL01);
        map.put(MapKey.TRAN_ORG, OrgConfig.ORG_CODE);
        map.put(MapKey.TIMESTAMP, TimeUtil.getSecondsTime());
        map.put(MapKey.ORG_CODE, orgCode);
        map.put(MapKey.TOKEN, token);
        map.put(MapKey.ADVICE_DATE_TIME, adviceDateTime);
        // 如果个人支付为 0时，不传此字段
        //map.put(MapKey.PAY_PLAT_TRADE_NO, payPlatTradeNo);
        map.put(MapKey.SIGN, SignUtil.getSignWithObject(map));

        RetrofitHelper
                .getInstance()
                .createService(SettleService.class)
                .toSettle(RequestUrl.YD0007, Converter.toBody(map))
                .enqueue(new Callback<SettleEntity>() {
                    @Override
                    public void onResponse(Call<SettleEntity> call, Response<SettleEntity> response) {
                        SettleEntity body = response.body();
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
                    public void onFailure(Call<SettleEntity> call, Throwable t) {
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
