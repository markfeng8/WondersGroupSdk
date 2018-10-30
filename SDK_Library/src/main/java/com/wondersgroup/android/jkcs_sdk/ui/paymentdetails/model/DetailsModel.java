package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model;

import android.app.Activity;
import android.text.TextUtils;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.TranCode;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.KeyboardBean;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnLockOrderListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnOrderDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnYiBaoTokenListener;
import com.wondersgroup.android.jkcs_sdk.net.RetrofitHelper;
import com.wondersgroup.android.jkcs_sdk.net.api.Converter;
import com.wondersgroup.android.jkcs_sdk.net.service.FeeBillService;
import com.wondersgroup.android.jkcs_sdk.net.service.GetPayParamService;
import com.wondersgroup.android.jkcs_sdk.net.service.LockOrderService;
import com.wondersgroup.android.jkcs_sdk.net.service.OrderDetailsService;
import com.wondersgroup.android.jkcs_sdk.net.service.SettleService;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.DetailsContract;
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
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面的 Model 类
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
        // 如果现金支付为 0时，锁单号固定传 0，如果不为0，就传真是锁单号
        map.put(MapKey.PAY_PLAT_TRADE_NO, "0");
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

    @Override
    public void getYiBaoToken(WeakReference<Activity> weakReference, OnYiBaoTokenListener listener) {
        String yiBaoToken = SpUtil.getInstance().getString(SpKey.YIBAO_TOKEN, "");
        String tokenTime = SpUtil.getInstance().getString(SpKey.TOKEN_TIME, "");

        // 判断医保 token 和 保存的 token 是否在有效期内，如果在就使用之前的获取到的，如果没有那就获取
        if (!TextUtils.isEmpty(yiBaoToken) && !TextUtils.isEmpty(tokenTime) && !TimeUtil.isOver30min(tokenTime)) {
            if (listener != null) {
                listener.onResult(yiBaoToken);
            }

        } else {
            Activity activity = weakReference.get();
            if (activity == null) {
                return;
            }

            /*
             * 点击 "立即支付" 结算时，先弹出键盘获取 token
             * 如果是第一次需要弹出医保键盘获取 token，获取之后，如果在没退出页面，则此 token 30 min 内有效，
             * 如果退出页面，则需要再次弹出键盘获取 token
             */
            AuthCall.getToken(activity, MakeArgsFactory.getKeyboardArgs(),
                    result -> {
                        LogUtil.i(TAG, "result===" + result);
                        if (!TextUtils.isEmpty(result)) {
                            KeyboardBean keyboardBean = new Gson().fromJson(result, KeyboardBean.class);
                            if (keyboardBean != null) {
                                String code = keyboardBean.getCode();
                                if ("0".equals(code)) {
                                    /*
                                     * 获取 token 后，重新保存医保 token 和 token 时间
                                     */
                                    String token = keyboardBean.getToken();
                                    SpUtil.getInstance().save(SpKey.YIBAO_TOKEN, token);
                                    SpUtil.getInstance().save(SpKey.TOKEN_TIME, TimeUtil.getCurrentMillis());

                                    if (listener != null) {
                                        listener.onResult(token);
                                    }

                                } else {
                                    String msg = keyboardBean.getMsg();
                                    WToastUtil.show(String.valueOf(msg));
                                }
                            }
                        }
                    });
        }
    }

}
