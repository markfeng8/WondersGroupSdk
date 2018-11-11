package com.wondersgroup.android.jkcs_sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view.AfterPayHomeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.view.InHospitalHomeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.view.SelfPayFeeActivity;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public class WondersGroup {

    /**
     * jump to after pay home page.
     *
     * @param context     上下文
     * @param name        姓名
     * @param phone       手机号
     * @param idType      证件类型(01：身份证)
     * @param idNum       证件号码
     * @param cardType    就诊卡类型(0：社保卡 2：自费卡)
     * @param cardNum     就诊卡号
     * @param homeAddress 家庭地址
     */
    public static void startAfterPayHome(@NonNull Context context,
                                         @NonNull String name,
                                         @NonNull String phone,
                                         @NonNull String idType,
                                         @NonNull String idNum,
                                         @NonNull String cardType,
                                         @NonNull String cardNum,
                                         @NonNull String homeAddress) {

        if (checkParametersValidity(name, phone, idType, idNum, cardType, cardNum, homeAddress)) {
            return;
        }
        saveParametersValue(name, phone, idType, idNum, cardType, cardNum, homeAddress);
        jumpToActivityWithHashMap(context, name, phone, idType, idNum, cardType, cardNum, homeAddress);
    }

    /**
     * 跳转到住院页面首页
     */
    public static void startInHospitalHome(@NonNull Context context,
                                           @NonNull String name,
                                           @NonNull String phone,
                                           @NonNull String idType,
                                           @NonNull String idNum,
                                           @NonNull String cardType,
                                           @NonNull String cardNum,
                                           @NonNull String homeAddress) {

        if (checkParametersValidity(name, phone, idType, idNum, cardType, cardNum, homeAddress)) {
            return;
        }
        saveParametersValue(name, phone, idType, idNum, cardType, cardNum, homeAddress);
        InHospitalHomeActivity.actionStart(context);
    }

    private static void jumpToActivityWithHashMap(@NonNull Context context,
                                                  @NonNull String name,
                                                  @NonNull String phone,
                                                  @NonNull String idType,
                                                  @NonNull String idNum,
                                                  @NonNull String cardType,
                                                  @NonNull String cardNum,
                                                  @NonNull String homeAddress) {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.NAME, name);
        map.put(MapKey.PHONE, phone);
        map.put(MapKey.ID_TYPE, idType);
        map.put(MapKey.ID_NO, idNum);
        map.put(MapKey.CARD_TYPE, cardType);
        map.put(MapKey.CARD_NO, cardNum);
        map.put(MapKey.HOME_ADDRESS, homeAddress);

        switch (cardType) {
            case "0": // 0：社保卡
                startActivityWithParam(context, map, AfterPayHomeActivity.class);
                break;
            case "2": // 2：自费卡
                startActivityWithParam(context, map, SelfPayFeeActivity.class);
                break;
            default:
                WToastUtil.show("非法的就诊卡类型！");
                break;
        }
    }

    /**
     * 校验传递过来参数的合法性
     */
    private static boolean checkParametersValidity(@NonNull String name,
                                                   @NonNull String phone,
                                                   @NonNull String idType,
                                                   @NonNull String idNum,
                                                   @NonNull String cardType,
                                                   @NonNull String cardNum,
                                                   @NonNull String homeAddress) {
        if (TextUtils.isEmpty(name)) {
            WToastUtil.show("请输入姓名！");
            return true;
        }
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            WToastUtil.show("手机号为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(idType) || idType.length() != 2) {
            WToastUtil.show("证件类型为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(idNum) || idNum.length() != 18) {
            WToastUtil.show("证件号码为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(cardType) || cardType.length() != 1) {
            WToastUtil.show("就诊卡类型为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(cardNum) || cardNum.length() != 9) {
            WToastUtil.show("就诊卡号为空或非法！");
            return true;
        }
        if (TextUtils.isEmpty(homeAddress)) {
            WToastUtil.show("家庭地址为空或非法！");
            return true;
        }
        return false;
    }

    /**
     * jump to activity with HashMap parameters.
     *
     * @param context 上下文
     * @param param   map collection
     * @param clazz   Activity Simple name
     */
    private static void startActivityWithParam(Context context, HashMap<String, String> param, Class<?> clazz) {
        if (context != null) {
            if (param != null && !param.isEmpty()) {
                // 传递数据
                SerializableHashMap sMap = new SerializableHashMap();
                sMap.setMap(param); // 将map数据添加到封装的sMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentExtra.SERIALIZABLE_MAP, sMap);
                Intent intent = new Intent(context, clazz);
                intent.putExtras(bundle);
                context.startActivity(intent);
            } else {
                throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
            }

        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_CONTEXT_NULL);
        }
    }

    /**
     * save parameters value.
     */
    private static void saveParametersValue(String name, String phone, String idType, String idNum,
                                            String cardType, String cardNum, String homeAddress) {
        SpUtil.getInstance().save(SpKey.NAME, name);
        SpUtil.getInstance().save(SpKey.PASS_PHONE, phone);
        SpUtil.getInstance().save(SpKey.ID_TYPE, idType);
        SpUtil.getInstance().save(SpKey.ID_NUM, idNum);
        SpUtil.getInstance().save(SpKey.CARD_TYPE, cardType);
        SpUtil.getInstance().save(SpKey.CARD_NUM, cardNum);
        SpUtil.getInstance().save(SpKey.HOME_ADDRESS, homeAddress);
    }
}
