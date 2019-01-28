package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面接口的契约类
 */
public interface PaymentDetailsContract {

    interface IModel {
        void lockOrder(HashMap<String, Object> map, int totalCount, HttpRequestCallback<LockOrderEntity> callback);

        void requestYd0003(String orgCode, HttpRequestCallback<FeeBillEntity> callback);

        void getOrderDetails(String hisOrderNo, String orgCode, HttpRequestCallback<OrderDetailsEntity> callback);

        void tryToSettle(String token, String orgCode, HashMap<String, Object> map, HttpRequestCallback<SettleEntity> callback);

        void getPayParam(String orgCode, HttpRequestCallback<PayParamEntity> callback);

        void sendOfficialPay(boolean isPureYiBao, String toState, String token, String orgCode, HashMap<String, Object> map, HttpRequestCallback<SettleEntity> callback);
    }

    interface IView {
        void onYd0003Result(FeeBillEntity entity);

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

        void requestYd0003(String orgCode);

        void getOrderDetails(String hisOrderNo, String orgCode);

        void tryToSettle(String token, String orgCode, HashMap<String, Object> map);

        void getPayParam(String orgCode);

        void sendOfficialPay(boolean isPureYiBao, String toState, String token, String orgCode, HashMap<String, Object> map);
    }
}
