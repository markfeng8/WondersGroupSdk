package com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract;

import com.wondersgroup.android.jkcs_sdk.listener.OnOpenResultListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnTerminationListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnVerifySendListener;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public interface SettingsContract {
    interface IModel {
        void sendOpenRequest(HashMap<String, String> map, OnOpenResultListener listener);

        void sendVerifyCode(String phone, String idenClass, OnVerifySendListener listener);

        void termination(HashMap<String, String> map, OnTerminationListener listener);
    }

    interface IView {
        void dismissPopupWindow();

        void terminationSuccess();
    }

    interface IPresenter {
        void sendOpenRequest(HashMap<String, String> map);

        void sendVerifyCode(String phone, String idenClass);

        void termination(HashMap<String, String> map);
    }
}
