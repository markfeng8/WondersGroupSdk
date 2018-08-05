package com.wondersgroup.android.jkcs_sdk.ui.afterpaying.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.afterpaying.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpaying.model.AfterPayModel;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:
 */
public class AfterPayPresenter<T extends AfterPayContract.IView>
        extends MvpBasePresenter<T> implements AfterPayContract.IPresenter {

    private AfterPayModel mModel = new AfterPayModel();

    public AfterPayPresenter() {
    }

    @Override
    public void getSignStatus() {

    }
}
