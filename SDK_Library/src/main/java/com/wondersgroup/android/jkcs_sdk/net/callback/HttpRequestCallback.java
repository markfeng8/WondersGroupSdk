/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.callback;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:Http 请求的统一回调接口
 */
public interface HttpRequestCallback<T> {

    /**
     * 成功的回调
     *
     * @param t 成功的数据
     */
    void onSuccess(T t);

    /**
     * 失败的回调
     *
     * @param errMsg 错误信息
     */
    void onFailed(String errMsg);
}
