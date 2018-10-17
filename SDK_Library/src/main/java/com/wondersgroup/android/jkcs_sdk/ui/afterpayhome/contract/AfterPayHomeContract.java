package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract;

import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeRecordListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnHospitalListListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnMobilePayStateListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页接口的契约类
 */
public interface AfterPayHomeContract {

    interface IModel {
        void getAfterPayState(HashMap<String, String> map, OnAfterPayStateListener listener);

        void uploadMobilePayState(String status, OnMobilePayStateListener listener);

        void getUnclearedBill(HashMap<String, String> map, OnFeeDetailListener listener);

        void getHospitalList(OnHospitalListListener listener);

        void getFeeRecord(String feeState, String startDate, String endDate,
                          String pageNumber, String pageSize, OnFeeRecordListener listener);

        void getFeeDetail(String tradeNo, OnFeeDetailListener listener);
    }

    interface IView {
        void afterPayResult(AfterPayStateEntity entity);

        void feeBillResult(FeeBillEntity entity);

        void showLoading();

        void dismissLoading();

        void onHospitalListResult(HospitalEntity body);

        void onFeeRecordResult(FeeRecordEntity entity);

        void onFeeDetailResult(FeeBillEntity entity);
    }

    interface IPresenter {
        void getAfterPayState(HashMap<String, String> map);

        void uploadMobilePayState(String status);

        void getUnclearedBill(HashMap<String, String> map);

        void getHospitalList();

        void getFeeRecord(String feeState, String startDate, String endDate,
                          String pageNumber, String pageSize);

        void getFeeDetail(String tradeNo);
    }
}
