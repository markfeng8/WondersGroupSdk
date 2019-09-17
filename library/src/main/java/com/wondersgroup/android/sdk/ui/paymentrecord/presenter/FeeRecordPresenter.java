package com.wondersgroup.android.sdk.ui.paymentrecord.presenter;

import android.text.TextUtils;

import com.wondersgroup.android.sdk.base.MvpBasePresenter;
import com.wondersgroup.android.sdk.constants.Exceptions;
import com.wondersgroup.android.sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.ui.paymentrecord.contract.FeeRecordContract;
import com.wondersgroup.android.sdk.ui.paymentrecord.model.FeeRecordModel;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

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
            showLoading(true);
            mModel.getFeeRecord(feeState, startDate, endDate, pageNumber, pageSize, new HttpRequestCallback<FeeRecordEntity>() {
                @Override
                public void onSuccess(FeeRecordEntity entity) {
                    LogUtil.i(TAG, "requestYd0008() -> onSuccess()");
                    showLoading(false);
                    if (isNonNull()) {
                        mViewRef.get().onFeeRecordResult(entity);
                    }
                }

                @Override
                public void onFailed(String errCodeDes) {
                    LogUtil.e(TAG, "requestYd0008() -> onFailed()===" + errCodeDes);
                    showLoading(false);
                    WToastUtil.show(errCodeDes);
                }
            });
        } else {
            LogUtil.eLogging(TAG, "getFeeRecord():" + Exceptions.PARAM_IS_NULL);
        }
    }

    private void showLoading(boolean show) {
        if (isNonNull()) {
            mViewRef.get().showLoading(show);
        }
    }
}
