/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.api;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.JsInvokeBean;
import com.wondersgroup.android.sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;

/**
 * Created by x-sir on 2019-12-12 :)
 * Function:统一支付工具类
 */
public class PaymentUtils {

    private static final String TAG = "PaymentUtils";

    /**
     * 从健康湖州的家庭医生跳转到支付页面
     *
     * @param context Activity context
     * @param json    data parameters
     */
    public static void toPay(Context context, String json) {
        if (!TextUtils.isEmpty(json)) {
            LogUtil.json(TAG, json);

            JsInvokeBean jsInvokeBean = new Gson().fromJson(json, JsInvokeBean.class);
            String name = jsInvokeBean.getName();
            String cardType = jsInvokeBean.getCardType();
            String cardNum = jsInvokeBean.getCardNum();
            String orgCode = jsInvokeBean.getOrgCode();
            String orgName = jsInvokeBean.getOrgName();
            String idType = jsInvokeBean.getIdType();
            String idNum = jsInvokeBean.getIdNum();

            SpUtil.getInstance().save(SpKey.NAME, name);
            SpUtil.getInstance().save(SpKey.CARD_TYPE, cardType);
            SpUtil.getInstance().save(SpKey.CARD_NUM, cardNum);
            SpUtil.getInstance().save(SpKey.ID_TYPE, idType);
            SpUtil.getInstance().save(SpKey.ID_NUM, idNum);

            PaymentDetailsActivity.actionStart(context, orgCode, orgName, false);
        }
    }
}
