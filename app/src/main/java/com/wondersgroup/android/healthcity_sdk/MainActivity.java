package com.wondersgroup.android.healthcity_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wondersgroup.android.jkcs_sdk.ui.epsoft.EpSoftMainActivity;
import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.view.SmsCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnSendSms)
    Button btnSendSms;
    @BindView(R.id.btnEpSoft)
    Button btnEpSoft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSendSms, R.id.btnEpSoft})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnSendSms:
                intent = new Intent(MainActivity.this, SmsCodeActivity.class);
                break;
            case R.id.btnEpSoft:
                intent = new Intent(MainActivity.this, EpSoftMainActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}
