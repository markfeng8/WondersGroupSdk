package com.wondersgroup.android.sdk.ui.paymentresult.presenter;

import com.wondersgroup.android.sdk.base.MvpBasePresenter;
import com.wondersgroup.android.sdk.ui.paymentresult.contract.PaymentResultContract;
import com.wondersgroup.android.sdk.ui.paymentresult.model.PaymentResultModel;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:支付结果的 Presenter
 */
public class PaymentResultPresenter<T extends PaymentResultContract.IView>
        extends MvpBasePresenter<T> implements PaymentResultContract.IPresenter {

    private static final String TAG = "PaymentResultPresenter";
    private PaymentResultContract.IModel mModel = new PaymentResultModel();

}
