/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospital.view;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospital.contract.InHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospital.presenter.InHospitalPresenter;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院页面
 */
public class InHospitalActivity extends MvpBaseActivity<InHospitalContract.IView,
        InHospitalPresenter<InHospitalContract.IView>> implements InHospitalContract.IView {

    private static final String TAG = "InHospitalActivity";

    @Override
    protected InHospitalPresenter<InHospitalContract.IView> createPresenter() {
        return new InHospitalPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_in_hospital);
    }
}
