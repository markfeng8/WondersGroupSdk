package com.epsoft.hzauthsdk.utils;

import com.epsoft.hzauthsdk.cons.YiBaoConfig;
import com.epsoft.hzauthsdk.pub.BusinessArgs;
import com.epsoft.hzauthsdk.pub.ChangePWArgs;
import com.epsoft.hzauthsdk.pub.QueryOpenStatusArgs;
import com.epsoft.hzauthsdk.pub.TokenArgs;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

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
        String phone = SpUtil.getInstance().getString(SpKey.PHONE, "");
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        LogUtil.i(TAG, "phone===" + phone + ",name===" + ",idNum===" + idNum + ",cardNum===" + cardNum);

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
     * 获取试结算时弹出键盘的参数
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
        return new ChangePWArgs.Builder()
                .setAuthChannel(YiBaoConfig.CHANNEL)
                .setCardNum(TestArgs.cardNum)
                .setExtendedField(TestArgs.extendedField)
                .build();
    }

}
