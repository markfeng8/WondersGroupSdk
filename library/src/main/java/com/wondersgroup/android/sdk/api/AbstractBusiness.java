/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.api;

import android.content.Context;

import com.wondersgroup.android.sdk.entity.UserBuilder;
import com.wondersgroup.android.sdk.utils.SpUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2019/1/22 :)
 * Function:抽象业务类
 */
public abstract class AbstractBusiness {

    public abstract void execute(Context context, UserBuilder builder);

    /**
     * 校验参数
     */
    public void checkParametersValidity(UserBuilder builder) {

    }

    /**
     * 保存数据
     */
    public void saveUserInfo(UserBuilder builder) {

    }

    protected final void save(HashMap<String, Object> map) {
        SpUtil.getInstance().save(map);
    }
}
