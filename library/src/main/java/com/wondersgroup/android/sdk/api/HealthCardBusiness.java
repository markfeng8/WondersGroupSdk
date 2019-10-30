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

import com.wondersgroup.android.sdk.entity.UserBuilder;
import com.wondersgroup.android.sdk.ui.healthcard.HealthCardWebViewActivity;
import com.wondersgroup.android.sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2019-10-30 :)
 * Function:
 */
public class HealthCardBusiness extends AbstractBusiness {

    @Override
    public void execute(Context context, UserBuilder builder) {
        checkParametersValidity(builder);
        HealthCardWebViewActivity.actionStart(context, builder.getName(), builder.getIdNum(), builder.getPhone());
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

        if (TextUtils.isEmpty(builder.getIdNum()) || builder.getIdNum().length() != 18) {
            WToastUtil.show("证件号码为空或非法！");
        }
    }
}
