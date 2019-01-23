/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.api;

import android.content.Context;
import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.cons.ErrorCode;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.UserBuilder;
import com.wondersgroup.android.jkcs_sdk.ui.selfpayfee.view.SelfPayFeeActivity;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2019/1/22 :)
 * Function:自费业务的具体实现
 */
public class SelfPayBusiness extends AbstractBusiness {

    @Override
    public void execute(Context context, UserBuilder builder) {
        checkParametersValidity(builder);
        SelfPayFeeActivity.actionStart(context);
    }

    @Override
    public void checkParametersValidity(UserBuilder builder) {
        super.checkParametersValidity(builder);

        if (builder == null) {
            WToastUtil.show("UserBuilder object is null!");
            return;
        }

        if (TextUtils.isEmpty(builder.getName())) {
            WToastUtil.show("请输入姓名！");
            return;
        }

        if (TextUtils.isEmpty(builder.getPhone()) || builder.getPhone().length() != 11) {
            WToastUtil.show("手机号为空或非法！");
            return;
        }

        if (TextUtils.isEmpty(builder.getIdType()) || builder.getIdType().length() != 2) {
            WToastUtil.show("证件类型为空或非法！");
            return;
        }

        if (TextUtils.isEmpty(builder.getIdNum()) || builder.getIdNum().length() != 18) {
            WToastUtil.show("证件号码为空或非法！");
            return;
        }

        if (TextUtils.isEmpty(builder.getCardType()) || !("2".equals(builder.getCardType()))) {
            WToastUtil.show("就诊卡类型为空或非法！" + ErrorCode.ERROR1002);
            return;
        }

        if (TextUtils.isEmpty(builder.getAddress())) {
            WToastUtil.show("家庭地址为空或非法！");
            return;
        }

        saveUserInfo(builder);
    }

    @Override
    public void saveUserInfo(UserBuilder builder) {
        super.saveUserInfo(builder);

        HashMap<String, Object> map = new HashMap<>();
        map.put(SpKey.NAME, builder.getName());
        map.put(SpKey.PASS_PHONE, builder.getPhone());
        map.put(SpKey.ID_TYPE, builder.getIdType());
        map.put(SpKey.ID_NUM, builder.getIdNum().toUpperCase());
        map.put(SpKey.CARD_TYPE, builder.getCardType());
        // 当为自费卡时，卡号固定传 9 个 0
        map.put(SpKey.CARD_NUM, "000000000");
        map.put(SpKey.HOME_ADDRESS, builder.getAddress());

        save(map);
    }
}
