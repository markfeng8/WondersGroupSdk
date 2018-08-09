package com.wondersgroup.android.healthcity_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.AfterPayHomeActivity;
import com.wondersgroup.android.jkcs_sdk.ui.epsoft.EpSoftMainActivity;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view.OpenAfterPayActivity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.PayRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.PersonalPayActivity;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.view.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cashier.SetWorkKeyActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnSendSms)
    Button btnSendSms;
    @BindView(R.id.btnEpSoft)
    Button btnEpSoft;
    @BindView(R.id.btnWdPay)
    Button btnWdPay;
    @BindView(R.id.btnPersonalPay)
    Button btnPersonalPay;
    @BindView(R.id.btnAfterHome)
    Button btnAfterHome;
    @BindView(R.id.btnPaymentDetail)
    Button btnPaymentDetail;
    @BindView(R.id.btnPayRecord)
    Button btnPayRecord;
    @BindView(R.id.btnSettings)
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSendSms, R.id.btnEpSoft, R.id.btnWdPay, R.id.btnPersonalPay, R.id.btnAfterHome,
            R.id.btnPaymentDetail, R.id.btnPayRecord, R.id.btnSettings})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnSendSms:
                intent = new Intent(MainActivity.this, OpenAfterPayActivity.class);
                break;
            case R.id.btnEpSoft:
                intent = new Intent(MainActivity.this, EpSoftMainActivity.class);
                break;
            case R.id.btnWdPay:
                intent = new Intent(MainActivity.this, SetWorkKeyActivity.class);
                break;
            case R.id.btnPersonalPay:
                intent = new Intent(MainActivity.this, PersonalPayActivity.class);
                break;
            case R.id.btnAfterHome:
                intent = new Intent(MainActivity.this, AfterPayHomeActivity.class);
                break;
            case R.id.btnPaymentDetail:
                intent = new Intent(MainActivity.this, PaymentDetailsActivity.class);
                break;
            case R.id.btnPayRecord:
                intent = new Intent(MainActivity.this, PayRecordActivity.class);
                break;
            case R.id.btnSettings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}
