package com.wondersgroup.android.jkcs_sdk.ui.settingspage.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract.SettingsContract;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.presenter.SettingsPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.WonderToastUtil;

import java.util.HashMap;

// 设置页面
public class SettingsActivity extends MvpBaseActivity<SettingsContract.IView,
        SettingsPresenter<SettingsContract.IView>> implements SettingsContract.IView {

    private TextView tvName;
    private TextView tvIcNum;
    private TextView tvSocialNum;
    private TextView tvSignDate;
    private TextView tvPhone;
    private TextView tvAfterPayState;
    private TextView tvMobilePayState;
    private TextView tvLookRule;
    private TextView tvUpdatePayPwd;
    private TextView tvTermination;
    private TextView tvTitleName;
    private ImageView ivBackBtn;
    private ImageView ivEditPhone;

    private PopupWindow popupWindow;
    private View popupView;
    private String mName;
    private String mIdNo;
    private String mCardNo;
    private String mPhone;
    private String mIdenCode;

    @Override
    protected SettingsPresenter<SettingsContract.IView> createPresenter() {
        return new SettingsPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_settings);
        findViews();
        initData();
        initListener();
    }

    @Override
    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_settings));
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableHashMap sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                AfterPayStateEntity afterPayEntity = (AfterPayStateEntity) bundle.get(IntentExtra.SERIALIZABLE_AFTERPAY_ENTITY);
                MobilePayEntity mobilePayEntity = (MobilePayEntity) bundle.get(IntentExtra.SERIALIZABLE_MOBILEPAY_ENTITY);

                if (sMap != null) {
                    HashMap<String, String> map = sMap.getMap();
                    mName = map.get(MapKey.NAME);
                    mIdNo = map.get(MapKey.ID_NO);
                    mCardNo = map.get(MapKey.CARD_NO);
                    mPhone = map.get(MapKey.PHONE);
                    tvName.setText(mName);
                    tvIcNum.setText(mIdNo);
                    tvSocialNum.setText(mCardNo);
                    tvPhone.setText(mPhone);
                }

                if (afterPayEntity != null) {
                    String state = "";
                    String signingStatus = afterPayEntity.getSigning_status();
                    if ("00".equals(signingStatus)) { // 00未签约（医后付状态给NULL）
                        state = "未签约";
                    } else if ("01".equals(signingStatus)) { // 01已签约
                        /*
                         * 1：正常（缴清或未使用医后付服务）2：欠费(医后付后有欠费的概要信
                         */
                        String paymentStatus = afterPayEntity.getOne_payment_status();
                        if ("1".equals(paymentStatus)) {
                            state = "正常";
                        } else if ("2".equals(paymentStatus)) {
                            state = "欠费";
                        }
                    } else if ("02".equals(signingStatus)) { // 02 其他
                        state = "其他";
                    }
                    tvAfterPayState.setText(state);
                }

                if (mobilePayEntity != null) {
                    String mobPayStatus = mobilePayEntity.getMobile_pay_status();
                    if ("00".equals(mobPayStatus)) { // 00 未签约
                        tvMobilePayState.setText("未签约");
                    } else if ("01".equals(mobPayStatus)) { // 01已签约
                        tvMobilePayState.setText("已签约");
                    } else if ("02".equals(mobPayStatus)) { // 02 其他
                        tvMobilePayState.setText("其他");
                    }
                }
            }
        }
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.this.finish();
            }
        });
        ivEditPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdatePhoneDialog();
                BrightnessManager.lightoff(SettingsActivity.this);
            }
        });
    }

    private void findViews() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvIcNum = (TextView) findViewById(R.id.tvIcNum);
        tvSocialNum = (TextView) findViewById(R.id.tvSocialNum);
        tvSignDate = (TextView) findViewById(R.id.tvSignDate);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        ivEditPhone = (ImageView) findViewById(R.id.ivEditPhone);
        tvAfterPayState = (TextView) findViewById(R.id.tvAfterPayState);
        tvMobilePayState = (TextView) findViewById(R.id.tvMobilePayState);
        tvLookRule = (TextView) findViewById(R.id.tvLookRule);
        tvTitleName = findViewById(R.id.tvTitleName);
        ivBackBtn = findViewById(R.id.ivBackBtn);
        tvUpdatePayPwd = findViewById(R.id.tvUpdatePayPwd);
        tvTermination = findViewById(R.id.tvTermination);
    }

    private void showUpdatePhoneDialog() {
        if (popupWindow == null) {
            popupView = View.inflate(SettingsActivity.this, R.layout.popupwindow_update_phone, null);
            popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    BrightnessManager.lighton(SettingsActivity.this);
                }
            });
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);

            final EditText etPhone = (EditText) popupView.findViewById(R.id.etPhone);
            final EditText etVerifyCode = (EditText) popupView.findViewById(R.id.etVerifyCode);
            TextView tvOriginalPhone = (TextView) popupView.findViewById(R.id.tvOriginalPhone);
            String phoneText = getString(R.string.wonders_original_phone) + mPhone;
            tvOriginalPhone.setText(phoneText);

            // 获取验证码
            popupView.findViewById(R.id.tvGetSmsCode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = etPhone.getText().toString();
                    mPresenter.sendVerifyCode(phone);
                }
            });

            // 关闭
            popupView.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    BrightnessManager.lighton(SettingsActivity.this);
                }
            });

            // 开通
            popupView.findViewById(R.id.tvOpen).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = etPhone.getText().toString();
                    String verifyCode = etVerifyCode.getText().toString();
                    if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(verifyCode)) {
                        HashMap<String, String> param = new HashMap<>();
                        param.put(MapKey.NAME, mName);
                        param.put(MapKey.PHONE, phone);
                        param.put(MapKey.ID_NO, mIdNo);
                        param.put(MapKey.CARD_NO, mCardNo);
                        param.put(MapKey.IDEN_CODE, verifyCode);
                        mPresenter.sendOpenRequest(param);
                    } else {
                        WonderToastUtil.show("手机号或验证码不能为空！");
                    }
                }
            });
        }

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
            BrightnessManager.lighton(SettingsActivity.this);
        }
        popupWindow.showAtLocation(SettingsActivity.this.findViewById(R.id.activity_settings),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
