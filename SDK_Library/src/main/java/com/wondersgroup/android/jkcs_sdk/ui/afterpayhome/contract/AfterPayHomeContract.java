package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract;

import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnMobilePayStateListener;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.listener.OnUnclearedBillListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public interface AfterPayHomeContract {
    interface IModel {
        void getAfterPayState(HashMap<String, String> map, OnAfterPayStateListener listener);

        void getMobilePayState(HashMap<String, String> map, OnMobilePayStateListener listener);

        void getUnclearedBill(HashMap<String, String> map, OnUnclearedBillListener listener);
    }

    interface IView {
        void afterPayResult(AfterPayStateEntity entity);

        void mobilePayResult(MobilePayEntity entity);

        void feeBillResult(FeeBillEntity entity);

        void showLoading();

        void dismissLoading();
    }

    interface IPresenter {
        void getAfterPayState(HashMap<String, String> map);

        void getMobilePayState(HashMap<String, String> map);

        void getUnclearedBill(HashMap<String, String> map);
    }
}
