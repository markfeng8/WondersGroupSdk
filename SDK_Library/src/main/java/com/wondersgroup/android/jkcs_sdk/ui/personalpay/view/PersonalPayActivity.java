package com.wondersgroup.android.jkcs_sdk.ui.personalpay.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.epsoft.hzauthsdk.bean.KeyboardBean;
import com.epsoft.hzauthsdk.utils.MakeArgsFactory;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.view.FeeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.contract.PersonalPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.presenter.PersonalPayPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;

// 个人账户支付页面
public class PersonalPayActivity extends MvpBaseActivity<PersonalPayContract.IView,
        PersonalPayPresenter<PersonalPayContract.IView>> implements PersonalPayContract.IView {

    private static final String TAG = "PersonalPayActivity";
    private View activityView;
    private TextView tvTongChouPay;
    private TextView tvTreatName;
    private TextView tvSocialNum;
    private TextView tvBillDate;
    private TextView tvTotalPay;
    private TextView tvPayDetails;
    private Button btnConfirmPay;
    private LinearLayout llPaySuccess;
    private LinearLayout llPayResult;
    private LoadingView mLoading;

    @Override
    protected PersonalPayPresenter<PersonalPayContract.IView> createPresenter() {
        return new PersonalPayPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_personal_pay);
        findViews();
        initData();
        initListener();
    }

    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .setDropView(activityView)
                .build();

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        tvTreatName.setText(name);
        tvSocialNum.setText(cardNum);
    }

    private void findViews() {
        tvTongChouPay = (TextView) findViewById(R.id.tvTongChouPay);
        tvTreatName = (TextView) findViewById(R.id.tvTreatName);
        tvSocialNum = (TextView) findViewById(R.id.tvSocialNum);
        tvBillDate = (TextView) findViewById(R.id.tvBillDate);
        tvTotalPay = (TextView) findViewById(R.id.tvTotalPay);
        btnConfirmPay = (Button) findViewById(R.id.btnConfirmPay);
        activityView = findViewById(R.id.activityView);
        tvPayDetails = findViewById(R.id.tvPayDetails);
        llPaySuccess = findViewById(R.id.llPaySuccess);
        llPayResult = findViewById(R.id.llPayResult);
    }

    private void initListener() {
        btnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYiBaoKeyBoard();
            }
        });
        tvPayDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalPayActivity.this, FeeRecordActivity.class));
                finish();
            }
        });
    }

    private void openYiBaoKeyBoard() {
        AuthCall.getToken(PersonalPayActivity.this, MakeArgsFactory.getKeyboardArgs(),
                result -> {
                    LogUtil.i(TAG, "result===" + result);
                    if (!TextUtils.isEmpty(result)) {
                        KeyboardBean keyboardBean = new Gson().fromJson(result, KeyboardBean.class);
                        if (keyboardBean != null) {
                            String code = keyboardBean.getCode();
                            if ("0".equals(code)) {

                                // TODO: 2018/9/18 携带 token 发起正式结算
                                showPaySuccess();

                            } else {
                                String msg = keyboardBean.getMsg();
                                WToastUtil.show(String.valueOf(msg));
                            }
                        }
                    }
                });
    }

    private void showPaySuccess() {
        llPayResult.setVisibility(View.GONE);
        llPaySuccess.setVisibility(View.VISIBLE);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PersonalPayActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    @Override
    public void showLoading() {
        if (mLoading != null) {
            mLoading.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    @Override
    public void onOfficialSettleResult(SettleEntity body) {
        if (body != null) {
            String feeTotal = body.getFee_total();
            String feeCashTotal = body.getFee_cash_total();
            String feeYbTotal = body.getFee_yb_total();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoading != null) {
            mLoading.dispose();
        }
    }
}
