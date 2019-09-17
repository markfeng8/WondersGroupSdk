/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.wondersgroup.android.sdk.entity.UserBuilder;

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
        BusinessFactory.getBusiness(flag).execute(context, builder);
    }
}
