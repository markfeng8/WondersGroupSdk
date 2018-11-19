/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.recorddetail.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnOrderDetailListener;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model.PaymentDetailsModel;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.contract.RecordDetailContract;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.model.RecordDetailModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:
 */
public class RecordDetailPresenter<T extends RecordDetailContract.IView>
        extends MvpBasePresenter<T> implements RecordDetailContract.IPresenter {

    private static final String TAG = "RecordDetailPresenter";
    private RecordDetailContract.IModel mModel = new RecordDetailModel();
    private PaymentDetailsContract.IModel mPayModel = new PaymentDetailsModel();

    @Override
    public void requestYd0003(String orgCode) {
        if (!TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mPayModel.requestYd0003(orgCode, new OnFeeDetailListener() {
                @Override
                public void onSuccess(FeeBillEntity entity) {
                    LogUtil.i(TAG, "requestYd0003() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onYd0003Result(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "requestYd0003() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void getOrderDetails(String hisOrderNo, String orgCode) {
        if (!TextUtils.isEmpty(hisOrderNo)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mPayModel.getOrderDetails(hisOrderNo, orgCode, new OnOrderDetailListener() {
                @Override
                public void onSuccess(OrderDetailsEntity entity) {
                    LogUtil.i(TAG, "getOrderDetails() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onOrderDetailsResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getOrderDetails() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
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
