package com.epsoft.hzauthsdk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wondersgroup.android.jkcs_sdk.R;
import com.epsoft.hzauthsdk.utils.JavaScriptUtilsApp;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/4/27  10:53
 */

public class WebViewActivity extends AppCompatActivity {

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_test);
        url = getIntent().getStringExtra("url");
        initWeb();
    }

    public static void newInstance(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @SuppressLint("JavascriptInterface")
    private void initWeb() {
        WebView web_view = (WebView) findViewById(R.id.web);
        //清除webView缓存
        web_view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        web_view.setWebChromeClient(new WebChromeClient());
        WebSettings settings = web_view.getSettings();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(false);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(false);
        //扩大比例的缩放
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        web_view.setHorizontalScrollBarEnabled(false);//水平不显示
        web_view.setVerticalScrollBarEnabled(false); //垂直不显示
        web_view.addJavascriptInterface(new JavaScriptUtilsApp(this, web_view), "android");
        web_view.setWebChromeClient(new webPageChromeClient());
        web_view.setWebViewClient(new WebPageViewClient());
        web_view.loadUrl(url);
    }

    private class webPageChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            //这里可以获取到title,但是返回时获取不到

        }
    }

    private class WebPageViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            finish();
            super.onReceivedError(view, request, error);
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView webView, int i, String s, String s1) {
            super.onReceivedError(webView, i, s, s1);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }

}
