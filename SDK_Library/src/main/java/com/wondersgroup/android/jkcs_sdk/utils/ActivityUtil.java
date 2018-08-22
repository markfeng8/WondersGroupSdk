package com.wondersgroup.android.jkcs_sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view.AfterPayHomeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view.OpenAfterPayActivity;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.view.SettingsActivity;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public class ActivityUtil {

    /**
     * jump to after pay home page.
     *
     * @param context
     * @param param
     */
    public static void startAfterPayHome(@NonNull Context context, @NonNull HashMap<String, String> param) {
        startActivityWithParam(context, param, AfterPayHomeActivity.class);
    }

    /**
     * jump to open after pay page.
     *
     * @param context
     * @param param
     */
    public static void startOpenAfterPay(@NonNull Context context, @NonNull HashMap<String, String> param) {
        startActivityWithParam(context, param, OpenAfterPayActivity.class);
    }

    /**
     * jump to settings page.
     *
     * @param context
     * @param param
     * @param mAfterPayEntity
     * @param mMobilePayEntity
     */
    public static void startSettingsPage(Context context, HashMap<String, String> param
            , AfterPayStateEntity mAfterPayEntity, MobilePayEntity mMobilePayEntity) {
        if (context != null) {
            if (param != null && !param.isEmpty()) {
                // 传递数据
                SerializableHashMap sMap = new SerializableHashMap();
                sMap.setMap(param); // 将map数据添加到封装的sMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentExtra.SERIALIZABLE_MAP, sMap);
                bundle.putSerializable(IntentExtra.SERIALIZABLE_AFTERPAY_ENTITY, mAfterPayEntity);
                bundle.putSerializable(IntentExtra.SERIALIZABLE_MOBILEPAY_ENTITY, mMobilePayEntity);
                Intent intent = new Intent(context, SettingsActivity.class);
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
     * jump to activity with HashMap parameters.
     *
     * @param context
     * @param param
     * @param clazz
     */
    private static void startActivityWithParam(Context context, HashMap<String, String> param, Class<?> clazz) {
        if (context != null) {
            if (param != null && !param.isEmpty()) {
                savePassValues(param);
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
     * save passing values.
     *
     * @param param HashMap collection.
     */
    private static void savePassValues(@NonNull HashMap<String, String> param) {
        String name = param.get(MapKey.NAME);
        String phone = param.get(MapKey.PHONE);
        String icNum = param.get(MapKey.ID_NO);
        String socialNum = param.get(MapKey.CARD_NO);
        String homeAddress = param.get(MapKey.HOME_ADDRESS);

        SpUtil.getInstance().save(SpKey.NAME, name);
        SpUtil.getInstance().save(SpKey.PHONE, phone);
        SpUtil.getInstance().save(SpKey.IC_NUM, icNum);
        SpUtil.getInstance().save(SpKey.SOCIAL_NUM, socialNum);
        SpUtil.getInstance().save(SpKey.HOME_ADDRESS, homeAddress);
    }
}
