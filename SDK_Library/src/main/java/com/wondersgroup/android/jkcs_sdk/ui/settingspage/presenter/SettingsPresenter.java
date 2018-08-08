package com.wondersgroup.android.jkcs_sdk.ui.settingspage.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract.SettingsContract;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.model.SettingsModel;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class SettingsPresenter<T extends SettingsContract.IView>
        extends MvpBasePresenter<T> implements SettingsContract.IPresenter {

    private SettingsModel mModel = new SettingsModel();

    public SettingsPresenter() {
    }

    @Override
    public void getSignStatus() {

    }
}
