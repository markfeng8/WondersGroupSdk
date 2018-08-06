package com.wondersgroup.android.jkcs_sdk.ui.epsoft;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.epsoft.hzauthsdk.pub.QueryOpenStatusArgs;
import com.epsoft.hzauthsdk.pub.TokenArgs;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.utils.CheckPermissionUtils;
import com.wondersgroup.android.jkcs_sdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.utils.TestArgs;
import com.wondersgroup.android.jkcs_sdk.utils.ToastUtils;

public class EpSoftMainActivity extends AppCompatActivity {

    private EditText etMobile;
    private Button scannerH5, btnInit, btnOpen, btnClose, btnQueryOpen, btnGetToken,
            btnVerToken, btnGetIdcode, btnPres, btnRegister, btnHospital, btnQueryPay;
    private CheckPermissionUtils checkPermissionUtils;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epsoft_main);
        findViews();
        initListener();
        initSDK();
        checkPermissionUtils = new CheckPermissionUtils(this);
    }

    private void findViews() {
        etMobile = findViewById(R.id.et_mobile);
        scannerH5 = findViewById(R.id.scanner_h5);
        btnInit = findViewById(R.id.bt_init);
        btnOpen = findViewById(R.id.bt_open);
        btnClose = findViewById(R.id.bt_close);
        btnQueryOpen = findViewById(R.id.bt_query_open);
        btnGetToken = findViewById(R.id.bt_get_token);
        btnVerToken = findViewById(R.id.bt_ver_token);
        btnGetIdcode = findViewById(R.id.bt_get_idcode);
        btnPres = findViewById(R.id.bt_pres);
        btnRegister = findViewById(R.id.bt_registr);
        btnHospital = findViewById(R.id.bt_hospital);
        btnQueryPay = findViewById(R.id.bt_query_pay);
    }

    private void initListener() {
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // sdk实人认证测试 -> 开通
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etMobile.getText().toString().trim();
                if (!TextUtils.isEmpty(trim) && trim.length() == 11) {
                    AuthCall.businessProcess(EpSoftMainActivity.this, MakeArgsFactory.getBussArgs(trim), new AuthCall.CallBackListener() {
                        @Override
                        public void callBack(String result) {
                            ToastUtils.showToast(EpSoftMainActivity.this, result);
                        }
                    });
                } else {
                    Toast.makeText(EpSoftMainActivity.this, "请输入接受验证码的手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 修改重置
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = etMobile.getText().toString().trim();
                if (!TextUtils.isEmpty(trim) && trim.length() == 11) {
                    AuthCall.changePassword(EpSoftMainActivity.this, MakeArgsFactory.getChangeArgs(), new AuthCall.CallBackListener() {
                        @Override
                        public void callBack(String result) {
                            ToastUtils.showToast(EpSoftMainActivity.this, result);
                        }
                    });
                } else {
                    Toast.makeText(EpSoftMainActivity.this, "请输入接受验证码的手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //查询开通
        btnQueryOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryOpenStatusArgs build = new QueryOpenStatusArgs.Builder()
                        .setAuthChannel("2")
                        .setCardNum(TestArgs.cardNum)
                        .build();
                AuthCall.queryOpenStatus(EpSoftMainActivity.this, build, new AuthCall.CallBackListener() {
                    @Override
                    public void callBack(String result) {
                        ToastUtils.showToast(EpSoftMainActivity.this, result);
                    }
                });
            }
        });
        // 获取token
        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenArgs tokenArgs = new TokenArgs.Builder()
                        .setAuthChannel("2")
                        .setCardNum(TestArgs.cardNum)
                        .setType("2")
                        .build();

                AuthCall.getToken(EpSoftMainActivity.this, tokenArgs, new AuthCall.CallBackListener() {
                    @Override
                    public void callBack(String result) {
                        ToastUtils.showToast(EpSoftMainActivity.this, result);
                    }
                });
            }
        });
        // 验证token
        btnVerToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnGetIdcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TokenArgs tokenArgs2 = new TokenArgs.Builder()
                        .setAuthChannel("2")
                        .setCardNum(TestArgs.cardNum)
                        .setType("1")
                        .build();

                AuthCall.getToken(EpSoftMainActivity.this, tokenArgs2, new AuthCall.CallBackListener() {
                    @Override
                    public void callBack(String result) {
                        ToastUtils.showToast(EpSoftMainActivity.this, result);
                    }
                });
            }
        });
    }

    /**
     * 初始化
     */
    private void initSDK() {
        //实人认证
        AuthCall.initSDK(this, "6151490102", new AuthCall.CallBackListener() {
            @Override
            public void callBack(String result) {

            }
        });
    }

}
