package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnUnclearedBillListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnLockOrderListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public interface DetailsContract {

    interface IModel {
        void lockOrder(HashMap<String, Object> map, int totalCount, OnLockOrderListener listener);

        void getUnclearedBill(HashMap<String, String> map, OnUnclearedBillListener listener);
    }

    interface IView {
        void feeBillResult(FeeBillEntity entity);

        void lockOrderResult(LockOrderEntity entity);

        void showLoading();

        void dismissLoading();
    }

    interface IPresenter {
        void lockOrder(HashMap<String, Object> map, int totalCount);

        void getUnclearedBill(HashMap<String, String> map);
    }
}
