package com.wondersgroup.android.jkcs_sdk.ui.settingspage.view;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.contract.SettingsContract;
import com.wondersgroup.android.jkcs_sdk.ui.settingspage.presenter.SettingsPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViews();
        initData();
        initListener();
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_settings));
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

    @Override
    protected SettingsPresenter<SettingsContract.IView> createPresenter() {
        return new SettingsPresenter<>();
    }

    @Override
    protected void bindView() {

    }

    @Override
    public void show() {

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

            EditText Phone = (EditText) popupView.findViewById(R.id.etPhone);
            EditText etVerifyCode = (EditText) popupView.findViewById(R.id.etVerifyCode);
            TextView tvOriginalPhone = (TextView) popupView.findViewById(R.id.tvOriginalPhone);

            // 获取验证码
            popupView.findViewById(R.id.tvGetSmsCode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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
