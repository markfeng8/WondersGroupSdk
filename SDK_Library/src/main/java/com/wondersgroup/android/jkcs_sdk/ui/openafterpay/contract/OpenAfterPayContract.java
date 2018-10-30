package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract;

import com.wondersgroup.android.jkcs_sdk.listener.OnOpenAfterPayListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnSmsSendListener;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:开通医后付页面的契约类
 */
public interface OpenAfterPayContract {
    interface IModel {
        void sendSmsCode(String phone, OnSmsSendListener listener);

        void openAfterPay(String phone, String idenCode, OnOpenAfterPayListener listener);
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
