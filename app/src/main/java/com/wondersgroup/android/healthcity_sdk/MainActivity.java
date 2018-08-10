package com.wondersgroup.android.healthcity_sdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.ui.epsoft.EpSoftMainActivity;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view.OpenAfterPayActivity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.PersonalPayActivity;
import com.wondersgroup.android.jkcs_sdk.utils.ActivityUtil;

import java.util.HashMap;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnSendSms, R.id.btnEpSoft, R.id.btnWdPay, R.id.btnPersonalPay, R.id.btnAfterHome,
            R.id.btnPaymentDetail})
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
                startAfterPayHomePage();
                break;
            case R.id.btnPaymentDetail:
                intent = new Intent(MainActivity.this, PaymentDetailsActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 将所有需要传递的参数（所有参数必须传）放入 HashMap 中，
     * 不要修改 Key，只修改 value 即可。
     */
    private void startAfterPayHomePage() {
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.NAME, "胡汉三"); // 姓名
        map.put(MapKey.PHONE, "17724801294"); // 手机号
        map.put(MapKey.CARD_NO, "A02543853"); // 社保卡号
        map.put(MapKey.ID_NO, "330501198408250211"); // 身份证号码
        map.put(MapKey.HOME_ADDRESS, "ShangHai"); // 家庭地址
        ActivityUtil.startAfterPayHome(MainActivity.this, map);
    }

}
