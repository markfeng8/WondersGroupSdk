package com.wondersgroup.android.jkcs_sdk.ui.personalpay.contract;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:
 */
public interface PersonalPayContract {

    interface IModel {
        void sendOfficialPay();
    }

    interface IView {
    }

    interface IPresenter {

        void sendOfficialPay();
    }
}
