package com.epsoft.hzauthsdk.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.wondersgroup.android.jkcs_sdk.R;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/5/10  21:41
 */

public class ScannerActivity extends AppCompatActivity implements OnScannerCompletionListener {

    private ScannerView zxing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_scanner);
        initZXing();
    }


    private void initZXing() {
        zxing = (ScannerView) findViewById(R.id.zxing);
        zxing.setOnScannerCompletionListener(this);
        zxing.setDrawText("请对准二维码/条码,耐心等待", true);
        zxing.setScanMode(BarcodeFormat.values());
    }

    @Override
    protected void onResume() {
        if (zxing!=null){
            zxing.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (zxing!=null){
            zxing.onPause();
        }
        super.onPause();
    }
    

    @Override
    public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        String text = rawResult.getText();
        if (!text.startsWith("http://")&&!text.startsWith("https://")){
            Toast.makeText(this,"你扫描的不是一个正确的账单条码!", Toast.LENGTH_SHORT).show();
        }else {
            WebViewActivity.newInstance(this,text);
            finish();
        }
    }
}
