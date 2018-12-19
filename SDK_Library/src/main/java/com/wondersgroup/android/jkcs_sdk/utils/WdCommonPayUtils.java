/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;

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
        if (!NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            WToastUtil.show("无法连接到互联网，请检查您的网络连接！");
            if (listener != null) {
                listener.onFailed();
            }
            return;
        }

        if (TextUtils.isEmpty(amount)) {
            WToastUtil.show("需支付的的金额非法！");
            if (listener != null) {
                listener.onFailed();
            }
            return;
        }

        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        Activity context = weakReference.get();
        if (context == null) {
            if (listener != null) {
                listener.onFailed();
            }
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
            if (listener != null) {
                listener.onFailed();
            }
            return;
        }

        if ((payType == 2) && (!AppInfoUtil.isWeChatAppInstalled(WondersApplication.getsContext()))) {
            WToastUtil.show("您没有安装微信客户端，请先安装微信客户端！");
            if (listener != null) {
                listener.onFailed();
            }
        } else {
            String describe = "药品费";
            // 传入订单标题、订单金额(分)、订单流水号、扩展参数(可以null) 等
            WDPay.reqPayAsync(context, appId, apiKey, PaymentUtil.getWdPayType(payType),
                    subMerNo, orgName, describe, formatCents, tradeNo, describe, null,
                    wdResult -> {
                        final WDPayResult bcPayResult = (WDPayResult) wdResult;
                        context.runOnUiThread(() -> {

                            String result = bcPayResult.getResult();
                            LogUtil.i(TAG, "done result=" + result);

                            switch (result) {
                                case WDPayResult.RESULT_SUCCESS:
                                    WToastUtil.show("支付成功~");
                                    if (listener != null) {
                                        listener.onSuccess();
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

    private static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]+(.[0-9]{1,2})?$");
    }

    public interface OnPaymentResultListener {
        void onSuccess();

        void onFailed();
    }
}
