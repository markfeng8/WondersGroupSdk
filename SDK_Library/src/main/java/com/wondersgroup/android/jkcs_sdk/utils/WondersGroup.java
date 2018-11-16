package com.wondersgroup.android.jkcs_sdk.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.ErrorCode;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.UserBuilder;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view.AfterPayHomeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.view.InHospitalHomeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.view.SelfPayFeeActivity;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function: WondersGroup 对外暴露的调用接口类
 */
public class WondersGroup {


    /**
     * 对外暴露的开始调用业务的方法
     *
     * @param context 上下文
     * @param builder 用户信息的构建类
     * @param flag    业务标志 0 医后付 1 自费卡 2 住院
     */
    public static void startBusiness(@NonNull Context context,
                                     @NonNull UserBuilder builder, int flag) {

        if (checkParametersValidity(builder)) {
            return;
        }

        switch (flag) {
            case 0:
                AfterPayHomeActivity.actionStart(context, getHashMapParam(builder));
                break;
            case 1:
                SelfPayFeeActivity.actionStart(context);
                break;
            case 2:
                InHospitalHomeActivity.actionStart(context);
                break;
            default:
                WToastUtil.show("非法的业务类型！" + ErrorCode.ERROR1001);
                break;
        }
    }

    private static HashMap<String, String> getHashMapParam(UserBuilder builder) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.NAME, builder.getName());
        map.put(MapKey.PHONE, builder.getPhone());
        map.put(MapKey.ID_TYPE, builder.getIdType());
        map.put(MapKey.ID_NO, builder.getIdNum());
        map.put(MapKey.CARD_TYPE, builder.getCardType());
        map.put(MapKey.CARD_NO, builder.getCardNum());
        map.put(MapKey.HOME_ADDRESS, builder.getAddress());
        return map;
    }

    /**
     * 校验传递过来参数的合法性
     */
    private static boolean checkParametersValidity(UserBuilder builder) {
        if (builder == null) {
            WToastUtil.show("UserBuilder object is null!");
            return true;
        }
        if (TextUtils.isEmpty(builder.getName())) {
            WToastUtil.show("请输入姓名！");
            return true;
        }
        if (TextUtils.isEmpty(builder.getPhone()) || builder.getPhone().length() != 11) {
            WToastUtil.show("手机号为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(builder.getIdType()) || builder.getIdType().length() != 2) {
            WToastUtil.show("证件类型为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(builder.getIdNum()) || builder.getIdNum().length() != 18) {
            WToastUtil.show("证件号码为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(builder.getCardType()) || builder.getCardType().length() != 1
                || !("0".equals(builder.getCardType()) || "2".equals(builder.getCardType()))) {
            WToastUtil.show("就诊卡类型为空或非法！" + ErrorCode.ERROR1002);
            return true;
        }
        if (TextUtils.isEmpty(builder.getCardNum()) || builder.getCardNum().length() != 9) {
            WToastUtil.show("就诊卡号为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(builder.getAddress())) {
            WToastUtil.show("家庭地址为空或非法！");
            return true;
        }

        saveUserMessage(builder);

        return false;
    }

    /**
     * save user message.
     */
    private static void saveUserMessage(UserBuilder builder) {
        SpUtil.getInstance().save(SpKey.NAME, builder.getName());
        SpUtil.getInstance().save(SpKey.PASS_PHONE, builder.getPhone());
        SpUtil.getInstance().save(SpKey.ID_TYPE, builder.getIdType());
        SpUtil.getInstance().save(SpKey.ID_NUM, builder.getIdNum());
        SpUtil.getInstance().save(SpKey.CARD_TYPE, builder.getCardType());
        SpUtil.getInstance().save(SpKey.CARD_NUM, builder.getCardNum());
        SpUtil.getInstance().save(SpKey.HOME_ADDRESS, builder.getAddress());
    }
}
