package com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract;

import com.wondersgroup.android.jkcs_sdk.entity.BaseEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SmsEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:设置页面的契约类
 */
public interface SettingsContract {
    interface IModel {
        void sendOpenRequest(HashMap<String, String> map, HttpRequestCallback<BaseEntity> callback);

        void sendVerifyCode(String phone, String idenClass, HttpRequestCallback<SmsEntity> callback);

        void termination(HashMap<String, String> map, HttpRequestCallback<BaseEntity> callback);
    }

    interface IView {
        void dismissPopupWindow();

        void terminationSuccess();

        void onUpdateSuccessResult();
    }

    interface IPresenter {
        void sendOpenRequest(HashMap<String, String> map);

        void sendVerifyCode(String phone, String idenClass);

        void termination(HashMap<String, String> map);
    }
}
