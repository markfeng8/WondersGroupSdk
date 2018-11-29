/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.listener;

import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;

/**
 * Created by x-sir on 2018/8/23 :)
 * Function:查询缴费详情信息的监听器（医后付首页 & 缴费详情页 & 缴费记录页）
 */
public interface OnCy0001RequestListener {

    void onSuccess(Cy0001Entity entity);

    void onFailed(String errCodeDes);
}
