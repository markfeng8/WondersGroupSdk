/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.selfpayfee.presenter;

import com.wondersgroup.android.sdk.base.MvpBasePresenter;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.HospitalEntity;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.sdk.ui.afterpayhome.model.AfterPayHomeModel;
import com.wondersgroup.android.sdk.ui.selfpayfee.contract.SelfPayFeeContract;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/10/31 :)
 * Function:
 */
public class SelfPayFeePresenter<T extends SelfPayFeeContract.IView>
        extends MvpBasePresenter<T> implements SelfPayFeeContract.IPresenter {

    private static final String TAG = "SelfPayFeePresenter";
    private AfterPayHomeContract.IModel mHospitalModel = new AfterPayHomeModel();

    @Override
    public void getHospitalList(String version, String type) {
        showLoading(true);
        mHospitalModel.getHospitalList(version, type, new HttpRequestCallback<HospitalEntity>() {
            @Override
            public void onSuccess(HospitalEntity body) {
                LogUtil.i(TAG, "get defaultHospital list success~");
                showLoading(false);
                if (isNonNull()) {
                    mViewRef.get().onHospitalListResult(body);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "get defaultHospital list failed!");
                showLoading(false);
                WToastUtil.show(errCodeDes);
            }
        });
    }

    @Override
    public void requestYd0003(String orgCode) {
        showLoading(true);
        mHospitalModel.requestYd0003(orgCode, new HttpRequestCallback<FeeBillEntity>() {
            @Override
            public void onSuccess(FeeBillEntity entity) {
                LogUtil.i(TAG, "requestYd0003() -> onSuccess()");
                showLoading(false);
                if (isNonNull()) {
                    mViewRef.get().onYd0003Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestYd0003() -> onFailed()===" + errCodeDes);
                showLoading(false);
                WToastUtil.show(errCodeDes);
                if (isNonNull()) {
                    mViewRef.get().onYd0003Result(null);
                }
            }
        });
    }

    private void showLoading(boolean show) {
        if (isNonNull()) {
            mViewRef.get().showLoading(show);
        }
    }
}
