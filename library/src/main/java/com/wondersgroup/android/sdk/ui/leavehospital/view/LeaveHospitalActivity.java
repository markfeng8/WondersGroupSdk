/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.leavehospital.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.constants.IntentExtra;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.Cy0006Entity;
import com.wondersgroup.android.sdk.entity.Cy0007Entity;
import com.wondersgroup.android.sdk.entity.EleCardEntity;
import com.wondersgroup.android.sdk.entity.EleCardTokenEntity;
import com.wondersgroup.android.sdk.entity.PayParamEntity;
import com.wondersgroup.android.sdk.epsoft.ElectronicSocialSecurityCard;
import com.wondersgroup.android.sdk.ui.leavehospital.contract.LeaveHospitalContract;
import com.wondersgroup.android.sdk.ui.leavehospital.presenter.LeaveHospitalPresenter;
import com.wondersgroup.android.sdk.ui.leavehosresult.LeaveHosResultActivity;
import com.wondersgroup.android.sdk.utils.EpSoftUtils;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.PaymentUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;
import com.wondersgroup.android.sdk.utils.WdCommonPayUtils;

import java.util.concurrent.TimeUnit;

import cn.com.epsoft.zjessc.ZjEsscSDK;
import cn.com.epsoft.zjessc.callback.SdkCallBack;
import cn.com.epsoft.zjessc.tools.ZjBiap;
import cn.com.epsoft.zjessc.tools.ZjEsscException;
import cn.wd.checkout.api.WDPay;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by x-sir on 2018/11/9 :)
 * Function:??????????????????
 */
public class LeaveHospitalActivity extends MvpBaseActivity<LeaveHospitalContract.IView,
        LeaveHospitalPresenter<LeaveHospitalContract.IView>> implements LeaveHospitalContract.IView {

    private static final String TAG = "LeaveHospitalActivity";
    private TextView tvName;
    private TextView tvHosName;
    private TextView tvSocial;
    private TextView tvSocialNum;
    private TextView tvInHosId;
    private TextView tvInHosDate;
    private TextView tvInHosArea;
    private TextView tvTotalFee;
    private TextView tvYiBaoFee;
    private TextView tvCashFee;
    private TextView tvPrepayFee;
    private TextView tvNeedFee;
    private TextView tvWillPayFee;
    private TextView tvToPay;
    private RadioGroup rgPayType;
    private RadioButton rbAlipay;
    private RadioButton rbWeChatPay;
    private RadioButton rbUnionPay;
    private ConstraintLayout clBody;
    private String mOrgCode;
    private String mOrgName;
    private String mFeeNeedCashTotal;
    private String mPayPlatTradeNo;
    private String mYiBaoToken;
    private String feeCashTotal;
    private String feeYbTotal;
    private String feeTotal;
    private String feePrepayTotal;

    /**
     * ?????????????????????????????????
     */
    private int mPaymentType = 1;
    /**
     * ??????????????????
     */
    private int mOfficeSettleTimes = 0;
    /**
     * toState 1 ?????? token 2 ????????????
     */
    private static final String TO_STATE1 = "1";
    private static final String TO_STATE2 = "2";
    private String mCurrentToState;
    private String mBusinessType;

    @Override
    protected LeaveHospitalPresenter<LeaveHospitalContract.IView> createPresenter() {
        return new LeaveHospitalPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_leave_hospital);
        findViews();
        initData();
        initListener();
    }

    private void findViews() {
        tvName = findViewById(R.id.tvName);
        tvHosName = findViewById(R.id.tvHosName);
        tvSocial = findViewById(R.id.tvSocial);
        tvSocialNum = findViewById(R.id.tvSocialNum);
        tvInHosId = findViewById(R.id.tvInHosId);
        tvInHosDate = findViewById(R.id.tvInHosDate);
        tvInHosArea = findViewById(R.id.tvInHosArea);
        tvTotalFee = findViewById(R.id.tvTotalFee);
        tvYiBaoFee = findViewById(R.id.tvYiBaoFee);
        tvCashFee = findViewById(R.id.tvCashFee);
        tvPrepayFee = findViewById(R.id.tvPrepayFee);
        tvNeedFee = findViewById(R.id.tvNeedFee);
        tvWillPayFee = findViewById(R.id.tvWillPayFee);
        tvToPay = findViewById(R.id.tvToPay);
        rgPayType = findViewById(R.id.rgPayType);
        rbAlipay = findViewById(R.id.rbAlipay);
        rbWeChatPay = findViewById(R.id.rbWeChatPay);
        rbUnionPay = findViewById(R.id.rbUnionPay);
        clBody = findViewById(R.id.clBody);
    }

    private void initData() {
        rbAlipay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_alipay)));
        rbWeChatPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_wechat_pay)));
        rbUnionPay.setText(Html.fromHtml(getResources().getString(R.string.wonders_text_union_pay)));
        // ?????????????????????
        rgPayType.check(R.id.rbAlipay);

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("2".equals(cardType)) {
            tvSocial.setText("???????????????");
        }
        tvName.setText(name);
        tvSocialNum.setText(cardNum);

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
            String inHosId = intent.getStringExtra(IntentExtra.IN_HOS_ID);
            String inHosDate = intent.getStringExtra(IntentExtra.IN_HOS_DATE);
            String inHosArea = intent.getStringExtra(IntentExtra.IN_HOS_AREA);

            tvHosName.setText(mOrgName);
            tvInHosId.setText(inHosId);
            tvInHosDate.setText(inHosDate);
            tvInHosArea.setText(inHosArea);
        }

        // ??????????????? token???????????? cy0006 ??????
        EpSoftUtils.getTryToSettleToken(this, this::tryToSettle);
    }

    private void initListener() {
        tvToPay.setOnClickListener(v -> {
            if (clBody.getVisibility() == View.VISIBLE) {
                getOfficialToSettleToken();
            } else {
                WToastUtil.show("??????????????????");
            }
        });
        rgPayType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbAlipay) {
                mPaymentType = 1;
            } else if (checkedId == R.id.rbWeChatPay) {
                mPaymentType = 2;
            } else if (checkedId == R.id.rbUnionPay) {
                mPaymentType = 3;
            }
        });
    }

    public void requestTryToSettleToken(String businessType) {
        mBusinessType = businessType;
        mPresenter.applyElectronicSocialSecurityCardToken(mBusinessType);
    }

    @Override
    public void onApplyElectronicSocialSecurityCardToken(EleCardTokenEntity body) {
        String token = body.getToken();
        LogUtil.i(TAG, "token===" + token);
        if (OrgConfig.SRY.equals(mBusinessType)) {
            tryToSettle(token);
        } else if (OrgConfig.SRJ.equals(mBusinessType)) {
            officialSettle(token);
        }
    }

    private void officialSettle(String token) {
        mYiBaoToken = token;
        boolean isSelfFeeCard = "0".equals(token);
        // ?????? token ??? 0??????????????????????????????????????? token??????????????????????????????????????? token
        mCurrentToState = isSelfFeeCard ? TO_STATE2 : TO_STATE1;
        if (isSelfFeeCard) {
            if (!TextUtils.isEmpty(mFeeNeedCashTotal) && Double.parseDouble(mFeeNeedCashTotal) > 0) {
                mPresenter.getPayParam(mOrgCode);
            } else {
                requestCy0007();
            }
        } else {
            requestCy0007();
        }
    }

    private void tryToSettle(String token) {
        mPresenter.requestCy0006(mOrgCode, token);
    }

    public void checkElectronicSocialSecurityCardPassword() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");

        ElectronicSocialSecurityCard.getSign(
                ElectronicSocialSecurityCard.getVerifyElectronicSocialSecurityCardPasswordParams(),
                s -> startSdk(idNum, name, s)
        );
    }

    /**
     * ??????SDK
     *
     * @param idCard ?????????
     * @param name   ??????
     * @param s      ??????
     */
    private void startSdk(final String idCard, final String name, String s) {
        LogUtil.i(TAG, "idCard===" + idCard + ",name===" + name + ",s===" + s);
        String url = ZjBiap.getInstance().getPwdValidate();
        LogUtil.i(TAG, "url===" + url);

        // 662701
        ZjEsscSDK.startSdk(LeaveHospitalActivity.this, idCard, name, url, s, new SdkCallBack() {
            @Override
            public void onLoading(boolean show) {
                showLoading(show);
            }

            @Override
            public void onResult(String data) {
                handleScene(data);
            }

            @Override
            public void onError(String code, ZjEsscException e) {
                LogUtil.e(TAG, "onError():code===" + code + ",errorMsg===" + e.getMessage());
            }
        });
    }

    /**
     * ????????????????????????
     */
    private void handleScene(String data) {
        WToastUtil.show(data);
        EleCardEntity eleCardEntity = new Gson().fromJson(data, EleCardEntity.class);
        String actionType = eleCardEntity.getActionType();
        // ????????????
        if ("009".equals(actionType)) {
            ZjEsscSDK.closeSDK();
            String busiSeq = eleCardEntity.getBusiSeq();
            SpUtil.getInstance().save(SpKey.BUSI_SEQ, busiSeq);
            requestTryToSettleToken(OrgConfig.SRJ);
        }
    }

    private void getOfficialToSettleToken() {
        EpSoftUtils.getOfficialToSettleToken(this, this::officialSettle);
    }

    @Override
    public void showLoading(boolean show) {
        showLoadingView(show);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCy0006Result(Cy0006Entity entity) {
        feeCashTotal = entity.getFeeCashTotal();
        mFeeNeedCashTotal = entity.getFeeNeedCashTotal();
        feePrepayTotal = entity.getFeePrepayTotal();
        feeTotal = entity.getFeeTotal();
        feeYbTotal = entity.getFeeYbTotal();
        mPayPlatTradeNo = entity.getPayPlatTradeNo();
        String payStartTime = entity.getPayStartTime();

        SpUtil.getInstance().save(SpKey.PAY_PLAT_TRADE_NO, mPayPlatTradeNo);
        SpUtil.getInstance().save(SpKey.PAY_START_TIME, payStartTime);

        clBody.setVisibility(View.VISIBLE);
        tvTotalFee.setText(feeTotal + "???");
        tvYiBaoFee.setText(feeYbTotal + "???");
        tvCashFee.setText(feeCashTotal + "???");
        tvPrepayFee.setText(feePrepayTotal + "???");
        tvNeedFee.setText(mFeeNeedCashTotal);
        tvWillPayFee.setText("???" + mFeeNeedCashTotal);
    }

    @Override
    public void onPayParamResult(PayParamEntity body) {
        showLoading(true);
        PayParamEntity paramEntity = PayParamEntity.from(
                body, mOrgName, mPayPlatTradeNo, mPaymentType, mFeeNeedCashTotal
        );

        // ?????????????????????????????????????????????
        mCompositeDisposable.add(
                WdCommonPayUtils.toPay(this, paramEntity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            showLoading(false);
                            WToastUtil.show("SUCCESS".equals(s) ? "????????????~" : s);
                            if ("SUCCESS".equals(s)) {
                                mCurrentToState = TO_STATE2;
                                // ?????????????????????????????????
                                requestCy0007();
                            }
                        }, throwable -> WToastUtil.show(throwable.getMessage()))
        );
    }

    @Override
    public void timeoutAfter60s() {
        WToastUtil.show("??????????????????????????????????????????");
        finish();
    }

    /**
     * ??????????????????
     */
    private void requestCy0007() {
        if (TO_STATE2.equals(mCurrentToState)) {
            WToastUtil.showLong("?????????????????????????????????????????????????????????????????????????????????");
        }
        mPresenter.requestCy0007(mOrgCode, mCurrentToState, mYiBaoToken, mFeeNeedCashTotal, PaymentUtil.getPaymentChl(mPaymentType));
    }

    @Override
    public void onCy0007Result(Cy0007Entity entity) {
        if (TO_STATE1.equals(mCurrentToState)) {
            if (!TextUtils.isEmpty(mFeeNeedCashTotal) && Double.parseDouble(mFeeNeedCashTotal) > 0) {
                // ??????????????????
                mPresenter.getPayParam(mOrgCode);
            } else {
                mCurrentToState = TO_STATE2;
                requestCy0007();
            }

        } else if (TO_STATE2.equals(mCurrentToState)) {
            if (entity != null) {
                String payState = entity.getPayState();
                if (!TextUtils.isEmpty(payState)) {
                    switch (payState) {
                        // 1?????????????????????????????????????????????
                        case "1":
                            // ?????? 3 ???????????????????????????????????????
                            if (mOfficeSettleTimes < 3) {
                                mOfficeSettleTimes++;
                                waitingAndOnceAgain();
                            } else {
                                showLoading(false);
                                LeaveHospitalActivity.this.finish();
                            }
                            break;
                        // 2???????????????????????????????????????
                        case "2":
                            jumpToLeaveHospitalResultPager(true);
                            break;
                        // 3?????????????????????????????????????????????
                        case "3":
                            jumpToLeaveHospitalResultPager(false);
                            break;
                        default:
                            break;
                    }
                } else {
                    LogUtil.e(TAG, "payState is null!");
                }

            } else {
                jumpToLeaveHospitalResultPager(false);
            }
        }
    }

    private void waitingAndOnceAgain() {
        showLoading(true);
        // ????????????????????????????????? 10 ????????????????????????
        Disposable disposable =
                Observable
                        .timer(10, TimeUnit.SECONDS)
                        .subscribe(aLong -> {
                            mCurrentToState = TO_STATE2;
                            requestCy0007();
                        });

        mCompositeDisposable.add(disposable);
    }

    private void jumpToLeaveHospitalResultPager(boolean isSuccess) {
        LeaveHosResultActivity.actionStart(this, isSuccess, mOrgCode, mOrgName, feeTotal,
                feeCashTotal, feeYbTotal, feePrepayTotal, mFeeNeedCashTotal);
        finish();
    }

    public static void actionStart(Context context, String orgCode, String orgName, String inHosId, String inHosDate, String inHosArea) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, LeaveHospitalActivity.class);
        intent.putExtra(IntentExtra.ORG_CODE, orgCode);
        intent.putExtra(IntentExtra.ORG_NAME, orgName);
        intent.putExtra(IntentExtra.IN_HOS_ID, inHosId);
        intent.putExtra(IntentExtra.IN_HOS_DATE, inHosDate);
        intent.putExtra(IntentExtra.IN_HOS_AREA, inHosArea);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ?????????????????????????????????
        WDPay.ReleasePayserver();
        // ???????????????????????? mYiBaoToken ??? mYiBaoToken time ??????
        SpUtil.getInstance().save(SpKey.YIBAO_TOKEN, "");
        SpUtil.getInstance().save(SpKey.TOKEN_TIME, "");
    }
}
