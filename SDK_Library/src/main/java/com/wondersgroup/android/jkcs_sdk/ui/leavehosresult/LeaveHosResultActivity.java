/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.leavehosresult;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.ui.inhospitalrecord.view.InHospitalRecordActivity;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.QRCodeUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

/**
 * Created by x-sir on 2018/12/6 :)
 * Function:出院结算结果页面
 */
public class LeaveHosResultActivity extends AppCompatActivity {

    private static final String TAG = "LeaveHosResultActivity";
    private TextView tvResult;
    private TextView tvTreatName;
    private TextView tvSocialNum;
    private TextView tvHospitalName;
    private TextView tvBillDate;
    private TextView tvBillNo;
    private TextView tvInHosTotalFee;
    private TextView tvYiBaoFee;
    private TextView tvCashFee;
    private TextView tvPrepayFee;
    private TextView tvWillFee;
    private TextView tvInHosHis;
    private ImageView ivQrCode;

    private String mOrgName = "";
    private String mFeeTotal = "";
    private String mFeeCashTotal = "";
    private String mFeeYbTotal = "";
    private String mFeePrepayTotal;
    private String mFeeNeedCashTotal;
    /**
     * 是否支付成功
     */
    private boolean mIsSuccess = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_hos_result);
        findViews();
        initData();
        initListener();
    }

    private void initData() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");
        String payStartTime = SpUtil.getInstance().getString(SpKey.PAY_START_TIME, "");

        Intent intent = getIntent();
        if (intent != null) {
            mIsSuccess = intent.getBooleanExtra(IntentExtra.IS_SUCCESS, false);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            mFeeTotal = intent.getStringExtra(IntentExtra.FEE_TOTAL);
            mFeeCashTotal = intent.getStringExtra(IntentExtra.FEE_CASH_TOTAL);
            mFeeYbTotal = intent.getStringExtra(IntentExtra.FEE_YB_TOTAL);
            mFeePrepayTotal = intent.getStringExtra(IntentExtra.FEE_PREPAY_TOTAL);
            mFeeNeedCashTotal = intent.getStringExtra(IntentExtra.FEE_NEEDCASH_TOTAL);
        }

        if (!mIsSuccess) {
            tvResult.setText(getString(R.string.wonders_self_failed1));
        }

        tvTreatName.setText(name);
        tvSocialNum.setText(cardNum);
        tvHospitalName.setText(mOrgName);
        tvBillDate.setText(payStartTime);
        tvBillNo.setText(payPlatTradeNo);
        tvInHosTotalFee.setText(mFeeTotal);
        tvYiBaoFee.setText(mFeeYbTotal);
        tvCashFee.setText(mFeeCashTotal);
        tvPrepayFee.setText(mFeePrepayTotal);
        tvWillFee.setText(mFeeNeedCashTotal);

        if (!TextUtils.isEmpty(payPlatTradeNo)) {
            createQrCode(payPlatTradeNo);
        }
    }

    private void createQrCode(String payPlatTradeNo) {
        Bitmap bitmap = QRCodeUtil.createQrCodeBitmap(payPlatTradeNo, 200, 200, null);
        if (bitmap != null) {
            ivQrCode.setImageBitmap(bitmap);
        }
    }

    private void initListener() {
        tvInHosHis.setOnClickListener(v -> {
            InHospitalRecordActivity.actionStart(LeaveHosResultActivity.this);
            finish();
        });
    }

    private void findViews() {
        tvResult = findViewById(R.id.tvResult);
        tvTreatName = findViewById(R.id.tvTreatName);
        tvSocialNum = findViewById(R.id.tvSocialNum);
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvBillDate = findViewById(R.id.tvBillDate);
        tvBillNo = findViewById(R.id.tvBillNo);
        tvInHosTotalFee = findViewById(R.id.tvInHosTotalFee);
        tvYiBaoFee = findViewById(R.id.tvYiBaoFee);
        tvCashFee = findViewById(R.id.tvCashFee);
        tvPrepayFee = findViewById(R.id.tvPrepayFee);
        tvWillFee = findViewById(R.id.tvWillFee);
        tvInHosHis = findViewById(R.id.tvInHosHis);
        ivQrCode = findViewById(R.id.ivQrCode);
    }

    public static void actionStart(Context context, boolean isSuccess, String orgName, String feeTotal,
                                   String feeCashTotal, String feeYbTotal, String feePrepayTotal, String feeNeedCashTotal) {
        if (context != null) {
            Intent intent = new Intent(context, LeaveHosResultActivity.class);
            intent.putExtra(IntentExtra.IS_SUCCESS, isSuccess);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            intent.putExtra(IntentExtra.FEE_TOTAL, feeTotal);
            intent.putExtra(IntentExtra.FEE_CASH_TOTAL, feeCashTotal);
            intent.putExtra(IntentExtra.FEE_YB_TOTAL, feeYbTotal);
            intent.putExtra(IntentExtra.FEE_PREPAY_TOTAL, feePrepayTotal);
            intent.putExtra(IntentExtra.FEE_NEEDCASH_TOTAL, feeNeedCashTotal);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
