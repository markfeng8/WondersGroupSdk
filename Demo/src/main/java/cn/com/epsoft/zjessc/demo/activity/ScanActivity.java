package cn.com.epsoft.zjessc.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.mylhyl.zxing.scanner.ScannerView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import cn.com.epsoft.zjessc.demo.BuildConfig;
import cn.com.epsoft.zjessc.demo.R;
import cn.com.epsoft.zjessc.demo.base.BaseActivity;

/**
 * @author 启研
 * @created at 2017/11/22 16:48
 */
public class ScanActivity extends BaseActivity {

    ScannerView mScannerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//设置高亮
        this.setContentView(R.layout.act_scan);
        toolbar = findViewById(R.id.toolbar);
        mScannerView = findViewById(R.id.scannerView);
        toolbar.setTitle(BuildConfig.DEBUG ? "测试环境" : "正式环境");
        mScannerView.setOnScannerCompletionListener((rawResult, parsedResult, barcode) -> {
            if (rawResult != null && !TextUtils.isEmpty(rawResult.getText())) {
                String text = rawResult.getText();
                Intent intent = new Intent();
                intent.putExtra("data", text);
                setResult(Activity.RESULT_OK, intent);
                onBackPressed();
            }
        });
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.CAMERA)
                .onGranted(permissions -> {
                    mScannerView.onResume();
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    onBackPressed();
                    Toast.makeText(getBaseContext(), "您拒绝了应用对相机权限的开启，请到系统设置中开启后再使用此功能！", Toast.LENGTH_LONG)
                            .show();
                })
                .start();
    }


    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

}
