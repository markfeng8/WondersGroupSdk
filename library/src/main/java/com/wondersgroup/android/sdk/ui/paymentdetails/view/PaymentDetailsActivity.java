/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.paymentdetails.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.adapter.PaymentDetailsAdapter;
import com.wondersgroup.android.sdk.base.MvpBaseActivity;
import com.wondersgroup.android.sdk.constants.IntentExtra;
import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.constants.OrgConfig;
import com.wondersgroup.android.sdk.constants.SpKey;
import com.wondersgroup.android.sdk.entity.CombineDetailsBean;
import com.wondersgroup.android.sdk.entity.DetailHeadBean;
import com.wondersgroup.android.sdk.entity.DetailPayBean;
import com.wondersgroup.android.sdk.entity.EleCardEntity;
import com.wondersgroup.android.sdk.entity.EleCardTokenEntity;
import com.wondersgroup.android.sdk.entity.FeeBillDetailsBean;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.LockOrderEntity;
import com.wondersgroup.android.sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.sdk.entity.PayParamEntity;
import com.wondersgroup.android.sdk.entity.SettleEntity;
import com.wondersgroup.android.sdk.epsoft.ElectronicSocialSecurityCard;
import com.wondersgroup.android.sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.sdk.ui.paymentdetails.presenter.PaymentDetailsPresenter;
import com.wondersgroup.android.sdk.ui.paymentresult.view.PaymentResultActivity;
import com.wondersgroup.android.sdk.utils.BrightnessManager;
import com.wondersgroup.android.sdk.utils.EpSoftUtils;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.NumberUtil;
import com.wondersgroup.android.sdk.utils.SettleUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;
import com.wondersgroup.android.sdk.utils.WdCommonPayUtils;
import com.wondersgroup.android.sdk.widget.LoadingView;
import com.wondersgroup.android.sdk.widget.SelectPayTypeWindow;
import com.wondersgroup.android.sdk.widget.TitleBarLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.com.epsoft.zjessc.ZjEsscSDK;
import cn.com.epsoft.zjessc.callback.SdkCallBack;
import cn.com.epsoft.zjessc.tools.ZjBiap;
import cn.com.epsoft.zjessc.tools.ZjEsscException;
import cn.wd.checkout.api.WDPay;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:??????????????????
 */
public class PaymentDetailsActivity extends MvpBaseActivity<PaymentDetailsContract.IView,
        PaymentDetailsPresenter<PaymentDetailsContract.IView>> implements PaymentDetailsContract.IView {

    private static final String TAG = "PaymentDetailsActivity";
    private RecyclerView recyclerView;
    private TextView tvPayName;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private TextView tvNotice;
    private TextView tvTextYuan;
    private View activityView;
    private TitleBarLayout titleBar;
    private String mOrgCode;
    private String mOrgName;
    private DetailHeadBean mHeadBean;
    private PaymentDetailsAdapter mAdapter;
    private List<Object> mItemList = new ArrayList<>();
    private DetailPayBean mDetailPayBean = new DetailPayBean();
    private SelectPayTypeWindow mSelectPayTypeWindow;
    private int mPaymentType = 1;
    private String mFeeTotal;
    private String mFeeCashTotal;
    private String mFeeYbTotal;
    private String mPayPlatTradeNo;
    private LoadingView mLoading;
    private boolean tryToSettleIsSuccess = false;
    private int mClickItemPosition = -1;
    /**
     * ??????????????????????????????????????????
     */
    private boolean isNeedToPay = false;
    /**
     * ?????? Item ???????????????
     */
    private List<CombineDetailsBean> mCombineList = new ArrayList<>();
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
    private PaymentDetailsAdapter.OnCheckedCallback mOnCheckedCallback;

    private SelectPayTypeWindow.OnCheckedListener mCheckedListener = type -> {
        mPaymentType = type;
        if (mOnCheckedCallback != null) {
            mOnCheckedCallback.onSelected(mPaymentType);
        }
    };
    private SelectPayTypeWindow.OnLoadingListener onLoadingListener =
            () -> BrightnessManager.lighton(PaymentDetailsActivity.this);
    private List<FeeBillDetailsBean> details;
    private String mYiBaoToken;

    @Override
    protected PaymentDetailsPresenter<PaymentDetailsContract.IView> createPresenter() {
        return new PaymentDetailsPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_payment_details);
        findViews();
        initData();
        initListener();
    }

    private void initData() {
        mLoading = new LoadingView.Builder(this).build();
        mSelectPayTypeWindow = new SelectPayTypeWindow.Builder(this)
                .setDropView(activityView)
                .setListener(onLoadingListener)
                .setCheckedListener(mCheckedListener)
                .build();

        // ??????????????????????????????????????????????????????????????????????????????
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            tvNotice.setVisibility(View.VISIBLE);
        } else if ("2".equals(cardType)) {
            tvNotice.setVisibility(View.GONE);
        }

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
        }

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        mHeadBean = new DetailHeadBean();
        mHeadBean.setName(name);
        mHeadBean.setOrderNum(mPayPlatTradeNo);
        mHeadBean.setSocialNum(cardNum);
        mHeadBean.setHospitalName(mOrgName);

        mItemList.add(mHeadBean);
        setAdapter();

        // ???????????????????????????
        mPresenter.requestYd0003(mOrgCode);
    }

    /**
     * ?????????????????????????????????
     */
    private void initListener() {
        tvPayMoney.setOnClickListener(v -> toPayMoney());
        titleBar.setOnBackListener(this::showAlertDialog);
    }

    /**
     * ??????????????????????????????
     */
    private void toPayMoney() {
        // ??????????????????????????????????????????????????????????????????
        if (mLoading != null && mLoading.isShowing()) {
            WToastUtil.show("???????????????????????????????????????");
            return;
        }

        // ???????????????????????????????????? token????????????????????????????????????
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            // ???????????????????????????
            if (tryToSettleIsSuccess) {
                getOfficialToSettleToken();
            } else {
                isNeedToPay = true;
                getTryToSettleToken();
            }
        } else if ("2".equals(cardType)) {
            // ???????????????????????????????????????????????????????????????????????????
            LogUtil.i(TAG, "mOrgCode===" + mOrgCode);
            mPresenter.getPayParam(mOrgCode);
        }
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????? token
     */
    private void queryEleCardOpenStatus() {
        String eleCardStatus = SpUtil.getInstance().getString(SpKey.ELE_CARD_STATUS, "");
        // ?????????
        if ("01".equals(eleCardStatus)) {
            getTryToSettleToken();
        }
    }

    private void getTryToSettleToken() {
        // ?????????????????????????????? token ????????????????????????????????????????????????
        EpSoftUtils.getTryToSettleToken(this, this::tryToSettle);
    }

    private void getOfficialToSettleToken() {
        EpSoftUtils.getOfficialToSettleToken(this, this::officialSettle);
    }

    private void officialSettle(String token) {
        mYiBaoToken = token;
        LogUtil.i(TAG, "onYiBaoTokenResult() -> token===" + token);
        if (!TextUtils.isEmpty(mFeeCashTotal)) {
            // ???????????????????????? token
            mCurrentToState = TO_STATE1;
            sendOfficialPay(false);
        } else {
            LogUtil.e(TAG, "to pay money failed, because mFeeCashTotal is null!");
        }
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("????????????")
                .setMessage(getString(R.string.wonders_group_personal_pay_back_notice2))
                .setPositiveButton("??????", (dialog, which) -> PaymentDetailsActivity.this.finish())
                .setNegativeButton("??????", null)
                .show();
    }

    private void findViews() {
        titleBar = findViewById(R.id.titleBar);
        recyclerView = findViewById(R.id.recyclerView);
        tvPayName = findViewById(R.id.tvPayName);
        tvMoneyNum = findViewById(R.id.tvMoneyNum);
        tvPayMoney = findViewById(R.id.tvPayMoney);
        activityView = findViewById(R.id.activityView);
        tvNotice = findViewById(R.id.tvNotice);
        tvTextYuan = findViewById(R.id.tvTextYuan);
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mAdapter = new PaymentDetailsAdapter(this, mItemList);
            recyclerView.setAdapter(mAdapter);
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public void onYd0003Result(FeeBillEntity entity) {
        if (entity != null) {
            // ????????????????????????????????????
            mFeeTotal = entity.getFeeTotal();
            mFeeCashTotal = entity.getFeeTotal();
            mFeeYbTotal = "0";

            List<HashMap<String, String>> detailsList = new ArrayList<>();
            details = entity.getDetails();
            // ?????????????????????
            getCombineListData(details);
            // ??????????????????
            mItemList.addAll(mCombineList);
            // ??????????????????
            mItemList.add(mDetailPayBean);
            refreshAdapter();

            HashMap<String, Object> map = new HashMap<>();
            map.put(MapKey.FEE_TOTAL, mFeeTotal);
            map.put(MapKey.ORG_CODE, mOrgCode);

            for (int i = 0; i < details.size(); i++) {
                FeeBillDetailsBean detailsBean = details.get(i);
                HashMap<String, String> detailItem = new HashMap<>();
                detailItem.put(MapKey.HIS_ORDER_NO, detailsBean.getHis_order_no());
                detailItem.put(MapKey.HIS_ORDER_TIME, detailsBean.getHis_order_time());
                detailItem.put(MapKey.FEE_ORDER, NumberUtil.twoBitDecimal(detailsBean.getFee_order()));
                detailItem.put(MapKey.ORDER_NAME, detailsBean.getOrdername());
                detailsList.add(detailItem);
            }

            if (detailsList.size() > 0) {
                map.put(MapKey.DETAILS, detailsList);
            }

            // ??????????????????
            mPresenter.lockOrder(map, detailsList.size());
        }
    }

    /**
     * ?????? List ???????????????
     */
    private void getCombineListData(List<FeeBillDetailsBean> details) {
        for (int i = 0; i < details.size(); i++) {
            CombineDetailsBean bean = new CombineDetailsBean();
            bean.setDefaultDetails(details.get(i));
            mCombineList.add(bean);
        }
    }

    @Override
    public void lockOrderResult(LockOrderEntity entity) {
        mCompositeDisposable.add(
                Observable
                        .just(entity)
                        .doOnNext(lockOrderEntity -> {
                            SpUtil.getInstance().save(SpKey.LOCK_START_TIME, lockOrderEntity.getLockStartTime());
                            SpUtil.getInstance().save(SpKey.PAY_PLAT_TRADE_NO, lockOrderEntity.getPayPlatTradNo());
                        })
                        .map(LockOrderEntity::getPayPlatTradNo)
                        .subscribe(s -> {
                            mPayPlatTradeNo = s;
                            // ??????????????????????????????
                            mHeadBean.setOrderNum(s);
                            refreshAdapter();
                            continueSettle();
                        })
        );
    }

    /**
     * ?????????????????????????????????
     */
    private void continueSettle() {
        // ??????????????????????????????????????????????????????????????????????????????
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            queryEleCardOpenStatus();
        } else {
            // ???????????????????????????
            tvPayName.setText("??????????????????");
            // ?????????????????????????????????
            tvTextYuan.setVisibility(View.VISIBLE);
            tvMoneyNum.setText(mFeeCashTotal);

            mDetailPayBean.setTotalPay(mFeeTotal);
            mDetailPayBean.setPersonalPay(mFeeCashTotal);
            mDetailPayBean.setYibaoPay(mFeeYbTotal);

            // ???????????????????????????????????????????????????????????????????????????
            if (mItemList.size() > 0) {
                mItemList.clear();
            }
            // ?????????????????????
            mItemList.add(mHeadBean);
            // ????????? List ??????
            mItemList.addAll(mCombineList);
            // ??????????????????
            mItemList.add(mDetailPayBean);
            refreshAdapter();
        }
    }

    /**
     * ??????????????????????????????
     */
    @Override
    public void onOrderDetailsResult(OrderDetailsEntity entity) {
        if (entity != null) {
            List<OrderDetailsEntity.DetailsBean> details = entity.getDetails();
            if (details.size() > 0) {
                // List ????????? 1 ???????????????????????????????????? 1
                mCombineList.get(mClickItemPosition - 1).setOpenDetails(details);
                mCombineList.get(mClickItemPosition - 1).setSpread(true);
                // ???????????????
                refreshAdapter();
            }
        }
    }

    @Override
    public void onTryToSettleResult(SettleEntity body) {
        if (body == null) {
            tryToSettleIsSuccess = false;
            return;
        }

        tryToSettleIsSuccess = true;
        mFeeTotal = body.getFee_total();
        mFeeCashTotal = body.getFee_cash_total();
        mFeeYbTotal = body.getFee_yb_total();
        LogUtil.i(TAG, "mFeeTotal===" + mFeeTotal + ",mFeeCashTotal===" + mFeeCashTotal + ",mFeeYbTotal===" + mFeeYbTotal);

        tvTextYuan.setVisibility(View.VISIBLE);
        // ??????????????????????????? 0 ??????????????????????????????
        if (Double.parseDouble(mFeeCashTotal) == 0) {
            tvPayName.setText("??????????????????");
            tvMoneyNum.setText(mFeeYbTotal);
        } else {
            tvPayName.setText("??????????????????");
            // ?????????????????????????????????
            tvMoneyNum.setText(mFeeCashTotal);
        }

        if (mDetailPayBean == null) {
            mDetailPayBean = new DetailPayBean();
        }
        mDetailPayBean.setTotalPay(mFeeTotal);
        mDetailPayBean.setPersonalPay(mFeeCashTotal);
        mDetailPayBean.setYibaoPay(mFeeYbTotal);

        // ???????????????????????????????????????????????????????????????????????????
        if (mItemList.size() > 0) {
            mItemList.clear();
        }
        // ?????????????????????
        mItemList.add(mHeadBean);
        // ????????? List ??????
        mItemList.addAll(mCombineList);
        // ??????????????????
        mItemList.add(mDetailPayBean);
        refreshAdapter();

        // ???????????????????????????????????????????????????????????????
        if (isNeedToPay) {
            getOfficialToSettleToken();
        }
    }

    @Override
    public void onPayParamResult(PayParamEntity body) {
        showLoading(true);
        PayParamEntity paramEntity = PayParamEntity.from(
                body, mOrgName, mPayPlatTradeNo, mPaymentType, mFeeCashTotal
        );
        // ?????????????????????????????????????????????
        mCompositeDisposable.add(
                WdCommonPayUtils.toPay(this, paramEntity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            showLoading(false);
                            WToastUtil.show("SUCCESS".equals(s) ? "????????????~" : s);
                            if ("SUCCESS".equals(s)) {
                                // ?????????????????????????????????
                                onCashPaySuccess();
                            }
                        }, throwable -> WToastUtil.show(throwable.getMessage()))
        );
    }

    private void onCashPaySuccess() {
        // ??????????????????????????????????????????????????????????????????????????????
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            // ???????????????????????????????????????????????????(????????????????????????????????????0)
            if (!TextUtils.isEmpty(mFeeYbTotal)) {
                mCurrentToState = TO_STATE2;
                sendOfficialPay(false);
            } else {
                LogUtil.e(TAG, "to pay money failed, because mFeeYbTotal is null!");
            }
        } else if ("2".equals(cardType)) {
            // ?????????????????????token ??? 0
            mCurrentToState = TO_STATE2;
            mPresenter.sendOfficialPay(false, mCurrentToState, "0", mOrgCode,
                    SettleUtil.getOfficialSettleParam(details));
        }
    }

    @Override
    public void onOfficialSettleResult(SettleEntity body) {
        if (TO_STATE1.equals(mCurrentToState)) {
            /*
             * ????????? token ??????????????????????????? 0????????? mYiBaoToken??????????????????????????????????????????????????????
             * ???????????? 0????????????????????????(????????????)????????????????????????
             */
            if (Double.parseDouble(mFeeCashTotal) == 0) {
                mCurrentToState = TO_STATE2;
                // ?????? mYiBaoToken ??????????????????
                sendOfficialPay(true);
            } else {
                // ???????????????????????????????????????????????????????????????
                mPresenter.getPayParam(mOrgCode);
            }
        } else if (TO_STATE2.equals(mCurrentToState)) {
            parseOfficialSettleResult(body);
        }
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

    /**
     * ????????????????????????
     */
    private void parseOfficialSettleResult(SettleEntity body) {
        // ?????????????????????
        if (body == null) {
            jumpToPaymentResultPage(false);
            return;
        }

        String payState = body.getPayState();
        if (TextUtils.isEmpty(payState)) {
            LogUtil.e(TAG, "payState is null!");
            return;
        }

        // ??????????????????~
        switch (payState) {
            // 1?????????????????????????????????????????????
            case "1":
                // ?????? 3 ???????????????????????????????????????
                if (mOfficeSettleTimes < 3) {
                    mOfficeSettleTimes++;
                    waitingAndOnceAgain();
                } else {
                    showLoading(false);
                    PaymentDetailsActivity.this.finish();
                }
                break;
            // 2???????????????????????????????????????
            case "2":
                String feeTotal = body.getFee_total();
                String feeCashTotal = body.getFee_cash_total();
                String feeYbTotal = body.getFee_yb_total();
                LogUtil.i(TAG, "feeTotal===" + feeTotal + ",feeCashTotal===" + feeCashTotal + ",feeYbTotal===" + feeYbTotal);
                // ???????????????????????? null????????????????????????????????????????????????????????? token ?????????
                if (!TextUtils.isEmpty(feeTotal) && !TextUtils.isEmpty(feeCashTotal) && !TextUtils.isEmpty(feeYbTotal)) {
                    // ??????????????????????????????????????? true ????????????????????????
                    jumpToPaymentResultPage(true);
                }
                break;
            // 3?????????????????????????????????????????????
            case "3":
                jumpToPaymentResultPage(false);
                break;
            default:
                break;
        }
    }

    private void waitingAndOnceAgain() {
        showLoading(true);
        mCompositeDisposable.add(
                Observable
                        .timer(5, TimeUnit.SECONDS)
                        .subscribe(aLong -> {
                            mCurrentToState = TO_STATE2;
                            sendOfficialPay(false);
                        })
        );
    }

    /**
     * ???????????????????????????
     *
     * @param isSuccess true ?????????????????????false ??????????????????
     */
    private void jumpToPaymentResultPage(boolean isSuccess) {
        PaymentResultActivity.actionStart(PaymentDetailsActivity.this, isSuccess, true,
                mOrgName, mFeeTotal, mFeeCashTotal, mFeeYbTotal);
    }

    public void showSelectPayTypeWindow(PaymentDetailsAdapter.OnCheckedCallback onCheckedCallback) {
        mOnCheckedCallback = onCheckedCallback;
        BrightnessManager.lightoff(this);
        if (mSelectPayTypeWindow != null) {
            mSelectPayTypeWindow.show();
        }
    }

    /**
     * ????????????????????????
     */
    public void setPersonalPayAmount(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            // ?????????????????????????????????
            mFeeCashTotal = amount;
            tvTextYuan.setVisibility(View.VISIBLE);
            tvMoneyNum.setText(amount);
        }
    }

    @Override
    public void showLoading(boolean show) {
        mCompositeDisposable.add(
                Observable
                        .just(show)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                mLoading.showLoadingDialog();
                            } else {
                                mLoading.dismissLoadingDialog();
                            }
                        })
        );
    }

    /**
     * ??????????????????
     *
     * @param isPureYiBao ??????????????????
     */
    private void sendOfficialPay(boolean isPureYiBao) {
        mPresenter.sendOfficialPay(isPureYiBao, mCurrentToState, mYiBaoToken, mOrgCode,
                SettleUtil.getOfficialSettleParam(details));
    }

    /**
     * ?????????????????????
     */
    private void tryToSettle(String siCardCode) {
        mPresenter.tryToSettle(siCardCode, mOrgCode, SettleUtil.getTryToSettleParam(mPayPlatTradeNo, details));
    }

    public void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.refreshAdapter();
        }
    }

    /**
     * ??????????????????
     */
    public void getOrderDetails(String hisOrderNo, int position) {
        mClickItemPosition = position;
        mPresenter.getOrderDetails(hisOrderNo, mOrgCode);
    }

    /**
     * ??????????????? action
     *
     * @param context  ?????????
     * @param orgCode  ????????????
     * @param orgName  ????????????
     * @param isFinish ????????????????????????????????????
     */
    public static void actionStart(Context context, String orgCode, String orgName, boolean isFinish) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PaymentDetailsActivity.class);
        intent.putExtra(IntentExtra.ORG_CODE, orgCode);
        intent.putExtra(IntentExtra.ORG_NAME, orgName);
        context.startActivity(intent);
        if (isFinish) {
            ((Activity) context).finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ?????????????????????????????????
        WDPay.ReleasePayserver();
        // ???????????????????????? mYiBaoToken ??? mYiBaoToken time ??????
        SpUtil.getInstance().save(SpKey.YIBAO_TOKEN, "");
        SpUtil.getInstance().save(SpKey.TOKEN_TIME, "");
        if (mLoading != null) {
            mLoading.dispose();
        }
    }

    public void requestTryToSettleToken(String businessType) {
        mBusinessType = businessType;
        mPresenter.applyElectronicSocialSecurityCardToken(mBusinessType);
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
        ZjEsscSDK.startSdk(PaymentDetailsActivity.this, idCard, name, url, s, new SdkCallBack() {
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
}
