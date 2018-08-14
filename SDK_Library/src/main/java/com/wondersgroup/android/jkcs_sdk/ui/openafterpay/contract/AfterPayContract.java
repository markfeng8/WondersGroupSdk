package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract;

import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.listener.OnOpenAfterPayListener;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.listener.OnSmsSendListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public interface AfterPayContract {
    interface IModel {
        void sendSmsCode(String phone, OnSmsSendListener listener);

        void openAfterPay(HashMap<String, String> map, OnOpenAfterPayListener listener);
    }

    interface IView {
        String getPhoneNumber();
    }

    interface IPresenter {
        void sendSmsCode();

        void openAfterPay(HashMap<String, String> map);
    }
}
