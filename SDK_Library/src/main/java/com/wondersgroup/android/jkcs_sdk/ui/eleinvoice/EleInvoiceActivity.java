/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.eleinvoice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/11/30 :)
 * Function:电子发票页面
 */
public class EleInvoiceActivity extends AppCompatActivity {

    private static final String TAG = "EleInvoiceActivity";
    private static final String ELE_INVOICE_URL = "https://lp.axnecp.com/?zf=yhf%@";
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ele_invoice);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        findViews();
        initData();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent intent = getIntent();
        if (intent != null) {
            String payPlatTradeNo = intent.getStringExtra(IntentExtra.PAY_PLAT_TRADE_NO);
            loadEleInvoicePage(payPlatTradeNo);
        }
    }

    private void loadEleInvoicePage(String payPlatTradeNo) {
        if (!TextUtils.isEmpty(payPlatTradeNo)) {
            String url = ELE_INVOICE_URL + payPlatTradeNo;
            LogUtil.i(TAG, "url===" + url);
            webView.loadUrl(url);
        } else {
            LogUtil.eLogging(TAG, "payPlatTradeNo is null!");
        }
    }

    private void findViews() {
        webView = findViewById(R.id.webView);
    }

    public static void actionStart(Context context, String payPlatTradeNo) {
        if (context != null) {
            Intent intent = new Intent(context, EleInvoiceActivity.class);
            intent.putExtra(IntentExtra.PAY_PLAT_TRADE_NO, payPlatTradeNo);
            context.startActivity(intent);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
