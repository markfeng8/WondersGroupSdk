package com.wondersgroup.android.jkcs_sdk.ui.openmobilepay.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.openmobilepay.contract.MobilePayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openmobilepay.model.MobilePayModel;

/**
 * Created by x-sir on 2018/8/21 :)
 * Function:
 */
public class MobilePayPresenter<T extends MobilePayContract.IView>
        extends MvpBasePresenter<T> implements MobilePayContract.IPresenter {

    private MobilePayContract.IModel mModel = new MobilePayModel();

    public MobilePayPresenter() {
    }

    @Override
    public void openMobilePay() {
        mModel.openMobilePay();
    }
}
