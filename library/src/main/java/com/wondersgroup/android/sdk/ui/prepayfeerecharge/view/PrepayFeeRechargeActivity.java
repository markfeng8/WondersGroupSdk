/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.prepayfeerecharge.view;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.ui.prepayfeerecharge.contract.PrepayFeeRechargeContract;
import com.wondersgroup.android.sdk.ui.prepayfeerecharge.presenter.PrepayFeeRechargePresenter;

/**
 * Created by x-sir on 2018/11/8 :)
 * Function:预交金充值页面
 */
public class PrepayFeeRechargeActivity extends MvpBaseActivity<PrepayFeeRechargeContract.IView,
        PrepayFeeRechargePresenter<PrepayFeeRechargeContract.IView>> implements PrepayFeeRechargeContract.IView {

    private static final String TAG = "PrepayFeeRechargeActivity";
    private TextView tv600;
    private TextView tv500;
    private TextView tv1000;
    private TextView tv2000;
    private EditText etInput;
    private ImageView ivBack;
    private RadioGroup rgPayType;
    private RadioButton rbAlipay;
    private RadioButton rbWeChatPay;
    private RadioButton rbUnionPay;
    private int mPayType = 1; // 默认是1，1 支付宝 2 微信 3 银行卡

    @Override
    protected PrepayFeeRechargePresenter<PrepayFeeRechargeContract.IView> createPresenter() {
        return new PrepayFeeRechargePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_prepay_fee_recharge);
        findViews();
        initData();
        initListener();
    }

    private void initListener() {
        ivBack.setOnClickListener(view -> finish());
        rgPayType.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rbAlipay) {
                mPayType = 1;
            } else if (i == R.id.rbWeChatPay) {
                mPayType = 2;
            } else if (i == R.id.rbUnionPay) {
                mPayType = 3;
            }
        });
    }

    private void initData() {
        rbAlipay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_alipay)));
        rbWeChatPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_wechat_pay)));
        rbUnionPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_union_pay)));
        // 默认选中支付宝
        rgPayType.check(R.id.rbAlipay);
    }

    private void findViews() {
        ivBack = findViewById(R.id.ivBack);
        rgPayType = findViewById(R.id.rgPayType);
        rbAlipay = findViewById(R.id.rbAlipay);
        rbWeChatPay = findViewById(R.id.rbWeChatPay);
        rbUnionPay = findViewById(R.id.rbUnionPay);
    }

    public static void actionStart(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PrepayFeeRechargeActivity.class);
        context.startActivity(intent);
    }
}
