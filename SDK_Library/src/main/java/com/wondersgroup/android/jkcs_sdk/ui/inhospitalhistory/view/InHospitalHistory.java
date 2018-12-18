/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.view;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.contract.InHosHisContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhistory.presenter.InHosHisPresenter;

/**
 * Created by x-sir on 2018/12/18 :)
 * Function:
 */
public class InHospitalHistory extends MvpBaseActivity<InHosHisContract.IView,
        InHosHisPresenter<InHosHisContract.IView>> implements InHosHisContract.IView {

    private static final String TAG = "InHospitalHistory";

    @Override
    protected InHosHisPresenter<InHosHisContract.IView> createPresenter() {
        return new InHosHisPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_in_hospital_history);
    }
}
