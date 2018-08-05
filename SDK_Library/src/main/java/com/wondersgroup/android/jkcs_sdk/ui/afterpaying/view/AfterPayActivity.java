package com.wondersgroup.android.jkcs_sdk.ui.afterpaying.view;

import android.os.Bundle;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.afterpaying.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpaying.presenter.AfterPayPresenter;

public class AfterPayActivity extends MvpBaseActivity<AfterPayContract.IView,
        AfterPayPresenter<AfterPayContract.IView>> implements AfterPayContract.IView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_pay);
    }

    @Override
    protected AfterPayPresenter<AfterPayContract.IView> createPresenter() {
        return new AfterPayPresenter<>();
    }

    @Override
    protected void bindView() {

    }

    @Override
    public void show() {

    }
}
