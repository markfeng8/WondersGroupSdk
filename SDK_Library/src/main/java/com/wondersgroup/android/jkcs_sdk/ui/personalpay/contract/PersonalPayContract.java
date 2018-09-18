package com.wondersgroup.android.jkcs_sdk.ui.personalpay.contract;

import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnSettleListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:
 */
public interface PersonalPayContract {

    interface IModel {
        void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map, OnSettleListener listener);
    }

    interface IView {

        void showLoading();

        void dismissLoading();

        void onOfficialSettleResult(SettleEntity body);
    }

    interface IPresenter {

        void sendOfficialPay(String token, String orgCode, HashMap<String, Object> map);
    }
}
