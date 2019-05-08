package cn.com.epsoft.zjessc.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.pingan.ai.request.biap.net.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.epsoft.zjessc.ZjEsscSDK;
import cn.com.epsoft.zjessc.callback.ResultType;
import cn.com.epsoft.zjessc.callback.SdkCallBack;
import cn.com.epsoft.zjessc.demo.App;
import cn.com.epsoft.zjessc.demo.R;
import cn.com.epsoft.zjessc.demo.base.BaseActivity;
import cn.com.epsoft.zjessc.demo.tools.RequestTool;
import cn.com.epsoft.zjessc.tools.ZjBiap;
import cn.com.epsoft.zjessc.tools.ZjEsscException;

/**
 * @author 启研
 * @created at 2018/11/15 14:58
 */
public class UserInfoActivity extends BaseActivity {

    private static final String TAG = "UserInfoActivity";
    private final int REQUEST_CODE_SCAN = 1;
    EditText idcardEt, nameEt, areaEt, busiSeqEt, signNoEt, phoneEt, historyFlagEt;

    String url;
    boolean hide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_info);
        ((Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ((Toolbar) findViewById(R.id.toolbar)).setTitle(getIntent().getStringExtra("title"));
        url = getIntent().getStringExtra("url");
        hide = getIntent().getBooleanExtra("hide", false);
        idcardEt = findViewById(R.id.idcardEt);
        nameEt = findViewById(R.id.nameEt);
        areaEt = findViewById(R.id.areaEt);
        busiSeqEt = findViewById(R.id.busiSeqEt);
        signNoEt = findViewById(R.id.signNoEt);
        phoneEt = findViewById(R.id.phoneEt);
        historyFlagEt = findViewById(R.id.historyFlagEt);
        findViewById(R.id.startBtn).setOnClickListener(v -> startSdk(v));

        phoneEt.setVisibility(hide ? View.GONE : View.VISIBLE);
        busiSeqEt.setVisibility(hide ? View.GONE : View.VISIBLE);
        signNoEt.setVisibility(hide ? View.GONE : View.VISIBLE);
        historyFlagEt.setVisibility(hide ? View.GONE : View.VISIBLE);

        initUser();
    }

    private void initUser() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                idcardEt.setText(App.getUser().idcard);
                nameEt.setText(App.getUser().name);
                areaEt.setText(App.getUser().area);
                if (!hide) {
                    signNoEt.setText(App.getUser().signNo);
                }
            }
        });
    }


    public void startSdk(View view) {
        // 身份证号码
        final String idcard = idcardEt.getText().toString();
        // 姓名
        final String name = nameEt.getText().toString();
        // 地区
        final String area = areaEt.getText().toString();
        final String signNo = signNoEt.getText().toString();

        if (TextUtils.isEmpty(idcard) || TextUtils.isEmpty(name)) {
            Toast.makeText(getBaseContext(), "请填入用户信息", Toast.LENGTH_LONG).show();
        } else if ((ZjBiap.getInstance().getQrCode().equals(url) || ZjBiap.getInstance().getValidPwd()
                .equals(url) || ZjBiap.getInstance().getFaceValidate().equals(url)) && (
                TextUtils.isEmpty(area) || TextUtils.isEmpty(signNo))) {
            Toast.makeText(getBaseContext(), "该功能需要填入发卡地和签发号", Toast.LENGTH_LONG).show();
        } else {
            App.getUser().idcard = idcard;
            App.getUser().name = name;
            App.getUser().area = area;
            App.getUser().signNo = signNo;

            if (ZjBiap.getInstance().getScanValidateUrl().equals(url)) {
                startActivityForResult(new Intent(getBaseContext(), ScanActivity.class), REQUEST_CODE_SCAN);
            } else {

                showProgress(true);
                // 签名需集成方App实现，该测试接口不稳定
                RequestTool.getSign(this, idcard, name, area,
                        signNo, busiSeqEt.getText().toString(), phoneEt.getText().toString(),
                        historyFlagEt.getText().toString(), "", "", s -> {
                            Log.i(TAG, "获取到的签名串为：" + s);
                            // 获取到签名启动SDK
                            startSdk(idcard, name, s);
                        });
            }
        }
    }

    /**
     * 启动SDK
     *
     * @param idcard 身份证
     * @param name   姓名
     * @param s      签名
     */
    private void startSdk(final String idcard, final String name, String s) {
        ZjEsscSDK.startSdk(UserInfoActivity.this, idcard, name, url, s, new SdkCallBack() {
            @Override
            public void onLoading(boolean show) {
                showProgress(show);
            }

            @Override
            public void onResult(@ResultType int type, String data) {
                if (type == ResultType.ACTION) {
                    handleAction(data);
                } else if (type == ResultType.SCENE) {
                    handleScene(data);
                }
            }

            @Override
            public void onError(String code, ZjEsscException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 签发类回执
     *
     * @_actiontype=001 表示 一级签发，
     * @_actionType=002 密码重置 完成
     * @_actiontype=003 表示解除关联，
     * @_actiontype=004 部平台密码校验完成
     * @_actiontype=005 表示开通缴费结算功能
     * @_actiontype=006 表示提供给SDK用户信息，不需要处理。
     */
    private void handleAction(String data) {
        LogUtils.i("签发回调" + data);
        try {
            JSONObject jo = new JSONObject(data);
            if (jo.has("actionType") && "006".equals(jo.getString("actionType"))) {
                App.getUser().area = jo.getString("aab301");
                App.getUser().signNo = jo.getString("signNo");
            } else if (jo.has("actionType") && "005".equals(jo.getString("actionType"))) {
                App.getUser().area = jo.getString("aab301");
                App.getUser().signNo = jo.getString("signNo");
            } else if (jo.has("actionType") && "003".equals(jo.getString("actionType"))) {
                App.getUser().area = "";
                App.getUser().signNo = "";
            }
            Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initUser();
    }

    /**
     * 独立服务回执
     *
     * @_sceneType=004 密码验证
     * @_sceneType=005 短信验证
     * @_sceneType=008 人脸识别验证
     */
    private void handleScene(String data) {
        LogUtils.i("独立服务回调" + data);
        try {
            JSONObject jo = new JSONObject(data);
            if (jo.has("sceneType") && "004".equals(jo.getString("sceneType"))) {
                ZjEsscSDK.closeSDK();
            } else if (jo.has("sceneType") && "008".equals(jo.getString("sceneType"))) {
                ZjEsscSDK.closeSDK();
            } else if (jo.has("sceneType") && "005".equals(jo.getString("sceneType"))) {
                ZjEsscSDK.closeSDK();
            }
            Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == Activity.RESULT_OK) {
            String qrCode = data.getStringExtra("data");
            showProgress(true);
            //签名需集成方App实现，该测试接口不稳定
            RequestTool.getSign(this, idcardEt.getText().toString(), nameEt.getText().toString(),
                    areaEt.getText().toString(),
                    signNoEt.getText().toString(), busiSeqEt.getText().toString(),
                    phoneEt.getText().toString(),
                    historyFlagEt.getText().toString(), "", qrCode, s -> {
                        //获取到签名启动SDK
                        startSdk(idcardEt.getText().toString(), nameEt.getText().toString(), s);
                    });
        }
    }
}
