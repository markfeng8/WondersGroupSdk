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

import com.wondersgroup.android.jkcs_sdk.constants.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.constants.SpKey;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;

/**
 * Created by x-sir on 2018/12/20 :)
 * Function:恩普医保相关的工具类
 */
public class EpSoftUtils {

    private static final String TAG = "EpSoftUtils";

    /**
     * 查询医保移动支付开通状态
     *
     * @param activity
     * @param listener
     */
    public static void queryYiBaoOpenStatus(Activity activity, final OnOpenStatusListener listener) {
        if (activity == null) {
            return;
        }

//        SignatureTool.getSign(activity,);

//        AuthCall.queryOpenStatus(activity, MakeArgsFactory.getOpenStatusArgs(), result -> {
//            String mobPayStatus = "00";
//            if (!TextUtils.isEmpty(result)) {
//                LogUtil.i(TAG, "result===" + result);
//                OpenStatusBean statusBean = new Gson().fromJson(result, OpenStatusBean.class);
//                int isYbPay = statusBean.getIsYbPay();
//                if (isYbPay == 1) { // 已开通
//                    mobPayStatus = "01";
//                } else { // 未开通
//                    mobPayStatus = "00";
//                }
//            }
//
//            // 保存医保移动支付开通状态
//            SpUtil.getInstance().save(SpKey.ELE_CARD_STATUS, mobPayStatus);
//
//            if (listener != null) {
//                listener.onResult(mobPayStatus);
//            }
//        });
    }

    /**
     * 获取试结算 token
     *
     * @param activity
     * @param listener
     */
    public static void getTryToSettleToken(Activity activity, OnTokenListener listener) {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        // 0 是社保卡，2 是自费卡
        if ("0".equals(cardType)) {

            if (activity == null) {
                return;
            }

            if(activity instanceof PaymentDetailsActivity) {
                ((PaymentDetailsActivity) activity).requestTryToSettleToken(OrgConfig.SRY);
            }

        } else if ("2".equals(cardType)) {
            if (listener != null) {
                listener.onResult("0");
            }
        }
    }

    /**
     * 获取正式结算时的 token
     *
     * @param activity
     * @param listener
     */
    public static void getOfficialToSettleToken(Activity activity, OnTokenListener listener) {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        // 0 是社保卡，2 是自费卡
        if ("0".equals(cardType)) {
            String yiBaoToken = SpUtil.getInstance().getString(SpKey.YIBAO_TOKEN, "");
            String tokenTime = SpUtil.getInstance().getString(SpKey.TOKEN_TIME, "");

            // 判断医保 token 和 保存的 token 是否在有效期内，如果在就使用之前的获取到的，如果没有那就获取
            if (!TextUtils.isEmpty(yiBaoToken) && !TextUtils.isEmpty(tokenTime) && !DateUtils.isOver30min(tokenTime)) {
                if (listener != null) {
                    listener.onResult(yiBaoToken);
                }
                return;
            }

            if (activity == null) {
                return;
            }

            /*
             * 点击 "立即支付" 结算时，先弹出键盘获取 token
             * 如果是第一次需要弹出医保键盘获取 token，获取之后，如果在没退出页面，则此 token 30 min 内有效，
             * 如果退出页面，则需要再次弹出键盘获取 token
             */
            if(activity instanceof PaymentDetailsActivity) {
                ((PaymentDetailsActivity) activity).checkElectronicSocialSecurityCardPassword();
            }

        } else if ("2".equals(cardType)) {
            if (listener != null) {
                listener.onResult("0");
            }
        }
    }

    public interface OnTokenListener {
        void onResult(String token);
    }

    public interface OnOpenStatusListener {
        void onResult(String status);
    }
}
