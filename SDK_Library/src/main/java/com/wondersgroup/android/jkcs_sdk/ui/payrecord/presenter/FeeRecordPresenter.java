package com.wondersgroup.android.jkcs_sdk.ui.payrecord.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeRecordListener;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.contract.FeeRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.model.FeeRecordModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NetworkUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/9/18 :)
 * Function:
 */
public class FeeRecordPresenter<T extends FeeRecordContract.IView>
        extends MvpBasePresenter<T> implements FeeRecordContract.IPresenter {

    private static final String TAG = "FeeRecordPresenter";
    private FeeRecordContract.IModel mModel = new FeeRecordModel();

    @Override
    public void getFeeRecord(String feeState, String startDate, String endDate,
                             String pageNumber, String pageSize) {
        if (!TextUtils.isEmpty(feeState)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.getFeeRecord(feeState, startDate, endDate, pageNumber, pageSize, new OnFeeRecordListener() {
                @Override
                public void onSuccess(FeeRecordEntity entity) {
                    LogUtil.i(TAG, "getFeeRecord() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onFeeRecordResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getFeeRecord() -> onFailed()===" + errCodeDes);
                    dismissLoading();
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_IS_NULL);
        }
    }

    @Override
    public void getFeeDetail(String tradeNo) {
        if (!TextUtils.isEmpty(tradeNo)) {
            if (NetworkUtil.isNetworkAvailable(WondersApplication.getsContext())) {
                showLoading();
            }

            mModel.getFeeDetail(tradeNo, new OnFeeDetailListener() {
                @Override
                public void onSuccess(FeeBillEntity entity) {
                    LogUtil.i(TAG, "getFeeDetail() -> onSuccess()");
                    dismissLoading();
                    if (isNonNull()) {
                        mViewRef.get().onFeeDetailResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "getFeeDetail() -> onFailed()===" + errCodeDes);
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
