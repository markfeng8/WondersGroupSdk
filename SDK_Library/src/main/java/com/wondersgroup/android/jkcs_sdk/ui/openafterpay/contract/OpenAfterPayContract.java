package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract;

import com.wondersgroup.android.jkcs_sdk.entity.SmsEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:开通医后付页面的契约类
 */
public interface OpenAfterPayContract {
    interface IModel {
        void sendSmsCode(String phone, HttpRequestCallback<SmsEntity> callback);

        void openAfterPay(String phone, String idenCode, HttpRequestCallback<SmsEntity> callback);
    }

    interface IView {
        String getPhoneNumber();

        void onAfterPayOpenSuccess();

        void onAfterPayOpenFailed();

        void showCountDownView();
    }

    interface IPresenter {
        void sendSmsCode();

        void openAfterPay(String phone, String idenCode);
    }
}
