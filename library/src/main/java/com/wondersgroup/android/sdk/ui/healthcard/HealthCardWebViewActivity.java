/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.healthcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.utils.StatusBarUtils;
import com.wondersgroup.android.sdk.widget.TitleBarLayout;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * Created by x-sir on 2019/10/18 :)
 * Function:电子健康卡页面
 */
public class HealthCardWebViewActivity extends AppCompatActivity {

    private static final String TAG = "HealthCardActivity";
    private static final String URL = "url";
    private static final String URL_H5 = "http://115.238.228.2:8000/hcbmp/management/h5/index?appId=050b1e9c8dd34d478518a9eafffa99ba&cipherText=";
    /**
     * 公钥截取前面16位作为报文加密密钥 dd80ec7b658a4ea660ae9bc7315fb227
     */
    private static final String PUBLIC_KEY = "dd80ec7b658a4ea6";
    private ProgressBar progressBar;
    private WebView webView;
    private TitleBarLayout titleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_card);
        StatusBarUtils.tint(this);
        titleLayout = findViewById(R.id.titleLayout);
        progressBar = findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);
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
            String url = intent.getStringExtra(URL);
            Log.i(TAG, "url===" + url);
            webView.loadUrl(url);
        }
    }

    public static void actionStart(Context context, String name, String idNum, String phone) {
        String requestUrl = getRequestUrl(name, idNum, phone);
        Log.i(TAG, "requestUrl===" + requestUrl);
        Intent intent = new Intent(context, HealthCardWebViewActivity.class);
        intent.putExtra(URL, requestUrl);
        context.startActivity(intent);
    }

    private static String getRequestUrl(String name, String idNum, String phone) {
        return URL_H5 + getEncryptionResult(name, idNum, phone);
    }

    private static String getEncryptionResult(String name, String idNum, String phone) {
        String content = getParameters(name, idNum, phone);
        Log.i(TAG, "content===" + content);
        SymmetricCrypto sm4 = SmUtil.sm4(PUBLIC_KEY.getBytes());
        return sm4.encryptHex(content);
    }

    private static String getParameters(String name, String idNum, String phone) {
        return "openId=" + idNum + "&xm=" + name + "&sfzh=" + idNum + "&sjhm=" + phone + "&timestamp=" + getTimestamp();
    }

    private static long getTimestamp() {
        return System.currentTimeMillis();
    }
}
