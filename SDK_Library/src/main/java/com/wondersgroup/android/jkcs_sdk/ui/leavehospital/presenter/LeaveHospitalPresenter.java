/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0007Entity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0006RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0007RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.model.LeaveHospitalModel;
import com.wondersgroup.android.jkcs_sdk.utils.AppInfoUtil;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.PaymentUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class LeaveHospitalPresenter<T extends LeaveHospitalContract.IView>
        extends MvpBasePresenter<T> implements LeaveHospitalContract.IPresenter {

    private static final String TAG = "LeaveHospitalPresenter";
    private LeaveHospitalContract.IModel mModel = new LeaveHospitalModel();

    @Override
    public void requestCy0006(String orgCode, String token) {
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            showLoading();
        }

        mModel.requestCy0006(orgCode, token, new OnCy0006RequestListener() {
            @Override
            public void onSuccess(Cy0006Entity entity) {
                LogUtil.i(TAG, "requestCy0006() -> success~");
                dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onCy0006Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0006() -> failed!" + errCodeDes);
                dismissLoading();
                WToastUtil.show(errCodeDes);
            }
        });
    }

    @Override
    public void requestCy0007(String orgCode, String toState, String token, String xxjje, String payChl) {
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            showLoading();
        }

        mModel.requestCy0007(orgCode, toState, token, xxjje, payChl, new OnCy0007RequestListener() {
            @Override
            public void onSuccess(Cy0007Entity entity) {
                LogUtil.i(TAG, "requestCy0006() -> success~");
                dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onCy0007Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0006() -> failed!" + errCodeDes);
                dismissLoading();
                WToastUtil.show(errCodeDes);
            }
        });
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
    public void getYiBaoToken() {
        if (isNonNull()) {
            Activity activity = (Activity) mViewRef.get();
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
    public void getTryToSettleToken() {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        // 0 是社保卡，2 是自费卡
        if ("0".equals(cardType)) {
            if (isNonNull()) {
                Activity activity = (Activity) mViewRef.get();
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
        } else if ("2".equals(cardType)) {
            if (isNonNull()) {
                mViewRef.get().onTryToSettleTokenResult("0");
            }
        }
    }

    @Override
    public void queryYiBaoOpenStatus() {
        if (isNonNull()) {
            Activity activity = (Activity) mViewRef.get();
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

        long formatCents = 0L;
        try {
            BigDecimal original = new BigDecimal(amount);
            BigDecimal hundred = new BigDecimal("100");
            formatCents = original.multiply(hundred).longValueExact();
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        if (!isNumeric(String.valueOf(formatCents))) {
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
                    subMerNo, orgName, describe, formatCents, tradeNo, describe, null,
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
