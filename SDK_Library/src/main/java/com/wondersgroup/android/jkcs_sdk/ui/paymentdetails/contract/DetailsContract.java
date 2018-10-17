package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnLockOrderListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnOrderDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnPayParamListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面接口的契约类
 */
public interface DetailsContract {

    interface IModel {
        void lockOrder(HashMap<String, Object> map, int totalCount, OnLockOrderListener listener);

        void getUnclearedBill(HashMap<String, String> map, OnFeeDetailListener listener);

        void getOrderDetails(String hisOrderNo, String orgCode, OnOrderDetailListener listener);

        void tryToSettle(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener);

        void getPayParam(String orgCode, OnPayParamListener listener);

        void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener);
    }

    interface IView {
        void feeBillResult(FeeBillEntity entity);

        void lockOrderResult(LockOrderEntity entity);

        void onOrderDetailsResult(OrderDetailsEntity entity);

        void onTryToSettleResult(SettleEntity body);

        void onPayParamResult(PayParamEntity body);

        void onOfficialSettleResult(SettleEntity body);

        void showLoading();

        void dismissLoading();
    }

    interface IPresenter {
        void lockOrder(HashMap<String, Object> map, int totalCount);

        void getUnclearedBill(HashMap<String, String> map);

        void getOrderDetails(String hisOrderNo, String orgCode);

        void tryToSettle(String token, String orgCode, HashMap<String, Object> map);

        void getPayParam(String orgCode);

        void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map);
    }
}
