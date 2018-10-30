package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract;

import android.app.Activity;

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
import com.wondersgroup.android.jkcs_sdk.listener.OnYiBaoOpenStatusListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnYiBaoTokenListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面接口的契约类
 */
public interface PaymentDetailsContract {

    interface IModel {
        void lockOrder(HashMap<String, Object> map, int totalCount, OnLockOrderListener listener);

        void requestYd0003(String orgCode, OnFeeDetailListener listener);

        void getOrderDetails(String hisOrderNo, String orgCode, OnOrderDetailListener listener);

        void tryToSettle(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener);

        void getPayParam(String orgCode, OnPayParamListener listener);

        void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener);

        void getYiBaoToken(WeakReference<Activity> weakReference, OnYiBaoTokenListener listener);

        void getTryToSettleToken(WeakReference<Activity> weakReference, OnYiBaoTokenListener listener);

        void queryYiBaoOpenStatus(WeakReference<Activity> weakReference, OnYiBaoOpenStatusListener listener);
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

        void onYiBaoTokenResult(String token);

        void onTryToSettleTokenResult(String token);

        void onYiBaoOpenSuccess();

        void onCashPaySuccess();
    }

    interface IPresenter {
        void lockOrder(HashMap<String, Object> map, int totalCount);

        void requestYd0003(String orgCode);

        void getOrderDetails(String hisOrderNo, String orgCode);

        void tryToSettle(String token, String orgCode, HashMap<String, Object> map);

        void getPayParam(String orgCode);

        void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map);

        void getYiBaoToken(Activity activity);

        void getTryToSettleToken(Activity activity);

        void queryYiBaoOpenStatus(Activity activity);

        void toSettleCashPay(Activity activity, String appId, String subMerNo, String apiKey, String orgName,
                             String tradeNo, int payType, String amount);
    }
}
