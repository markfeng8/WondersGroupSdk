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
import android.text.Html;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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
    private TextView tvName;
    private TextView tvHosName;
    private TextView tvSocialNum;
    private TextView tvInHosId;
    private TextView tvInHosDate;
    private TextView tvInHosArea;
    private TextView tvTotalFee;
    private TextView tvYiBaoFee;
    private TextView tvCashFee;
    private TextView tvPrepayFee;
    private TextView tvNeedFee;
    private TextView tvWillPayFee;
    private TextView tvToPay;
    private RadioGroup rgPayType;
    private RadioButton rbAlipay;
    private RadioButton rbWeChatPay;
    private RadioButton rbUnionPay;

    @Override
    protected LeaveHospitalPresenter<LeaveHospitalContract.IView> createPresenter() {
        return new LeaveHospitalPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_leave_hospital);
        findViews();
        initData();
    }

    private void findViews() {
        tvName = findViewById(R.id.tvName);
        tvHosName = findViewById(R.id.tvHosName);
        tvSocialNum = findViewById(R.id.tvSocialNum);
        tvInHosId = findViewById(R.id.tvInHosId);
        tvInHosDate = findViewById(R.id.tvInHosDate);
        tvInHosArea = findViewById(R.id.tvInHosArea);
        tvTotalFee = findViewById(R.id.tvTotalFee);
        tvYiBaoFee = findViewById(R.id.tvYiBaoFee);
        tvCashFee = findViewById(R.id.tvCashFee);
        tvPrepayFee = findViewById(R.id.tvPrepayFee);
        tvNeedFee = findViewById(R.id.tvNeedFee);
        tvWillPayFee = findViewById(R.id.tvWillPayFee);
        tvToPay = findViewById(R.id.tvToPay);
        rgPayType = findViewById(R.id.rgPayType);
        rbAlipay = findViewById(R.id.rbAlipay);
        rbWeChatPay = findViewById(R.id.rbWeChatPay);
        rbUnionPay = findViewById(R.id.rbUnionPay);
    }

    private void initData() {
        rbAlipay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_alipay)));
        rbWeChatPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_wechat_pay)));
        rbUnionPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_union_pay)));
        // 默认选中支付宝
        rgPayType.check(R.id.rbAlipay);
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
