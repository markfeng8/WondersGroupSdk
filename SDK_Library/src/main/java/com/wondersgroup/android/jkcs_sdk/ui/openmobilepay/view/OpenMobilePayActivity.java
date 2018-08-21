package com.wondersgroup.android.jkcs_sdk.ui.openmobilepay.view;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.openmobilepay.contract.MobilePayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openmobilepay.presenter.MobilePayPresenter;

// 开通医保移动支付
public class OpenMobilePayActivity extends MvpBaseActivity<MobilePayContract.IView,
        MobilePayPresenter<MobilePayContract.IView>> implements MobilePayContract.IView {

    @Override
    protected MobilePayPresenter<MobilePayContract.IView> createPresenter() {
        return new MobilePayPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_open_mobile_pay);
        mPresenter.openMobilePay();
    }
}
