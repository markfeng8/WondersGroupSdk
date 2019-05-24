/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.wondersgroup.android.sdk.WondersApplication;
import com.wondersgroup.android.sdk.constants.RequestUrl;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;

/**
 * Created by x-sir on 2018/12/19 :)
 * Function:万达统一支付工具类
 */
public class WdCommonPayUtils {

    private static final String TAG = "WdCommonPayUtils";

    /**
     * 发起万达统一支付，支付现金部分
     *
     * @param appId    appId
     * @param subMerNo 子商户号
     * @param apiKey   apiKey
     * @param orgName  组织名称
     * @param tradeNo  交易号
     * @param payType  支付类型
     * @param amount   支付金额
     */
    public static void toPay(Activity activity, String appId, String subMerNo, String apiKey,
                             String orgName, String tradeNo, int payType, String amount, OnPaymentResultListener listener) {
        String errMsg = "";

        /*
         * 1.判断网络连接是否可用
         */
        if (!NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            errMsg = "无法连接到互联网，请检查您的网络连接！";
            if (listener != null) {
                listener.onFailed(errMsg);
            }
            return;
        }

        /*
         * 2.判断金额是否为空
         */
        if (TextUtils.isEmpty(amount)) {
            errMsg = "需支付的的金额非法！";
            if (listener != null) {
                listener.onFailed(errMsg);
            }
            return;
        }

        /*
         * 3.判断上下文是否为空
         */
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        Activity context = weakReference.get();
        if (context == null) {
            errMsg = "context is null!";
            if (listener != null) {
                listener.onFailed(errMsg);
            }
            return;
        }

        /*
         * 4.判断金额是否为格式化为（单位：分）
         */
        if (!isNumeric(String.valueOf(getFormatCent(amount)))) {
            errMsg = "请输入正确的交易金额（单位：分）!";
            if (listener != null) {
                listener.onFailed(errMsg);
            }
            return;
        }

        /*
         * 5.如果是微信支付，判断微信客户端是否安装
         */
        if ((payType == 2) && (!AppInfoUtil.isWeChatAppInstalled(WondersApplication.getsContext()))) {
            errMsg = "您没有安装微信客户端，请先安装微信客户端！";
            if (listener != null) {
                listener.onFailed(errMsg);
            }
            return;
        }

        CheckOut.setIsPrint(true);
        // 设置统一支付回调地址
        CheckOut.setCustomURL(RequestUrl.HOST, RequestUrl.SDK_TO_BILL);
        String describe = "药品费";

        // 传入订单标题、订单金额(分)、订单流水号、扩展参数(可以null) 等
        WDPay.reqPayAsync(context, appId, apiKey, PaymentUtil.getWdPayType(payType),
                subMerNo, orgName, describe, getFormatCent(amount), tradeNo, describe, null,
                wdResult -> {
                    final WDPayResult bcPayResult = (WDPayResult) wdResult;
                    context.runOnUiThread(() -> {

                        String payResultMsg = "";
                        String result = bcPayResult.getResult();
                        LogUtil.i(TAG, "done result=" + result);

                        switch (result) {
                            case WDPayResult.RESULT_SUCCESS:
                                payResultMsg = "SUCCESS";
                                break;
                            case WDPayResult.RESULT_CANCEL:
                                payResultMsg = "用户取消支付";
                                break;
                            case WDPayResult.RESULT_FAIL:
                                payResultMsg = "支付失败, 原因: " + bcPayResult.getErrMsg() + ", " + bcPayResult.getDetailInfo();
                                break;
                            case WDPayResult.FAIL_UNKNOWN_WAY:
                                payResultMsg = "未知支付渠道";
                                break;
                            case WDPayResult.FAIL_WEIXIN_VERSION_ERROR:
                                payResultMsg = "针对微信支付版本错误（版本不支持）";
                                break;
                            case WDPayResult.FAIL_EXCEPTION:
                                payResultMsg = "支付过程中的Exception";
                                break;
                            case WDPayResult.FAIL_ERR_FROM_CHANNEL:
                                payResultMsg = "从第三方app支付渠道返回的错误信息，原因: " + bcPayResult.getErrMsg();
                                break;
                            case WDPayResult.FAIL_INVALID_PARAMS:
                                payResultMsg = "参数不合法造成的支付失败";
                                break;
                            case WDPayResult.RESULT_PAYING_UNCONFIRMED:
                                payResultMsg = "表示支付中，未获取确认信息";
                                break;
                            default:
                                payResultMsg = "invalid return";
                                break;
                        }

                        if (!TextUtils.isEmpty(payResultMsg) && "SUCCESS".equals(payResultMsg)) {
                            if (listener != null) {
                                listener.onSuccess();
                            }
                        } else {
                            if (listener != null) {
                                listener.onFailed(payResultMsg);
                            }
                        }
                    });
                });
    }

    private static long getFormatCent(String amount) {
        long formatCents = 0L;
        try {
            BigDecimal original = new BigDecimal(amount);
            BigDecimal hundred = new BigDecimal("100");
            formatCents = original.multiply(hundred).longValueExact();
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        return formatCents;
    }

    private static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]+(.[0-9]{1,2})?$");
    }

    public interface OnPaymentResultListener {
        void onSuccess();

        void onFailed(String errMsg);
    }
}
