package com.wondersgroup.android.healthcity_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.view.SmsCodeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnSendSms)
    Button btnSendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnSendSms)
    public void onViewClicked() {
        startActivity(new Intent(MainActivity.this, SmsCodeActivity.class));
    }
}
