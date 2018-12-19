/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0007Entity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0006RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnCy0007RequestListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.model.LeaveHospitalModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.lang.ref.WeakReference;

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
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            showLoading();
        }

        mModel.requestCy0006(orgCode, token, new OnCy0006RequestListener() {
            @Override
            public void onSuccess(Cy0006Entity entity) {
                LogUtil.i(TAG, "requestCy0006() -> success~");
                dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onCy0006Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0006() -> failed!" + errCodeDes);
                dismissLoading();
                WToastUtil.show(errCodeDes);
            }
        });
    }

    @Override
    public void requestCy0007(String orgCode, String toState, String token, String xxjje, String payChl) {
        if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
            showLoading();
        }

        mModel.requestCy0007(orgCode, toState, token, xxjje, payChl, new OnCy0007RequestListener() {
            @Override
            public void onSuccess(Cy0007Entity entity) {
                LogUtil.i(TAG, "requestCy0006() -> success~");
                dismissLoading();
                if (isNonNull()) {
                    mViewRef.get().onCy0007Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0006() -> failed!" + errCodeDes);
                dismissLoading();
                WToastUtil.show(errCodeDes);
                if (isNonNull()) {
                    mViewRef.get().onCy0007Result(null);
                }
            }
        });
    }

    @Override
    public void getPayParam(String orgCode) {
        if (!TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.getPayParam(orgCode, new OnPayParamListener() {
                @Override
                public void onSuccess(PayParamEntity entity) {
                    LogUtil.i(TAG, "getPayParam() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onPayParamResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getPayParam() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void getYiBaoToken() {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        // 0 是社保卡，2 是自费卡
        if ("0".equals(cardType)) {
            if (isNonNull()) {
                Activity activity = (Activity) mViewRef.get();
                WeakReference<Activity> weakReference = new WeakReference<>(activity);
                mModel.getYiBaoToken(weakReference, token -> {
                    if (isNonNull()) {
                        mViewRef.get().onYiBaoTokenResult(token);
                    }
                });

            } else {
                LogUtil.e(TAG, "activity is null!");
            }
        } else if ("2".equals(cardType)) {
            if (isNonNull()) {
                mViewRef.get().onYiBaoTokenResult("0");
            }
        }
    }

    @Override
    public void getTryToSettleToken() {
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        // 0 是社保卡，2 是自费卡
        if ("0".equals(cardType)) {
            if (isNonNull()) {
                Activity activity = (Activity) mViewRef.get();
                WeakReference<Activity> weakReference = new WeakReference<>(activity);
                mModel.getTryToSettleToken(weakReference, token -> {
                    if (token != null) {
                        if (isNonNull()) {
                            mViewRef.get().onTryToSettleTokenResult(token);
                        }
                    } else {
                        LogUtil.e(TAG, "getTryToSettleToken() -> token is null!");
                    }
                });

            } else {
                LogUtil.e(TAG, "activity is null!");
            }
        } else if ("2".equals(cardType)) {
            if (isNonNull()) {
                mViewRef.get().onTryToSettleTokenResult("0");
            }
        }
    }

    @Override
    public void queryYiBaoOpenStatus() {
        if (isNonNull()) {
            Activity activity = (Activity) mViewRef.get();
            WeakReference<Activity> weakReference = new WeakReference<>(activity);
            mModel.queryYiBaoOpenStatus(weakReference, status -> {
                if (status == 1) { // 已开通
                    if (isNonNull()) {
                        mViewRef.get().onYiBaoOpenSuccess();
                    }
                } else { // 未开通
                    WToastUtil.show("您未开通医保移动支付，不能进行医保结算！");
                }
            });

        } else {
            LogUtil.e(TAG, "activity is null!");
        }
    }

    private void showLoading() {
        if (isNonNull()) {
            mViewRef.get().showLoading();
        }
    }

    private void dismissLoading() {
        if (isNonNull()) {
            mViewRef.get().dismissLoading();
        }
    }
}
