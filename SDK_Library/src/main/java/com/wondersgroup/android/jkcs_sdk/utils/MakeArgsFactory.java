package com.wondersgroup.android.jkcs_sdk.utils;

import com.epsoft.hzauthsdk.pub.BusinessArgs;
import com.epsoft.hzauthsdk.pub.ChangePWArgs;
import com.epsoft.hzauthsdk.pub.QueryOpenStatusArgs;
import com.epsoft.hzauthsdk.pub.TokenArgs;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.cons.YiBaoConfig;

/**
 * Created by x-sir on 2018/8/24 :)
 * Function:医保移动支付（开通、查询、认证等相关）
 */
public class MakeArgsFactory {

    private static final String TAG = "MakeArgsFactory";

    /**
     * 开通医保移动支付参数
     *
     * @return
     */
    public static BusinessArgs getOpenArgs() {
        /*
         * 1、未开通医后付的，取健康湖州传过来 2、已开通医后付，取平台
         */
        String phone;
        String signingStatus = SpUtil.getInstance().getString(SpKey.SIGNING_STATUS, "");
        if ("01".equals(signingStatus)) {
            phone = SpUtil.getInstance().getString(SpKey.PHONE, "");
        } else {
            phone = SpUtil.getInstance().getString(SpKey.PASS_PHONE, "");
        }
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        LogUtil.i(TAG, "phone===" + phone + ",name===" + name + ",idNum===" + idNum + ",cardNum===" + cardNum);

        return new BusinessArgs.Builder()
                .setCbd(YiBaoConfig.CBD)
                .setName(name)
                .setCertNum(idNum)
                .setPhoneNum(phone)
                .setAccount(idNum)
                .setCbdName(YiBaoConfig.CB_NAME)
                .setCardNum(cardNum)
                .setAuthChannel(YiBaoConfig.CHANNEL)
                //.setExtendedField(extendedField)
                .build();
    }

    /**
     * 获取医保结算时弹出键盘的参数
     *
     * @return
     */
    public static TokenArgs getKeyboardArgs() {
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        return new TokenArgs.Builder()
                .setAuthChannel(YiBaoConfig.CHANNEL)
                .setCardNum(cardNum)
                .setType("2") // 2 ??
                .build();
    }

    /**
     * 获取试结算时的 Token 参数
     *
     * @return
     */
    public static TokenArgs getTokenArgs() {
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        return new TokenArgs.Builder()
                .setAuthChannel(YiBaoConfig.CHANNEL)
                .setCardNum(cardNum)
                .setType("1")
                .build();
    }

    /**
     * 获取查询开通状态参数
     *
     * @return
     */
    public static QueryOpenStatusArgs getOpenStatusArgs() {
        String socialNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        return new QueryOpenStatusArgs.Builder()
                .setAuthChannel(YiBaoConfig.CHANNEL)
                .setCardNum(socialNum)
                .build();
    }

    /**
     * 修改
     *
     * @return
     */
    public static ChangePWArgs getChangeArgs() {
        String cardNum = "";
        String extendedField = "";

        return new ChangePWArgs.Builder()
                .setAuthChannel(YiBaoConfig.CHANNEL)
                .setCardNum(cardNum)
                .setExtendedField(extendedField)
                .build();
    }

}
