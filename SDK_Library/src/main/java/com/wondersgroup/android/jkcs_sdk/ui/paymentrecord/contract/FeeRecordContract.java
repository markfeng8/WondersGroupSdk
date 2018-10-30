package com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.contract;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeRecordListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;

/**
 * Created by x-sir on 2018/9/18 :)
 * Function:
 */
public interface FeeRecordContract {

    interface IModel {
        void getFeeRecord(String feeState, String startDate, String endDate,
                          String pageNumber, String pageSize, OnFeeRecordListener listener);

        void getFeeDetail(String tradeNo, OnFeeDetailListener listener);
    }

    interface IView {

        void onFeeRecordResult(FeeRecordEntity entity);

        void onFeeDetailResult(FeeBillEntity entity);

        void showLoading();

        void dismissLoading();
    }

    interface IPresenter {

        void getFeeRecord(String feeState, String startDate, String endDate,
                          String pageNumber, String pageSize);

        void getFeeDetail(String tradeNo);
    }
}
