package com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public interface SettingsContract {
    interface IModel {
        void getSignStatus();
    }

    interface IView {
        void show();
    }

    interface IPresenter {
        void getSignStatus();
    }
}
