package com.wondersgroup.android.jkcs_sdk.ui.personalpay.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.wondersgroup.android.jkcs_sdk.entity.KeyboardBean;
import com.wondersgroup.android.jkcs_sdk.utils.MakeArgsFactory;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableObjectHashMap;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.view.FeeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.contract.PersonalPayContract;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.presenter.PersonalPayPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.PayResultLayout;

import java.util.HashMap;

// 个人账户支付页面
public class PersonalPayActivity extends MvpBaseActivity<PersonalPayContract.IView,
        PersonalPayPresenter<PersonalPayContract.IView>> implements PersonalPayContract.IView {

    private static final String TAG = "PersonalPayActivity";
    private View activityView;
    private TextView tvTongChouPay;
    private TextView tvPayToast;
    private TextView tvTotalPay;
    private TextView tvYiBaoPay;
    private TextView tvPayDetails;
    private TextView tvCompleteTotal;
    private TextView tvCompletePersonal;
    private TextView tvCompleteYiBao;
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
    private boolean mIsFinish = false;

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
            mIsFinish = intent.getBooleanExtra(IntentExtra.IS_FINISH, false);
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

        PayResultLayout payResultLayout = new PayResultLayout(this);
        payResultLayout.setTreatName(name);
        payResultLayout.setSocialNum(cardNum);
        payResultLayout.setHospitalName(mOrgName);
        payResultLayout.setBillDate(TimeUtil.getCurrentDate());

        // 判断是否已经全部支付完成
        if (mIsFinish) {
            setPaymentView(true);
            tvCompleteTotal.setText(mFeeTotal);
            tvCompletePersonal.setText(mFeeCashTotal);
            tvCompleteYiBao.setText(mFeeYbTotal);
            llContainer2.addView(payResultLayout);
        } else {
            // 医保支付为 0 时也需要发起正式结算，也就是需要弹出医保键盘输入密码然后发起正式结算
            setPaymentView(false);
            tvTongChouPay.setText("个人账户支付" + mFeeCashTotal + "元已完成！");
            tvPayToast.setText("您还有一笔医保" + mFeeYbTotal + "元尚未支付，请继续支付！");
            tvTotalPay.setText(mFeeTotal);
            tvYiBaoPay.setText(mFeeYbTotal);
            llContainer1.addView(payResultLayout);
        }
    }

    private void findViews() {
        tvTongChouPay = (TextView) findViewById(R.id.tvTongChouPay);
        tvPayToast = (TextView) findViewById(R.id.tvPayToast);
        tvTotalPay = (TextView) findViewById(R.id.tvTotalPay);
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

    public static void actionStart(Context context, boolean isFinish, String orgName, String orgCode, String feeTotal,
                                   String feeCashTotal, String feeYbTotal, HashMap<String, Object> param) {
        if (context != null) {
            Intent intent = new Intent(context, PersonalPayActivity.class);
            intent.putExtra(IntentExtra.IS_FINISH, isFinish);
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
            ((Activity) context).finish();
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

            tvCompleteTotal.setText(feeTotal);
            tvCompletePersonal.setText(feeCashTotal);
            tvCompleteYiBao.setText(feeYbTotal);
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
