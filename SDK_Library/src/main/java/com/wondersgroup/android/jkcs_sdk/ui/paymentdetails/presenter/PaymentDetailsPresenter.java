package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnLockOrderListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnOrderDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model.PaymentDetailsModel;
import com.wondersgroup.android.jkcs_sdk.utils.AppInfoUtil;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.PaymentUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面的 Presenter
 */
public class PaymentDetailsPresenter<T extends PaymentDetailsContract.IView>
        extends MvpBasePresenter<T> implements PaymentDetailsContract.IPresenter {

    private static final String TAG = "PaymentDetailsPresenter";
    private PaymentDetailsContract.IModel mModel = new PaymentDetailsModel();

    @Override
    public void requestYd0003(String orgCode) {
        if (!TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.requestYd0003(orgCode, new OnFeeDetailListener() {
                @Override
                public void onSuccess(FeeBillEntity entity) {
                    LogUtil.i(TAG, "requestYd0003() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onYd0003Result(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "requestYd0003() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void getOrderDetails(String hisOrderNo, String orgCode) {
        if (!TextUtils.isEmpty(hisOrderNo)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.getOrderDetails(hisOrderNo, orgCode, new OnOrderDetailListener() {
                @Override
                public void onSuccess(OrderDetailsEntity entity) {
                    LogUtil.i(TAG, "getOrderDetails() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onOrderDetailsResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getOrderDetails() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void tryToSettle(String token, String orgCode, HashMap<String, Object> map) {
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.tryToSettle(token, orgCode, map, new OnSettleListener() {
                @Override
                public void onSuccess(SettleEntity body) {
                    LogUtil.i(TAG, "tryToSettle() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onTryToSettleResult(body);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "tryToSettle() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        mViewRef.get().onTryToSettleResult(null);
                    }
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void getPayParam(String orgCode) {
        if (!TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.getPayParam(orgCode, new OnPayParamListener() {
                @Override
                public void onSuccess(PayParamEntity entity) {
                    LogUtil.i(TAG, "getPayParam() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onPayParamResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getPayParam() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void lockOrder(HashMap<String, Object> map, int totalCount) {
        if (map != null && !map.isEmpty()) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.lockOrder(map, totalCount, new OnLockOrderListener() {
                @Override
                public void onSuccess(LockOrderEntity entity) {
                    LogUtil.i(TAG, "lockOrder() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().lockOrderResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "lockOrder() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void sendOfficialPay(boolean isPureYiBao, String toState, String token, String orgCode, HashMap<String, Object> map) {
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.sendOfficialPay(isPureYiBao, toState, token, orgCode, map, new OnSettleListener() {
                @Override
                public void onSuccess(SettleEntity body) {
                    LogUtil.i(TAG, "sendOfficialPay() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onOfficialSettleResult(body);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "sendOfficialPay() -> onFailed()===" + errCodeDes);
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        dismissLoading();
                        // 传 null 表示正式结算失败！
                        mViewRef.get().onOfficialSettleResult(null);
                    }
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void getYiBaoToken(Activity activity) {
        if (activity != null) {
            WeakReference<Activity> weakReference = new WeakReference<>(activity);
            mModel.getYiBaoToken(weakReference, token -> {
                if (isNonNull()) {
                    mViewRef.get().onYiBaoTokenResult(token);
                }
            });

        } else {
            LogUtil.e(TAG, "activity is null!");
        }
    }

    @Override
    public void getTryToSettleToken(Activity activity) {
        if (activity != null) {
            WeakReference<Activity> weakReference = new WeakReference<>(activity);
            mModel.getTryToSettleToken(weakReference, token -> {
                if (token != null) {
                    if (isNonNull()) {
                        mViewRef.get().onTryToSettleTokenResult(token);
                    }
                } else {
                    LogUtil.e(TAG, "getTryToSettleToken() -> token is null!");
                }
            });

        } else {
            LogUtil.e(TAG, "activity is null!");
        }
    }

    @Override
    public void queryYiBaoOpenStatus(Activity activity) {
        if (activity != null) {
            WeakReference<Activity> weakReference = new WeakReference<>(activity);
            mModel.queryYiBaoOpenStatus(weakReference, status -> {
                if (status == 1) { // 已开通
                    if (isNonNull()) {
                        mViewRef.get().onYiBaoOpenSuccess();
                    }
                } else { // 未开通
                    WToastUtil.show("您未开通医保移动支付，不能进行医保结算！");
                }
            });

        } else {
            LogUtil.e(TAG, "activity is null!");
        }
    }

    /**
     * 发起万达统一支付，支付现金部分
     *
     * @param appId
     * @param subMerNo
     * @param apiKey
     * @param orgName
     * @param tradeNo
     * @param payType
     * @param amount
     */
    @Override
    public void toSettleCashPay(Activity activity, String appId, String subMerNo, String apiKey,
                                String orgName, String tradeNo, int payType, String amount) {
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            showLoading();
        }
        if (TextUtils.isEmpty(amount)) {
            WToastUtil.show("非法的金额！");
            dismissLoading();
            return;
        }

        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        Activity context = weakReference.get();
        if (context == null) {
            dismissLoading();
            return;
        }

        CheckOut.setIsPrint(true);
        // 设置统一支付回调地址
        CheckOut.setCustomURL(RequestUrl.HOST, RequestUrl.SDK_TO_BILL);
        // 初始化金额（0分）
        Long i = 0L;
        // 格式化金额为分
        long formatCents = (long) (Double.parseDouble(amount) * 100);

        if (isNumeric(String.valueOf(formatCents))) {
            i = Long.parseLong(String.valueOf(formatCents));
        } else {
            WToastUtil.show("请输入正确的交易金额（单位：分）");
            dismissLoading();
            return;
        }

        if ((payType == 2) && (!AppInfoUtil.isWeChatAppInstalled(WondersApplication.getsContext()))) {
            WToastUtil.show("您没有安装微信客户端，请先安装微信客户端！");
            dismissLoading();
        } else {
            String describe = "药品费";
            /*
             * 传入订单标题、订单金额(分)、订单流水号、扩展参数(可以null) 等
             */
            WDPay.reqPayAsync(context, appId, apiKey, PaymentUtil.getWdPayType(payType),
                    subMerNo, orgName, describe, i, tradeNo, describe, null,
                    wdResult -> {

                        dismissLoading();
                        final WDPayResult bcPayResult = (WDPayResult) wdResult;
                        context.runOnUiThread(() -> {

                            String result = bcPayResult.getResult();
                            LogUtil.i(TAG, "done result=" + result);

                            switch (result) {
                                case WDPayResult.RESULT_SUCCESS:
                                    WToastUtil.show("支付成功~");
                                    if (isNonNull()) {
                                        mViewRef.get().onCashPaySuccess();
                                    }
                                    break;
                                case WDPayResult.RESULT_CANCEL:
                                    WToastUtil.show("用户取消支付");
                                    break;
                                case WDPayResult.RESULT_FAIL:
                                    String info = "支付失败, 原因: " + bcPayResult.getErrMsg() + ", " + bcPayResult.getDetailInfo();
                                    WToastUtil.show(info);
                                    break;
                                case WDPayResult.FAIL_UNKNOWN_WAY:
                                    WToastUtil.show("未知支付渠道");
                                    break;
                                case WDPayResult.FAIL_WEIXIN_VERSION_ERROR:
                                    WToastUtil.show("针对微信支付版本错误（版本不支持）");
                                    break;
                                case WDPayResult.FAIL_EXCEPTION:
                                    WToastUtil.show("支付过程中的Exception");
                                    break;
                                case WDPayResult.FAIL_ERR_FROM_CHANNEL:
                                    WToastUtil.show("从第三方app支付渠道返回的错误信息，原因: " + bcPayResult.getErrMsg());
                                    break;
                                case WDPayResult.FAIL_INVALID_PARAMS:
                                    WToastUtil.show("参数不合法造成的支付失败");
                                    break;
                                case WDPayResult.RESULT_PAYING_UNCONFIRMED:
                                    WToastUtil.show("表示支付中，未获取确认信息");
                                    break;
                                default:
                                    WToastUtil.show("invalid return");
                                    break;
                            }
                        });
                    });
        }
    }

    private boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]+(.[0-9]{1,2})?$");
    }

    private void showLoading() {
        if (isNonNull()) {
            mViewRef.get().showLoading();
        }
    }

    private void dismissLoading() {
        if (isNonNull()) {
            mViewRef.get().dismissLoading();
        }
    }
}
