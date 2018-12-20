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
import android.os.Handler;
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
import com.wondersgroup.android.jkcs_sdk.ui.leavehosresult.LeaveHosResultActivity;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.PaymentUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WdCommonPayUtils;
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
    private TextView tvSocial;
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

    private String mOrgCode;
    private String mOrgName;
    private String mFeeNeedCashTotal;
    private String mPayPlatTradeNo;
    private String mYiBaoToken;
    private String feeCashTotal;
    private String feeYbTotal;
    private String feeTotal;
    private String feePrepayTotal;
    private Handler mHandler;

    /**
     * 支付类型，默认为支付宝
     */
    private int mPaymentType = 1;
    /**
     * 正式结算次数
     */
    private int mOfficeSettleTimes = 0;
    /**
     * toState 1 保存 token 2 正式结算
     */
    private static final String TO_STATE1 = "1";
    private static final String TO_STATE2 = "2";
    private String mCurrentToState;

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
        tvSocial = findViewById(R.id.tvSocial);
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
        mHandler = new Handler();

        rbAlipay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_alipay)));
        rbWeChatPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_wechat_pay)));
        rbUnionPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_union_pay)));
        // 默认选中支付宝
        rgPayType.check(R.id.rbAlipay);

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("2".equals(cardType)) {
            tvSocial.setText("自费卡号：");
        }
        tvName.setText(name);
        tvSocialNum.setText(cardNum);

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            String inHosId = intent.getStringExtra(IntentExtra.IN_HOS_ID);
            String inHosDate = intent.getStringExtra(IntentExtra.IN_HOS_DATE);
            String inHosArea = intent.getStringExtra(IntentExtra.IN_HOS_AREA);

            tvHosName.setText(mOrgName);
            tvInHosId.setText(inHosId);
            tvInHosDate.setText(inHosDate);
            tvInHosArea.setText(inHosArea);

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

    public static void actionStart(Context context, String orgCode, String orgName, String inHosId, String inHosDate, String inHosArea) {
        if (context != null) {
            Intent intent = new Intent(context, LeaveHospitalActivity.class);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            intent.putExtra(IntentExtra.IN_HOS_ID, inHosId);
            intent.putExtra(IntentExtra.IN_HOS_DATE, inHosDate);
            intent.putExtra(IntentExtra.IN_HOS_AREA, inHosArea);
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
        feeCashTotal = entity.getFeeCashTotal();
        mFeeNeedCashTotal = entity.getFeeNeedCashTotal();
        feePrepayTotal = entity.getFeePrepayTotal();
        feeTotal = entity.getFeeTotal();
        feeYbTotal = entity.getFeeYbTotal();
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
        // 发起正式结算保存 token
        mCurrentToState = TO_STATE1;
        requestCy0007();
    }

    @Override
    public void onPayParamResult(PayParamEntity body) {
        showLoading();
        // 发起万达统一支付，支付现金部分
        WdCommonPayUtils.toPay(this, body.getAppid(), body.getSubmerno(), body.getApikey(),
                mOrgName, mPayPlatTradeNo, mPaymentType, mFeeNeedCashTotal, new WdCommonPayUtils.OnPaymentResultListener() {
                    @Override
                    public void onSuccess() {
                        dismissLoading();
                        WToastUtil.show("支付成功~");
                        mCurrentToState = TO_STATE2;
                        // 支付成功后发起正式结算
                        requestCy0007();
                    }

                    @Override
                    public void onFailed(String errMsg) {
                        dismissLoading();
                        WToastUtil.show(errMsg);
                    }
                });
    }

    @Override
    public void onTryToSettleTokenResult(String token) {
        mPresenter.requestCy0006(mOrgCode, token);
    }

    /**
     * 发起正式结算
     */
    private void requestCy0007() {
        mPresenter.requestCy0007(mOrgCode, mCurrentToState, mYiBaoToken, mFeeNeedCashTotal, PaymentUtil.getPaymentChl(mPaymentType));
    }

    @Override
    public void onCy0007Result(Cy0007Entity entity) {
        if (TO_STATE1.equals(mCurrentToState)) {

            if (!TextUtils.isEmpty(mFeeNeedCashTotal) && Double.parseDouble(mFeeNeedCashTotal) > 0) {
                // 获取支付参数
                mPresenter.getPayParam(mOrgCode);
            } else {
                mCurrentToState = TO_STATE2;
                requestCy0007();
            }

        } else if (TO_STATE2.equals(mCurrentToState)) {
            if (entity != null) {
                String payState = entity.getPayState();
                if (!TextUtils.isEmpty(payState)) {
                    switch (payState) {
                        case "1": // 1、后台正在异步结算（前台等待）
                            // 重试 3 次，如果还是失败就返回首页
                            if (mOfficeSettleTimes < 3) {
                                mOfficeSettleTimes++;
                                waitingAndOnceAgain();
                            } else {
                                dismissLoading();
                                LeaveHospitalActivity.this.finish();
                            }
                            break;
                        case "2": // 2、结算完成（返回成功页面）
                            jumpToLeaveHospitalResultPager(true);
                            break;
                        case "3": // 3、结算失败（包括超时自动处理）
                            jumpToLeaveHospitalResultPager(false);
                            break;
                        default:
                            break;
                    }
                } else {
                    LogUtil.e(TAG, "payState is null!");
                }

            } else {
                jumpToLeaveHospitalResultPager(false);
            }
        }
    }

    private void waitingAndOnceAgain() {
        showLoading();
        mHandler.postDelayed(() -> {
            mCurrentToState = TO_STATE2;
            requestCy0007();
        }, 5000);
    }

    private void jumpToLeaveHospitalResultPager(boolean isSuccess) {
        LeaveHosResultActivity.actionStart(this, isSuccess, mOrgName, feeTotal,
                feeCashTotal, feeYbTotal, feePrepayTotal, mFeeNeedCashTotal);
        finish();
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
        mHandler.removeCallbacksAndMessages(null);
    }
}
