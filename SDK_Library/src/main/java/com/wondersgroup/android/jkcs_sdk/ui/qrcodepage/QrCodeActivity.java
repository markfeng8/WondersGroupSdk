/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.qrcodepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.constants.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.QRCodeUtil;

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:订单二维码页面
 */
public class QrCodeActivity extends AppCompatActivity {

    private static final String TAG = "QrCodeActivity";
    private TextView tvHospitalName;
    private TextView tvTradeNo;
    private ImageView ivQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_qrcode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        findViews();
        initData();
    }

    private void findViews() {
        tvHospitalName = findViewById(R.id.tvHospitalName);
        tvTradeNo = findViewById(R.id.tvTradeNo);
        ivQrCode = findViewById(R.id.ivQrCode);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String payPlatTradeNo = intent.getStringExtra(IntentExtra.PAY_PLAT_TRADE_NO);
            String orgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            tvHospitalName.setText("医院：" + orgName);
            tvTradeNo.setText("订单号：" + payPlatTradeNo);
            if (!TextUtils.isEmpty(payPlatTradeNo)) {
                createQrCode(payPlatTradeNo);
            }
        }
    }

    private void createQrCode(String payPlatTradeNo) {
        Bitmap bitmap = QRCodeUtil.createQrCodeBitmap(payPlatTradeNo, 200, 200, null);
        if (bitmap != null) {
            ivQrCode.setImageBitmap(bitmap);
        }
    }

    public static void actionStart(Context context, String payPlatTradeNo, String orgName) {
        if (context != null) {
            Intent intent = new Intent(context, QrCodeActivity.class);
            intent.putExtra(IntentExtra.PAY_PLAT_TRADE_NO, payPlatTradeNo);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
