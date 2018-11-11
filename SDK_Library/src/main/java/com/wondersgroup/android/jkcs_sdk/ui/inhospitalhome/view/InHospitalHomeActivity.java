/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.view;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.view.DayDetailedListActivity;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.contract.InHospitalHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalhome.presenter.InHospitalHomePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.view.InHospitalRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.view.LeaveHospitalActivity;
import com.wondersgroup.android.jkcs_sdk.ui.prepayfeerecharge.view.PrepayFeeRechargeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.rechargerecord.view.RechargeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

/**
 * Created by x-sir on 2018/11/7 :)
 * Function:住院服务首页面
 */
public class InHospitalHomeActivity extends MvpBaseActivity<InHospitalHomeContract.IView,
        InHospitalHomePresenter<InHospitalHomeContract.IView>> implements InHospitalHomeContract.IView {

    private static final String TAG = "InHospitalHomeActivity";
    private TextView tvName;
    private TextView tvIdNum;
    private TextView tvMobPayState;
    private TextView tvPrepayFee;
    private TextView tvRechargeRecord;
    private TextView tvDayDetail;
    private TextView tvLeaveHos;
    private TextView tvInHosRecord;

    @Override
    protected InHospitalHomePresenter<InHospitalHomeContract.IView> createPresenter() {
        return new InHospitalHomePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_in_hospital_home);
        findViews();
        initListener();
        initData();
    }

    private void initData() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");
        tvName.setText(name);
        tvIdNum.setText(idNum);
    }

    private void findViews() {
        tvName = findViewById(R.id.tvName);
        tvIdNum = findViewById(R.id.tvIdNum);
        tvMobPayState = findViewById(R.id.tvMobPayState);
        tvPrepayFee = findViewById(R.id.tvPrepayFee);
        tvRechargeRecord = findViewById(R.id.tvRechargeRecord);
        tvDayDetail = findViewById(R.id.tvDayDetail);
        tvLeaveHos = findViewById(R.id.tvLeaveHos);
        tvInHosRecord = findViewById(R.id.tvInHosRecord);
    }

    private void initListener() {
        // 预交金充值
        tvPrepayFee.setOnClickListener(view -> PrepayFeeRechargeActivity.actionStart(InHospitalHomeActivity.this));
        // 充值记录
        tvRechargeRecord.setOnClickListener(view -> RechargeRecordActivity.actionStart(InHospitalHomeActivity.this));
        // 日清单查询
        tvDayDetail.setOnClickListener(view -> DayDetailedListActivity.actionStart(InHospitalHomeActivity.this));
        // 出院结算
        tvLeaveHos.setOnClickListener(view -> LeaveHospitalActivity.actionStart(InHospitalHomeActivity.this));
        // 历史住院记录
        tvInHosRecord.setOnClickListener(view -> InHospitalRecordActivity.actionStart(InHospitalHomeActivity.this));
    }

    public static void actionStart(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, InHospitalHomeActivity.class);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
