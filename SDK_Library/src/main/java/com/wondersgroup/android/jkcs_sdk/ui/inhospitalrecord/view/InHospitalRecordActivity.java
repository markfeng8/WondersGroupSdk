/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.view;

import android.content.Context;
import android.content.Intent;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.contract.InHospitalRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.presenter.InHospitalRecordPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:
 */
public class InHospitalRecordActivity extends MvpBaseActivity<InHospitalRecordContract.IView,
        InHospitalRecordPresenter<InHospitalRecordContract.IView>> implements InHospitalRecordContract.IView {

    private static final String TAG = "InHospitalRecordActivity";

    @Override
    protected InHospitalRecordPresenter<InHospitalRecordContract.IView> createPresenter() {
        return new InHospitalRecordPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_in_hospital_record);
    }

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, InHospitalRecordActivity.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
