package com.wondersgroup.android.jkcs_sdk.ui.payrecord.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.contract.PayRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.model.PayRecordModel;

/**
 * Created by x-sir on 2018/9/18 :)
 * Function:
 */
public class PayRecordPresenter<T extends PayRecordContract.IView>
        extends MvpBasePresenter<T> implements PayRecordContract.IPresenter {

    private static final String TAG = "PayRecordPresenter";
    private PayRecordContract.IModel mModel = new PayRecordModel();
}
