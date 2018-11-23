package com.wondersgroup.android.jkcs_sdk.ui.paymentresult.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.ui.paymentrecord.view.FeeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentresult.contract.PaymentResultContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentresult.presenter.PaymentResultPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.QRCodeUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.PayResultLayout;

/**
 * Created by x-sir on 2018/9/17 :)
 * Function:支付结果页面
 */
public class PaymentResultActivity extends MvpBaseActivity<PaymentResultContract.IView,
        PaymentResultPresenter<PaymentResultContract.IView>> implements PaymentResultContract.IView {

    private static final String TAG = "PaymentResultActivity";
    private TextView tvPayDetails;
    private TextView tvCompleteTotal;
    private TextView tvCompletePersonal;
    private TextView tvCompleteYiBao;
    private TextView tvFailed1;
    private TextView tvFailed2;
    private ImageView ivQrCode;
    private Button btnBackToHome;
    private LinearLayout llPaySuccess;
    private LinearLayout llPayFailed;
    private LinearLayout llContainer1;
    private LinearLayout llContainer2;
    private LinearLayout llYiBaoLayout;
    private LoadingView mLoading;
    private String mOrgCode = "";
    private String mOrgName = "";
    private String mFeeTotal = "";
    private String mFeeCashTotal = "";
    private String mFeeYbTotal = "";
    private boolean mIsSuccess = false; // 是否支付成功

    @Override
    protected PaymentResultPresenter<PaymentResultContract.IView> createPresenter() {
        return new PaymentResultPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_personal_pay);
        findViews();
        initData();
        initListener();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .build();

        Intent intent = getIntent();
        if (intent != null) {
            mIsSuccess = intent.getBooleanExtra(IntentExtra.IS_SUCCESS, false);
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            mFeeTotal = intent.getStringExtra(IntentExtra.FEE_TOTAL);
            mFeeCashTotal = intent.getStringExtra(IntentExtra.FEE_CASH_TOTAL);
            mFeeYbTotal = intent.getStringExtra(IntentExtra.FEE_YB_TOTAL);
        }

        // 设置不管是全部完成支付还是全部未完成支付时需要显示的数据
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        String lockStartTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");

        PayResultLayout payResultLayout = new PayResultLayout(this);
        payResultLayout.setTreatName(name);
        payResultLayout.setSocialNum("2".equals(cardType) ? null : cardNum);
        payResultLayout.setHospitalName(mOrgName);
        payResultLayout.setBillDate(lockStartTime);
        payResultLayout.setBillNo(payPlatTradeNo);

        setPaymentView(mIsSuccess);

        // 判断是否已经支付成功
        if (mIsSuccess) {
            tvCompleteTotal.setText(mFeeTotal);
            tvCompletePersonal.setText(mFeeCashTotal);
            // 如果是门诊才需要显示医保金额，如果是自费卡不需要显示
            if ("2".equals(cardType)) {
                llYiBaoLayout.setVisibility(View.GONE);
            } else if ("0".equals(cardType)) {
                llYiBaoLayout.setVisibility(View.VISIBLE);
                tvCompleteYiBao.setText(mFeeYbTotal);
            }
            llContainer1.addView(payResultLayout);
            if (!TextUtils.isEmpty(payPlatTradeNo)) {
                createQrCode(payPlatTradeNo);
            }
        } else {
            llContainer2.addView(payResultLayout);
            if ("2".equals(cardType)) {
                tvFailed1.setText(getString(R.string.wonders_self_failed1));
                tvFailed2.setText(getString(R.string.wonders_self_failed2));
            } else if ("0".equals(cardType)) {
                tvFailed1.setText(getString(R.string.wonders_settle_failed1));
                tvFailed2.setText(getString(R.string.wonders_settle_failed2));
            }
        }
    }

    private void findViews() {
        tvCompleteTotal = findViewById(R.id.tvCompleteTotal);
        tvCompletePersonal = findViewById(R.id.tvCompletePersonal);
        tvCompleteYiBao = findViewById(R.id.tvCompleteYiBao);
        btnBackToHome = findViewById(R.id.btnBackToHome);
        tvPayDetails = findViewById(R.id.tvPayDetails);
        llPaySuccess = findViewById(R.id.llPaySuccess);
        llPayFailed = findViewById(R.id.llPayFailed);
        llContainer1 = findViewById(R.id.llContainer1);
        llContainer2 = findViewById(R.id.llContainer2);
        ivQrCode = findViewById(R.id.ivQrCode);
        llYiBaoLayout = findViewById(R.id.llYiBaoLayout);
        tvFailed1 = findViewById(R.id.tvFailed1);
        tvFailed2 = findViewById(R.id.tvFailed2);
    }

    private void initListener() {
        btnBackToHome.setOnClickListener(v -> finish());
        tvPayDetails.setOnClickListener(v -> {
            FeeRecordActivity.actionStart(PaymentResultActivity.this);
            finish();
        });
    }

    private void createQrCode(String payPlatTradeNo) {
        Bitmap bitmap = QRCodeUtil.createQrCodeBitmap(payPlatTradeNo, 200, 200, null);
        if (bitmap != null) {
            ivQrCode.setImageBitmap(bitmap);
        }
    }

    /**
     * 页面跳转的 action
     *
     * @param context      上下文
     * @param isSuccess    是否支付成功
     * @param isFinish     是否需要销毁跳转前的页面
     * @param orgName      机构名称
     * @param orgCode      机构编码
     * @param feeTotal     缴费总额
     * @param feeCashTotal 现金部分金额
     * @param feeYbTotal   医保部分金额
     */
    public static void actionStart(Context context, boolean isSuccess, boolean isFinish, String orgName,
                                   String orgCode, String feeTotal, String feeCashTotal, String feeYbTotal) {
        if (context != null) {
            Intent intent = new Intent(context, PaymentResultActivity.class);
            intent.putExtra(IntentExtra.IS_SUCCESS, isSuccess);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.FEE_TOTAL, feeTotal);
            intent.putExtra(IntentExtra.FEE_CASH_TOTAL, feeCashTotal);
            intent.putExtra(IntentExtra.FEE_YB_TOTAL, feeYbTotal);
            context.startActivity(intent);
            if (isFinish) {
                ((Activity) context).finish();
            }
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }

    /**
     * 设置支付成功或者失败的视图
     */
    private void setPaymentView(boolean isSuccess) {
        if (isSuccess) {
            llPaySuccess.setVisibility(View.VISIBLE);
            llPayFailed.setVisibility(View.GONE);
        } else {
            llPaySuccess.setVisibility(View.GONE);
            llPayFailed.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoading != null) {
            mLoading.dispose();
        }
    }
}
