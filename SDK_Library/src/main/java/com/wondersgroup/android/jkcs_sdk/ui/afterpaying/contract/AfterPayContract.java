package com.wondersgroup.android.jkcs_sdk.ui.afterpaying.contract;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public interface AfterPayContract {
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
