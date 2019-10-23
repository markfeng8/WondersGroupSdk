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
import com.wondersgroup.android.sdk.entity.PayParamEntity;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;

import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDCallBack;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;
import io.reactivex.Observable;

/**
 * Created by x-sir on 2018/12/19 :)
 * Function:万达统一支付工具类
 */
public class WdCommonPayUtils {

    private static final String TAG = "WdCommonPayUtils";

    /**
     * 发起万达统一支付，支付现金部分
     *
     * @return Observable<String> observable
     */
    public static Observable<String> toPay(Activity activity, final PayParamEntity body) {
        return Observable.create(emitter -> {
            String errMsg = "";

            /*
             * 1.判断网络连接是否可用
             */
            if (!NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                errMsg = "无法连接到互联网，请检查您的网络连接！";
                emitter.onNext(errMsg);
                emitter.onComplete();
                return;
            }

            /*
             * 2.判断金额是否为空
             */
            if (TextUtils.isEmpty(body.getFeeNeedCashTotal())) {
                errMsg = "需支付的的金额非法！";
                emitter.onNext(errMsg);
                emitter.onComplete();
                return;
            }

            /*
             * 3.判断上下文是否为空
             */
            WeakReference<Activity> weakReference = new WeakReference<>(activity);
            Activity context = weakReference.get();
            if (context == null) {
                errMsg = "context is null!";
                emitter.onNext(errMsg);
                emitter.onComplete();
                return;
            }

            /*
             * 4.判断金额是否为格式化为（单位：分）
             */
            if (!isNumeric(String.valueOf(getFormatCent(body.getFeeNeedCashTotal())))) {
                errMsg = "请输入正确的交易金额（单位：分）!";
                emitter.onNext(errMsg);
                emitter.onComplete();
                return;
            }

            /*
             * 5.如果是微信支付，判断微信客户端是否安装
             */
            if ((body.getPaymentType() == 2) && (!AppInfoUtil.isWeChatAppInstalled(WondersApplication.getsContext()))) {
                errMsg = "您没有安装微信客户端，请先安装微信客户端！";
                emitter.onNext(errMsg);
                emitter.onComplete();
                return;
            }

            WDCallBack wdCallBack = wdResult -> {
                final WDPayResult bcPayResult = (WDPayResult) wdResult;
                String result = bcPayResult.getResult();
                String payResultMsg = "";
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

                emitter.onNext(payResultMsg);
                emitter.onComplete();
            };

            CheckOut.setIsPrint(true);
            // 设置统一支付回调地址
            CheckOut.setCustomURL(RequestUrl.HOST, RequestUrl.SDK_TO_BILL);
            String describe = "药品费";

            // 传入订单标题、订单金额(分)、订单流水号、扩展参数(可以null) 等
            WDPay.reqPayAsync(context, body.getAppid(), body.getApikey(), PaymentUtil.getWdPayType(body.getPaymentType()), body.getSubmerno(),
                    body.getOrgName(), describe, getFormatCent(body.getFeeNeedCashTotal()), body.getPayPlatTradeNo(), describe, null, wdCallBack);
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
}
