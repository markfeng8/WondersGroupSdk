package com.wondersgroup.android.jkcs_sdk.ui.openafterpay.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.contract.AfterPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.openafterpay.presenter.AfterPayPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;

// 开通医后付页面
public class OpenAfterPayActivity extends MvpBaseActivity<AfterPayContract.IView,
        AfterPayPresenter<AfterPayContract.IView>> implements AfterPayContract.IView {

    private EditText etPhone;
    private EditText etSmsCode;
    private Button btnOpen;
    private Button btnBackToHome;
    private TextView btnGetSmsCode;
    private ToggleButton toggleButton;
    private LinearLayout llOpenPager;
    private LinearLayout llOpenSuccess;

    @Override
    protected AfterPayPresenter<AfterPayContract.IView> createPresenter() {
        return new AfterPayPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_open_afterpay);
        initViews();
        initData();
        initListener();
    }

    private void initData() {

    }

    private void initViews() {
        etPhone = findViewById(R.id.etPhone);
        etSmsCode = findViewById(R.id.etSmsCode);
        btnGetSmsCode = findViewById(R.id.btnGetSmsCode);
        toggleButton = findViewById(R.id.toggleButton);
        btnBackToHome = findViewById(R.id.btnBackToHome);
        btnOpen = findViewById(R.id.btnOpen);
        llOpenPager = findViewById(R.id.llOpenPager);
        llOpenSuccess = findViewById(R.id.llOpenSuccess);
    }

    private void initListener() {
        btnGetSmsCode.setOnClickListener(v -> mPresenter.sendSmsCode());
        btnOpen.setOnClickListener(v -> sendOpenRequest());
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO: 2018/8/24 set button enable
            }
        });
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void sendOpenRequest() {
        String phone = etPhone.getText().toString().trim();
        String smsCode = etSmsCode.getText().toString().trim();
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(smsCode)) {
            mPresenter.openAfterPay(phone, smsCode);
        } else {
            WToastUtil.show("手机号或验证为不能为空！");
        }
    }

    @Override
    public String getPhoneNumber() {
        return etPhone.getText().toString();
    }

    @Override
    public void onAfterPayOpenSuccess() {
        SpUtil.getInstance().save(SpKey.AFTER_PAY_OPEN_SUCCESS, true);
        llOpenPager.setVisibility(View.GONE);
        llOpenSuccess.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAfterPayOpenFailed() {
        WToastUtil.show("开通失败！");
    }
}
