/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.view;

import android.content.Context;
import android.content.Intent;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.presenter.LeaveHospitalPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:出院结算页面
 */
public class LeaveHospitalActivity extends MvpBaseActivity<LeaveHospitalContract.IView,
        LeaveHospitalPresenter<LeaveHospitalContract.IView>> implements LeaveHospitalContract.IView {

    private static final String TAG = "LeaveHospitalActivity";

    @Override
    protected LeaveHospitalPresenter<LeaveHospitalContract.IView> createPresenter() {
        return new LeaveHospitalPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_leave_hospital);
    }

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, LeaveHospitalActivity.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
