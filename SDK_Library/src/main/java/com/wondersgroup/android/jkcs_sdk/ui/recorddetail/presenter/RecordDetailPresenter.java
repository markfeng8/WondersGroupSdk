/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.recorddetail.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model.PaymentDetailsModel;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.contract.RecordDetailContract;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.model.RecordDetailModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
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
    public void getOrderDetails(String hisOrderNo, String orgCode) {
        if (!TextUtils.isEmpty(hisOrderNo)) {
            showLoading(true);
            mPayModel.getOrderDetails(hisOrderNo, orgCode, new HttpRequestCallback<OrderDetailsEntity>() {
                @Override
                public void onSuccess(OrderDetailsEntity entity) {
                    LogUtil.i(TAG, "getOrderDetails() -> onSuccess()");
                    showLoading(false);
                    if (isNonNull()) {
                        mViewRef.get().onOrderDetailsResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getOrderDetails() -> onFailed()===" + errCodeDes);
                    showLoading(false);
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            LogUtil.eLogging(TAG, "getOrderDetails():" + Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void requestYd0009(String tradeNo) {
        if (!TextUtils.isEmpty(tradeNo)) {
            showLoading(true);
            mModel.requestYd0009(tradeNo, new HttpRequestCallback<FeeBillEntity>() {
                @Override
                public void onSuccess(FeeBillEntity entity) {
                    LogUtil.i(TAG, "requestYd0009() -> onSuccess()");
                    showLoading(false);
                    if (isNonNull()) {
                        mViewRef.get().onYd0009Result(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "requestYd0009() -> onFailed()===" + errCodeDes);
                    showLoading(false);
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            LogUtil.eLogging(TAG, "requestYd0009():" + Exceptions.PARAM_IS_NULL);
        }
    }

    private void showLoading(boolean show) {
        if (isNonNull()) {
            mViewRef.get().showLoading(show);
        }
    }
}
