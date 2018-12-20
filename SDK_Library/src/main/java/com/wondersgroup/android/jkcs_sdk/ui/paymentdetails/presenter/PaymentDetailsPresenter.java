package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnLockOrderListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnOrderDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model.PaymentDetailsModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面的 Presenter
 */
public class PaymentDetailsPresenter<T extends PaymentDetailsContract.IView>
        extends MvpBasePresenter<T> implements PaymentDetailsContract.IPresenter {

    private static final String TAG = "PaymentDetailsPresenter";
    private PaymentDetailsContract.IModel mModel = new PaymentDetailsModel();

    @Override
    public void requestYd0003(String orgCode) {
        if (!TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.requestYd0003(orgCode, new OnFeeDetailListener() {
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

            mModel.getOrderDetails(hisOrderNo, orgCode, new OnOrderDetailListener() {
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

    @Override
    public void tryToSettle(String token, String orgCode, HashMap<String, Object> map) {
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.tryToSettle(token, orgCode, map, new OnSettleListener() {
                @Override
                public void onSuccess(SettleEntity body) {
                    LogUtil.i(TAG, "tryToSettle() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onTryToSettleResult(body);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "tryToSettle() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        mViewRef.get().onTryToSettleResult(null);
                    }
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
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
    public void lockOrder(HashMap<String, Object> map, int totalCount) {
        if (map != null && !map.isEmpty()) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.lockOrder(map, totalCount, new OnLockOrderListener() {
                @Override
                public void onSuccess(LockOrderEntity entity) {
                    LogUtil.i(TAG, "lockOrder() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().lockOrderResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "lockOrder() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void sendOfficialPay(boolean isPureYiBao, String toState, String token, String orgCode, HashMap<String, Object> map) {
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(orgCode)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.sendOfficialPay(isPureYiBao, toState, token, orgCode, map, new OnSettleListener() {
                @Override
                public void onSuccess(SettleEntity body) {
                    LogUtil.i(TAG, "sendOfficialPay() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onOfficialSettleResult(body);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "sendOfficialPay() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        // 传 null 表示正式结算失败！
                        mViewRef.get().onOfficialSettleResult(null);
                    }
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
