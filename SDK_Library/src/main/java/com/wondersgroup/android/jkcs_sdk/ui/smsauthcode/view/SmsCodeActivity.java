package com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.contract.SmsAuthContract;
import com.wondersgroup.android.jkcs_sdk.ui.smsauthcode.presenter.SmsAuthPresenter;

// 短信验证码获取页面
public class SmsCodeActivity extends MvpBaseActivity<SmsAuthContract.IView,
        SmsAuthPresenter<SmsAuthContract.IView>> implements SmsAuthContract.IView {

    private ImageView ivBackBtn;
    private TextView tvTitleName;
    private EditText etPhone;
    private EditText etSmsCode;
    private Button btnGetSmsCode;

    @Override
    protected SmsAuthPresenter<SmsAuthContract.IView> createPresenter() {
        return new SmsAuthPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_sms_code);
        initViews();
        initListener();
    }

    private void initViews() {
        ivBackBtn = findViewById(R.id.ivBackBtn);
        tvTitleName = findViewById(R.id.tvTitleName);
        etPhone = findViewById(R.id.etPhone);
        etSmsCode = findViewById(R.id.etSmsCode);
        btnGetSmsCode = findViewById(R.id.btnGetSmsCode);
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnGetSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendSmsCode();
            }
        });
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString();
    }
}
