/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.rechargerecord.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.rechargerecord.contract.RechargeRecordContract;
import com.wondersgroup.android.jkcs_sdk.ui.rechargerecord.presenter.RechargeRecordPresenter;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:充值记录页面
 */
public class RechargeRecordActivity extends MvpBaseActivity<RechargeRecordContract.IView,
        RechargeRecordPresenter<RechargeRecordContract.IView>> implements RechargeRecordContract.IView {

    private static final String TAG = "RechargeRecordActivity";
    private RecyclerView recyclerView;

    @Override
    protected RechargeRecordPresenter<RechargeRecordContract.IView> createPresenter() {
        return new RechargeRecordPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_recharge_record);
        findViews();
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    public static void actionStart(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, RechargeRecordActivity.class);
        context.startActivity(intent);
    }
}
