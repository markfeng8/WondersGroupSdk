/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.net.callback;

import android.text.TextUtils;

import com.wondersgroup.android.sdk.entity.BaseEntity;
import com.wondersgroup.android.sdk.utils.LogUtil;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Response;

/**
 * Created by x-sir on 2019-06-20 :)
 * Function:统一处理网络请求的回调
 */
public class ApiSubscriber<T, E> extends ResourceSubscriber<T> {

    private static final String TAG = "ApiSubscriber";
    private static final String SUCCESS = "SUCCESS";
    private static final String OK = "OK";
    private static final int CODE_200 = 200;
    private HttpRequestCallback<E> mCallback;

    public ApiSubscriber() {
    }

    public ApiSubscriber(HttpRequestCallback<E> callback) {
        this.mCallback = callback;
    }

    @Override
    public void onNext(T t) {
        if (t instanceof Response) {
            Response response = (Response) t;
            int code = response.code();
            String message = response.message();
            boolean successful = response.isSuccessful();
            LogUtil.i("My", response.toString());
            LogUtil.i("My", "errorBody====" + response.errorBody());
            if (code == CODE_200 && successful) {
                LogUtil.i("My", "body====" + response.body().toString());
                @SuppressWarnings("unchecked")
                E body = (E) response.body();
                if (body instanceof BaseEntity) {
                    BaseEntity baseEntity = (BaseEntity) body;
                    String returnCode = baseEntity.getReturnCode();
                    String resultCode = baseEntity.getResultCode();
                    if (SUCCESS.equals(returnCode) && SUCCESS.equals(resultCode)) {
                        if (mCallback != null) {
                            mCallback.onSuccess(body);
                        }
                    } else {
                        String errCodeDes = baseEntity.getErrCodeDes();
                        if (!TextUtils.isEmpty(errCodeDes)) {
                            errCodeDes = " ";
                        }
                        if (mCallback != null) {
                            mCallback.onFailed(errCodeDes);
                        }
                    }
                }
            } else {
                if (mCallback != null) {
                    mCallback.onFailed("服务器异常！");
                }
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        String error = t.toString();
        if (!TextUtils.isEmpty(error)) {
            LogUtil.eLogging(TAG, error);
            if (mCallback != null) {
                mCallback.onFailed(error);
            }
        } else {
            if (mCallback != null) {
                mCallback.onFailed("未知错误");
            }
        }
    }

    @Override
    public void onComplete() {
        LogUtil.iLogging(TAG, "onComplete():");
    }
}
