/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehospital.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0007Entity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.jkcs_sdk.ui.leavehospital.presenter.LeaveHospitalPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;

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
    private LoadingView mLoading;
    private ConstraintLayout clBody;
    /**
     * 支付类型，默认为支付宝
     */
    private int mPaymentType = 1;
    private String mOrgCode;
    private String mOrgName;
    private String mFeeNeedCashTotal;
    private String mPayPlatTradeNo;
    private String mYiBaoToken;

    @Override
    protected LeaveHospitalPresenter<LeaveHospitalContract.IView> createPresenter() {
        return new LeaveHospitalPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_leave_hospital);
        findViews();
        initData();
        initListener();
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
        clBody = findViewById(R.id.clBody);
    }

    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .build();

        rbAlipay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_alipay)));
        rbWeChatPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_wechat_pay)));
        rbUnionPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_union_pay)));
        // 默认选中支付宝
        rgPayType.check(R.id.rbAlipay);

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        tvName.setText(name);

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            tvHosName.setText(mOrgName);
            mPresenter.getTryToSettleToken();
        }
    }

    private void initListener() {
        tvToPay.setOnClickListener(v -> {
            if (clBody.getVisibility() == View.VISIBLE) {
                mPresenter.getYiBaoToken();
            } else {
                WToastUtil.show("试结算失败！");
            }
        });
        rgPayType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbAlipay) {
                mPaymentType = 1;
            } else if (checkedId == R.id.rbWeChatPay) {
                mPaymentType = 2;
            } else if (checkedId == R.id.rbUnionPay) {
                mPaymentType = 3;
            }
        });
    }

    public static void actionStart(Context context, String orgCode, String orgName) {
        if (context != null) {
            Intent intent = new Intent(context, LeaveHospitalActivity.class);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }

    @Override
    public void showLoading() {
        if (mLoading != null) {
            mLoading.showLoadingDialog();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismissLoadingDialog();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCy0006Result(Cy0006Entity entity) {
        String feeCashTotal = entity.getFeeCashTotal();
        mFeeNeedCashTotal = entity.getFeeNeedCashTotal();
        String feePrepayTotal = entity.getFeePrepayTotal();
        String feeTotal = entity.getFeeTotal();
        String feeYbTotal = entity.getFeeYbTotal();
        mPayPlatTradeNo = entity.getPayPlatTradeNo();
        String payStartTime = entity.getPayStartTime();

        SpUtil.getInstance().save(SpKey.PAY_PLAT_TRADE_NO, mPayPlatTradeNo);
        SpUtil.getInstance().save(SpKey.PAY_START_TIME, payStartTime);

        clBody.setVisibility(View.VISIBLE);
        tvTotalFee.setText(feeTotal + "元");
        tvYiBaoFee.setText(feeYbTotal + "元");
        tvCashFee.setText(feeCashTotal + "元");
        tvPrepayFee.setText(feePrepayTotal + "元");
        tvNeedFee.setText(mFeeNeedCashTotal);
        tvWillPayFee.setText("￥" + mFeeNeedCashTotal);
    }

    @Override
    public void onYiBaoTokenResult(String token) {
        mYiBaoToken = token;
        if (!TextUtils.isEmpty(mFeeNeedCashTotal) && Long.parseLong(mFeeNeedCashTotal) > 0) {
            // 获取支付参数
            mPresenter.getPayParam(mOrgCode);
        } else {
            WToastUtil.show("0元不需要支付了！");

            // TODO: 2018/12/12 正式结算
        }
    }

    @Override
    public void onPayParamResult(PayParamEntity body) {
        // 发起万达统一支付，支付现金部分
        mPresenter.toSettleCashPay(this, body.getAppid(), body.getSubmerno(), body.getApikey(),
                mOrgName, mPayPlatTradeNo, mPaymentType, mFeeNeedCashTotal);
    }

    @Override
    public void onTryToSettleTokenResult(String token) {
        mPresenter.requestCy0006(mOrgCode, token);
    }

    @Override
    public void onCashPaySuccess() {
        mPresenter.requestCy0007(mOrgCode, "01", mYiBaoToken, mFeeNeedCashTotal, getPaymentChl());
    }

    private String getPaymentChl() {
        String channel = "";
        switch (mPaymentType) {
            case 1:
                channel = "01";
                break;
            case 2:
                channel = "02";
                break;
            case 3:
                channel = "03";
                break;
            default:
                channel = "99";
                break;
        }

        return channel;
    }

    @Override
    public void onCy0007Result(Cy0007Entity entity) {
        // TODO: 2018/12/12 跳转到成功失败页面
    }

    @Override
    public void onYiBaoOpenSuccess() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 页面销毁将保存的 mYiBaoToken 和 mYiBaoToken time 清空
        SpUtil.getInstance().save(SpKey.YIBAO_TOKEN, "");
        SpUtil.getInstance().save(SpKey.TOKEN_TIME, "");
    }
}
