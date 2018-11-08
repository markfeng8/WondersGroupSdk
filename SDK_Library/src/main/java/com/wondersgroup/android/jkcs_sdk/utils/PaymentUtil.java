/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.utils;

import cn.wd.checkout.api.WDReqParams;

/**
 * Created by x-sir on 2018/10/30 :)
 * Function:统一支付相关工具类
 */
public class PaymentUtil {

    /**
     * 获取万达统一支付的支付类型（默认为支付宝）
     *
     * @param type 类型
     * @return WDChannelTypes
     */
    public static WDReqParams.WDChannelTypes getWdPayType(int type) {
        WDReqParams.WDChannelTypes wdChannelTypes = null;
        switch (type) {
            case 1:
                wdChannelTypes = WDReqParams.WDChannelTypes.alipay;
                break;
            case 2:
                wdChannelTypes = WDReqParams.WDChannelTypes.wepay;
                break;
            case 3:
                wdChannelTypes = WDReqParams.WDChannelTypes.uppaydirect_appand;
                break;
            default:
                wdChannelTypes = WDReqParams.WDChannelTypes.alipay;
                break;
        }

        return wdChannelTypes;
    }
}
