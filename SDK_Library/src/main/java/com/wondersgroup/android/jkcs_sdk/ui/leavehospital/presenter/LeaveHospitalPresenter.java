/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.constants.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0007Entity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.model.LeaveHospitalModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class LeaveHospitalPresenter<T extends LeaveHospitalContract.IView>
        extends MvpBasePresenter<T> implements LeaveHospitalContract.IPresenter {

    private static final String TAG = "LeaveHospitalPresenter";
    private LeaveHospitalContract.IModel mModel = new LeaveHospitalModel();

    @Override
    public void requestCy0006(String orgCode, String token) {
        showLoading(true);
        mModel.requestCy0006(orgCode, token, new HttpRequestCallback<Cy0006Entity>() {
            @Override
            public void onSuccess(Cy0006Entity entity) {
                LogUtil.i(TAG, "requestCy0006() -> success~");
                showLoading(false);
                if (isNonNull()) {
                    mViewRef.get().onCy0006Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0006() -> failed!" + errCodeDes);
                showLoading(false);
                WToastUtil.show(errCodeDes);
            }
        });
    }

    @Override
    public void requestCy0007(String orgCode, String toState, String token, String xxjje, String payChl) {
        showLoading(true);
        mModel.requestCy0007(orgCode, toState, token, xxjje, payChl, new HttpRequestCallback<Cy0007Entity>() {
            @Override
            public void onSuccess(Cy0007Entity entity) {
                LogUtil.i(TAG, "requestCy0007() -> success~");
                showLoading(false);
                if (isNonNull()) {
                    mViewRef.get().onCy0007Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0007() -> failed!" + errCodeDes);
                showLoading(false);
                // 判断是否为 60s 请求超时
                if (!TextUtils.isEmpty(errCodeDes) && errCodeDes.contains("60000ms")) {
                    if (isNonNull()) {
                        mViewRef.get().timeoutAfter60s();
                    }
                } else {
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        mViewRef.get().onCy0007Result(null);
                    }
                }
            }
        });
    }

    @Override
    public void getPayParam(String orgCode) {
        if (!TextUtils.isEmpty(orgCode)) {
            showLoading(true);
            mModel.getPayParam(orgCode, new HttpRequestCallback<PayParamEntity>() {
                @Override
                public void onSuccess(PayParamEntity entity) {
                    LogUtil.i(TAG, "getPayParam() -> onSuccess()");
                    showLoading(false);
                    if (isNonNull()) {
                        mViewRef.get().onPayParamResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getPayParam() -> onFailed()===" + errCodeDes);
                    showLoading(false);
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            LogUtil.eLogging(TAG, "getPayParam():" + Exceptions.PARAM_IS_NULL);
        }
    }

    private void showLoading(boolean show) {
        if (isNonNull()) {
            mViewRef.get().showLoading(show);
        }
    }
}
