package com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.contract;

import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.listener.OnSmsSendListener;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public interface SmsAuthContract {
    interface IModel {
        void sendSmsCode(String phone, OnSmsSendListener listener);
    }

    interface IView {
        String getPhoneNumber();
    }

    interface IPresenter {
        void sendSmsCode();
    }
}
