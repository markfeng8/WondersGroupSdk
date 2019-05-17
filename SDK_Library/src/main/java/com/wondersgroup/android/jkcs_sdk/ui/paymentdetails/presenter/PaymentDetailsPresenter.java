package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.constants.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.EleCardTokenEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.model.PaymentDetailsModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
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
            showLoading(true);
            mModel.requestYd0003(orgCode, new HttpRequestCallback<FeeBillEntity>() {
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
                }
            });
        } else {
            LogUtil.eLogging(TAG, "requestYd0003():" + Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void getOrderDetails(String hisOrderNo, String orgCode) {
        if (!TextUtils.isEmpty(hisOrderNo)) {
            showLoading(true);
            mModel.getOrderDetails(hisOrderNo, orgCode, new HttpRequestCallback<OrderDetailsEntity>() {
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
    public void tryToSettle(String token, String orgCode, HashMap<String, Object> map) {
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(orgCode)) {
            showLoading(true);
            mModel.tryToSettle(token, orgCode, map, new HttpRequestCallback<SettleEntity>() {
                @Override
                public void onSuccess(SettleEntity body) {
                    LogUtil.i(TAG, "tryToSettle() -> onSuccess()");
                    showLoading(false);
                    if (isNonNull()) {
                        mViewRef.get().onTryToSettleResult(body);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "tryToSettle() -> onFailed()===" + errCodeDes);
                    showLoading(false);
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        mViewRef.get().onTryToSettleResult(null);
                    }
                }
            });
        } else {
            LogUtil.eLogging(TAG, "tryToSettle():" + Exceptions.PARAM_IS_NULL);
        }
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

    @Override
    public void lockOrder(HashMap<String, Object> map, int totalCount) {
        if (map != null && !map.isEmpty()) {
            showLoading(true);
            mModel.lockOrder(map, totalCount, new HttpRequestCallback<LockOrderEntity>() {
                @Override
                public void onSuccess(LockOrderEntity entity) {
                    LogUtil.i(TAG, "lockOrder() -> onSuccess()");
                    showLoading(false);
                    if (isNonNull()) {
                        mViewRef.get().lockOrderResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "lockOrder() -> onFailed()===" + errCodeDes);
                    showLoading(false);
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            LogUtil.eLogging(TAG, "lockOrder():" + Exceptions.MAP_SET_NULL);
        }
    }

    @Override
    public void sendOfficialPay(boolean isPureYiBao, String toState, String token, String orgCode, HashMap<String, Object> map) {
        if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(orgCode)) {
            showLoading(true);
            mModel.sendOfficialPay(isPureYiBao, toState, token, orgCode, map, new HttpRequestCallback<SettleEntity>() {
                @Override
                public void onSuccess(SettleEntity body) {
                    LogUtil.i(TAG, "sendOfficialPay() -> onSuccess()");
                    showLoading(false);
                    if (isNonNull()) {
                        mViewRef.get().onOfficialSettleResult(body);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "sendOfficialPay() -> onFailed()===" + errCodeDes);
                    showLoading(false);
                    WToastUtil.show(errCodeDes);
                    if (isNonNull()) {
                        // 传 null 表示正式结算失败！
                        mViewRef.get().onOfficialSettleResult(null);
                    }
                }
            });
        } else {
            LogUtil.eLogging(TAG, "sendOfficialPay():" + Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void applyElectronicSocialSecurityCardToken(String businessType) {
        showLoading(true);
        mModel.applyElectronicSocialSecurityCardToken(businessType, new HttpRequestCallback<EleCardTokenEntity>() {
            @Override
            public void onSuccess(EleCardTokenEntity eleCardTokenEntity) {
                LogUtil.i(TAG, "applyElectronicSocialSecurityCardToken() -> onSuccess()");
                showLoading(false);
                if (isNonNull()) {
                    // 传 null 表示正式结算失败！
                    mViewRef.get().onApplyElectronicSocialSecurityCardToken(eleCardTokenEntity);
                }
            }

            @Override
            public void onFailed(String errMsg) {
                LogUtil.e(TAG, "applyElectronicSocialSecurityCardToken() -> onFailed()===" + errMsg);
                showLoading(false);
                WToastUtil.show(errMsg);
            }
        });
    }

    private void showLoading(boolean show) {
        if (isNonNull()) {
            mViewRef.get().showLoading(show);
        }
    }
}
