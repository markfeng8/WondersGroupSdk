package com.epsoft.hzauthsdk.utils;

import com.epsoft.hzauthsdk.cons.YiBaoConfig;
import com.epsoft.hzauthsdk.pub.BusinessArgs;
import com.epsoft.hzauthsdk.pub.ChangePWArgs;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

/**
 * Created by x-sir on 2018/8/24 :)
 * Function:医保移动支付（开通、查询、认证等相关）
 */
public class MakeArgsFactory {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    /**
     * 开通医保移动支付参数
     *
     * @return
     */
    public static BusinessArgs getBussArgs() {
        String phone = SpUtil.getInstance().getString(SpKey.PHONE, "");
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

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
