package com.wondersgroup.android.jkcs_sdk.ui.personalpay.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.KeyboardBean;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableObjectHashMap;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.view.FeeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.contract.PersonalPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.presenter.PersonalPayPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.PayResultLayout;
import com.wondersgroup.android.jkcs_sdk.widget.TitleBarLayout;

import java.util.HashMap;

// 个人账户支付页面
public class PersonalPayActivity extends MvpBaseActivity<PersonalPayContract.IView,
        PersonalPayPresenter<PersonalPayContract.IView>> implements PersonalPayContract.IView {

    private static final String TAG = "PersonalPayActivity";
    private View activityView;
    private TextView tvTongChouPay;
    private TextView tvTotalPay;
    private TextView tvPersonalPay;
    private TextView tvYiBaoPay;
    private TextView tvPayDetails;
    private TextView tvCompleteTotal;
    private TextView tvCompletePersonal;
    private TextView tvCompleteYiBao;
    private TitleBarLayout titleBar;
    private Button btnConfirmPay;
    private LinearLayout llPaySuccess;
    private LinearLayout llPayResult;
    private LinearLayout llContainer1;
    private LinearLayout llContainer2;
    private LoadingView mLoading;
    private String mOrgCode = "";
    private String mOrgName = "";
    private String mFeeTotal = "";
    private String mFeeCashTotal = "";
    private String mFeeYbTotal = "";
    private HashMap<String, Object> mPassParamMap;
    private boolean mIsComplete = false;
    private PayResultLayout mPayResultLayout;

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

    @SuppressLint("SetTextI18n")
    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .setDropView(activityView)
                .build();

        Intent intent = getIntent();
        if (intent != null) {
            mIsComplete = intent.getBooleanExtra(IntentExtra.IS_COMPLETE, false);
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            mFeeTotal = intent.getStringExtra(IntentExtra.FEE_TOTAL);
            mFeeCashTotal = intent.getStringExtra(IntentExtra.FEE_CASH_TOTAL);
            mFeeYbTotal = intent.getStringExtra(IntentExtra.FEE_YB_TOTAL);
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableObjectHashMap sMap = (SerializableObjectHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                if (sMap != null) {
                    mPassParamMap = sMap.getMap();
                }
            }
        }

        // 设置不管是全部完成支付还是全部未完成支付时需要显示的数据
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        String lockStartTime = SpUtil.getInstance().getString(SpKey.LOCK_START_TIME, "");
        String payPlatTradeNo = SpUtil.getInstance().getString(SpKey.PAY_PLAT_TRADE_NO, "");

        mPayResultLayout = new PayResultLayout(this);
        mPayResultLayout.setTreatName(name);
        mPayResultLayout.setSocialNum(cardNum);
        mPayResultLayout.setHospitalName(mOrgName);
        mPayResultLayout.setBillDate(lockStartTime);
        mPayResultLayout.setBillNo(payPlatTradeNo);

        // 判断是否已经全部支付完成
        if (mIsComplete) {
            setPaymentView(true);
            titleBar.setTitleName("缴费结果");
            tvCompleteTotal.setText(mFeeTotal);
            tvCompletePersonal.setText(mFeeCashTotal);
            tvCompleteYiBao.setText(mFeeYbTotal);
            llContainer2.addView(mPayResultLayout);
        } else {
            // 医保支付为 0 时也需要发起正式结算，也就是需要弹出医保键盘输入密码然后发起正式结算
            setPaymentView(false);
            titleBar.setTitleName("医保账户支付");
            tvTongChouPay.setText("现金部分支付" + mFeeCashTotal + "元已完成！请继续支付医保部分！");
            tvTotalPay.setText(mFeeTotal + "元");
            tvPersonalPay.setText(mFeeCashTotal + "元");
            tvYiBaoPay.setText(mFeeYbTotal + "元");
            llContainer1.addView(mPayResultLayout);
        }
    }

    private void findViews() {
        titleBar = findViewById(R.id.titleBar);
        tvTongChouPay = (TextView) findViewById(R.id.tvTongChouPay);
        tvTotalPay = (TextView) findViewById(R.id.tvTotalPay);
        tvPersonalPay = (TextView) findViewById(R.id.tvPersonalPay);
        tvYiBaoPay = (TextView) findViewById(R.id.tvYiBaoPay);
        tvCompleteTotal = (TextView) findViewById(R.id.tvCompleteTotal);
        tvCompletePersonal = (TextView) findViewById(R.id.tvCompletePersonal);
        tvCompleteYiBao = (TextView) findViewById(R.id.tvCompleteYiBao);
        btnConfirmPay = (Button) findViewById(R.id.btnConfirmPay);
        activityView = findViewById(R.id.activityView);
        tvPayDetails = findViewById(R.id.tvPayDetails);
        llPaySuccess = findViewById(R.id.llPaySuccess);
        llPayResult = findViewById(R.id.llPayResult);
        llContainer1 = findViewById(R.id.llContainer1);
        llContainer2 = findViewById(R.id.llContainer2);
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
        titleBar.setOnBackListener(new TitleBarLayout.OnBackClickListener() {
            @Override
            public void onClick() {
                showAlertDialog();
            }
        });
    }

    @SuppressLint("ResourceType")
    private void showAlertDialog() {
        // R.style.AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage(getString(R.string.wonders_group_personal_pay_back_notice))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PersonalPayActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
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
                                String token = keyboardBean.getToken();
                                // 携带 token 发起正式结算
                                mPresenter.sendOfficialPay(token, mOrgCode, mPassParamMap);
                            } else {
                                String msg = keyboardBean.getMsg();
                                WToastUtil.show(String.valueOf(msg));
                            }
                        }
                    }
                });
    }

    /**
     * 页面跳转的 action
     *
     * @param context      上下文
     * @param isComplete   是否全部支付完成
     * @param isFinish     是否需要销毁跳转前的页面
     * @param orgName      机构名称
     * @param orgCode      机构编码
     * @param feeTotal     缴费总额
     * @param feeCashTotal 现金部分金额
     * @param feeYbTotal   医保部分金额
     * @param param        正式结算需要的 details 数据参数
     */
    public static void actionStart(Context context, boolean isComplete, boolean isFinish, String orgName,
                                   String orgCode, String feeTotal, String feeCashTotal, String feeYbTotal,
                                   HashMap<String, Object> param) {
        if (context != null) {
            Intent intent = new Intent(context, PersonalPayActivity.class);
            intent.putExtra(IntentExtra.IS_COMPLETE, isComplete);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.FEE_TOTAL, feeTotal);
            intent.putExtra(IntentExtra.FEE_CASH_TOTAL, feeCashTotal);
            intent.putExtra(IntentExtra.FEE_YB_TOTAL, feeYbTotal);
            // 传递序列化的 map 数据
            SerializableObjectHashMap sMap = new SerializableObjectHashMap();
            sMap.setMap(param); // 将map数据添加到封装的sMap中
            Bundle bundle = new Bundle();
            bundle.putSerializable(IntentExtra.SERIALIZABLE_MAP, sMap);
            intent.putExtras(bundle);

            context.startActivity(intent);
            if (isFinish) {
                ((Activity) context).finish();
            }
        } else {
            LogUtil.e(TAG, "context is null!");
        }
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
            LogUtil.i(TAG, "feeTotal===" + feeTotal + ",feeCashTotal===" + feeCashTotal + ",feeYbTotal===" + feeYbTotal);
            LogUtil.i(TAG, "mFeeTotal===" + mFeeTotal + ",mFeeCashTotal===" + mFeeCashTotal + ",mFeeYbTotal===" + mFeeYbTotal);

            // 显示全部支付完成的布局
            setPaymentView(true);
            titleBar.setTitleName("缴费结果");
            tvCompleteTotal.setText(mFeeTotal);
            tvCompletePersonal.setText(mFeeCashTotal);
            tvCompleteYiBao.setText(mFeeYbTotal);
            // 如果 llContainer1 中添加了 mPayResultLayout，需要先移除，然后再添加
            int childCount = llContainer1.getChildCount();
            if (childCount > 0) {
                llContainer1.removeAllViews();
            }
            llContainer2.addView(mPayResultLayout);
        }
    }

    /**
     * 设置是否已经全部支付完成的视图
     */
    private void setPaymentView(boolean isComplete) {
        if (isComplete) {
            llPayResult.setVisibility(View.GONE);
            llPaySuccess.setVisibility(View.VISIBLE);
        } else {
            llPayResult.setVisibility(View.VISIBLE);
            llPaySuccess.setVisibility(View.GONE);
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
