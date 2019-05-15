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
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.constants.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.widget.TitleBarLayout;

/**
 * Created by x-sir on 2018/11/30 :)
 * Function:电子发票页面
 */
public class EleInvoiceActivity extends AppCompatActivity {

    private static final String TAG = "EleInvoiceActivity";
    private static final String ELE_INVOICE_URL = "https://lp.axnecp.com/huzwd?zf=yhf@";
    private WebView webView;
    private ProgressBar progressBar;
    private TitleBarLayout titleLayout;

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
        initListener();
    }

    private void initListener() {
        titleLayout.setOnBackListener(() -> {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initData() {
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 解除数据阻止
        webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

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
        progressBar = findViewById(R.id.progressBar);
        titleLayout = findViewById(R.id.titleLayout);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
