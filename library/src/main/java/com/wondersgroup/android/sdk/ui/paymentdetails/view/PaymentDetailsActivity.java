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
import com.wondersgroup.android.sdk.utils.WondersExternImp;
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
 * Function:缴费详情页面
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
    private String mHiCode;//医院前置机分配的医院编码
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
     * 是否是试结算失败时需要去结算
     */
    private boolean isNeedToPay = false;
    /**
     * 组合 Item 数据的集合
     */
    private List<CombineDetailsBean> mCombineList = new ArrayList<>();
    /**
     * 正式结算次数
     */
    private int mOfficeSettleTimes = 0;
    /**
     * toState 1 保存 token 2 正式结算
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

        // 如果是门诊才需要显示温馨提示，如果是自费卡不需要显示
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
            mHiCode = intent.getStringExtra(IntentExtra.HI_CODE);
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

        // 获取未结清账单详情
        mPresenter.requestYd0003(mOrgCode);
    }

    /**
     * 设置点击事件的监听回调
     */
    private void initListener() {
        tvPayMoney.setOnClickListener(v -> toPayMoney());
        titleBar.setOnBackListener(this::showAlertDialog);
    }

    /**
     * 点击立即付款时的处理
     */
    private void toPayMoney() {
        // 判断是否已经在结算中，就提示，防止再次点击！
        if (mLoading != null && mLoading.isShowing()) {
            WToastUtil.show("正在处理中，请勿重复点击！");
            return;
        }

        // 如果是门诊才需要获取医保 token，如果是自费卡不需要获取
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            // 判断试结算是否成功
            if (tryToSettleIsSuccess) {
                getOfficialToSettleToken();
            } else {
                isNeedToPay = true;
                getTryToSettleToken();
            }
        } else if ("2".equals(cardType)) {
            // 直接进行现金部分自费结算，先获取统一支付所需的参数
            LogUtil.i(TAG, "mOrgCode===" + mOrgCode);
            mPresenter.getPayParam(mOrgCode);
        }
    }

    /**
     * 查询并电子社保卡开通状态，如果开通了就继续获取试结算的 token
     */
    private void queryEleCardOpenStatus() {
        String eleCardStatus = SpUtil.getInstance().getString(SpKey.ELE_CARD_STATUS, "");
        // 已开通
        if ("01".equals(eleCardStatus)) {
            getTryToSettleToken();
        }
    }

    private void getTryToSettleToken() {
        // 如果没成功，就先携带 token 再发起一次试结算，然后再进行支付
        EpSoftUtils.getTryToSettleToken(this, this::tryToSettle);
    }

    private void getOfficialToSettleToken() {
        EpSoftUtils.getOfficialToSettleToken(this, this::officialSettle);
    }

    private void officialSettle(String token) {
        mYiBaoToken = token;
        LogUtil.i(TAG, "onYiBaoTokenResult() -> token===" + token);
        if (!TextUtils.isEmpty(mFeeCashTotal)) {
            // 发起正式结算保存 token
            mCurrentToState = TO_STATE1;
            sendOfficialPay(false);
        } else {
            LogUtil.e(TAG, "to pay money failed, because mFeeCashTotal is null!");
        }
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage(getString(R.string.wonders_group_personal_pay_back_notice2))
                .setPositiveButton("确定", (dialog, which) -> PaymentDetailsActivity.this.finish())
                .setNegativeButton("取消", null)
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
            // 初始化自费部分结算的金额
            mFeeTotal = entity.getFeeTotal();
            mFeeCashTotal = entity.getFeeTotal();
            mFeeYbTotal = "0";

            List<HashMap<String, String>> detailsList = new ArrayList<>();
            details = entity.getDetails();
            // 转换为组合数据
            getCombineListData(details);
            // 添加列表数据
            mItemList.addAll(mCombineList);
            // 添加支付数据
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

            // 调用锁单接口
            mPresenter.lockOrder(map, detailsList.size());
        }
    }

    /**
     * 获取 List 的组合数据
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
                            // 锁单成功后刷新订单号
                            mHeadBean.setOrderNum(s);
                            refreshAdapter();
                            continueSettle();
                        })
        );
    }

    /**
     * 锁单成功后继续发起结算
     */
    private void continueSettle() {
        // 如果是门诊才需要进行试结算，如果是自费卡不需要试结算
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            queryEleCardOpenStatus();
        } else {
            // 显示需要结算的金额
            tvPayName.setText("需现金支付：");
            // 显示现金需要支付的金额
            tvTextYuan.setVisibility(View.VISIBLE);
            tvMoneyNum.setText(mFeeCashTotal);

            mDetailPayBean.setTotalPay(mFeeTotal);
            mDetailPayBean.setPersonalPay(mFeeCashTotal);
            mDetailPayBean.setYibaoPay(mFeeYbTotal);

            // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
            if (mItemList.size() > 0) {
                mItemList.clear();
            }
            // 先添加头部数据
            mItemList.add(mHeadBean);
            // 再添加 List 数据
            mItemList.addAll(mCombineList);
            // 添加支付数据
            mItemList.add(mDetailPayBean);
            refreshAdapter();
        }
    }

    /**
     * 订单明细列表结果回调
     */
    @Override
    public void onOrderDetailsResult(OrderDetailsEntity entity) {
        if (entity != null) {
            List<OrderDetailsEntity.DetailsBean> details = entity.getDetails();
            if (details.size() > 0) {
                // List 数据从 1 开始，需要减去头部的位置 1
                mCombineList.get(mClickItemPosition - 1).setOpenDetails(details);
                mCombineList.get(mClickItemPosition - 1).setSpread(true);
                // 刷新适配器
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
        // 判断如果个人支付为 0 时，显示医保支付金额
        if (Double.parseDouble(mFeeCashTotal) == 0) {
            tvPayName.setText("需医保支付：");
            tvMoneyNum.setText(mFeeYbTotal);
        } else {
            tvPayName.setText("需现金支付：");
            // 显示现金需要支付的金额
            tvMoneyNum.setText(mFeeCashTotal);
        }

        if (mDetailPayBean == null) {
            mDetailPayBean = new DetailPayBean();
        }
        mDetailPayBean.setTotalPay(mFeeTotal);
        mDetailPayBean.setPersonalPay(mFeeCashTotal);
        mDetailPayBean.setYibaoPay(mFeeYbTotal);

        // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
        if (mItemList.size() > 0) {
            mItemList.clear();
        }
        // 先添加头部数据
        mItemList.add(mHeadBean);
        // 再添加 List 数据
        mItemList.addAll(mCombineList);
        // 添加支付数据
        mItemList.add(mDetailPayBean);
        refreshAdapter();

        // 判断是否是试结算失败时，然后再次去支付的。
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
        // 发起万达统一支付，支付现金部分
        mCompositeDisposable.add(
                WdCommonPayUtils.toPay(this, paramEntity)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                            showLoading(false);
                            WToastUtil.show("SUCCESS".equals(s) ? "支付成功~" : s);
                            if ("SUCCESS".equals(s)) {
                                // 支付成功后发起正式结算
                                onCashPaySuccess();
                            }
                        }, throwable -> WToastUtil.show(throwable.getMessage()))
        );
    }

    private void onCashPaySuccess() {
        // 如果是门诊才需要进行试结算，如果是自费卡不需要试结算
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            // 现金部分结算成功，继续支付医保部分(正式结算，不管医保是否为0)
            if (!TextUtils.isEmpty(mFeeYbTotal)) {
                mCurrentToState = TO_STATE2;
                sendOfficialPay(false);
            } else {
                LogUtil.e(TAG, "to pay money failed, because mFeeYbTotal is null!");
            }
        } else if ("2".equals(cardType)) {
            // 发起正式结算，token 传 0
            mCurrentToState = TO_STATE2;
            mPresenter.sendOfficialPay(false, mCurrentToState, "0", mOrgCode,
                    SettleUtil.getOfficialSettleParam(details));
        }
    }

    @Override
    public void onOfficialSettleResult(SettleEntity body) {
        if (TO_STATE1.equals(mCurrentToState)) {
            /*
             * 保存完 token 后，如果个人支付为 0，携带 mYiBaoToken，直接调用正式结算接口发起正式结算，
             * 如果不为 0，那就先个人支付(统一支付)，再进行医保支付
             */
            if (Double.parseDouble(mFeeCashTotal) == 0) {
                mCurrentToState = TO_STATE2;
                // 携带 mYiBaoToken 发起正式结算
                sendOfficialPay(true);
            } else {
                // 进行现金部分结算，先获取统一支付所需的参数
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
     * 解析正式结算结果
     */
    private void parseOfficialSettleResult(SettleEntity body) {
        // 正式结算失败！
        if (body == null) {
            jumpToPaymentResultPage(false);
            return;
        }

        String payState = body.getPayState();
        if (TextUtils.isEmpty(payState)) {
            LogUtil.e(TAG, "payState is null!");
            return;
        }

        // 正式结算成功~
        switch (payState) {
            // 1、后台正在异步结算（前台等待）
            case "1":
                // 重试 3 次，如果还是失败就返回首页
                if (mOfficeSettleTimes < 3) {
                    mOfficeSettleTimes++;
                    waitingAndOnceAgain();
                } else {
                    showLoading(false);
                    PaymentDetailsActivity.this.finish();
                }
                break;
            // 2、结算完成（返回成功页面）
            case "2":
                String feeTotal = body.getFee_total();
                String feeCashTotal = body.getFee_cash_total();
                String feeYbTotal = body.getFee_yb_total();
                LogUtil.i(TAG, "feeTotal===" + feeTotal + ",feeCashTotal===" + feeCashTotal + ",feeYbTotal===" + feeYbTotal);
                // 如果全部金额不为 null，说明是发起正式结算的回调，否则是上传 token 的回调
                if (!TextUtils.isEmpty(feeTotal) && !TextUtils.isEmpty(feeCashTotal) && !TextUtils.isEmpty(feeYbTotal)) {
                    // 跳转过去，显示全部支付完成 true 代表全部支付完成
                    jumpToPaymentResultPage(true);
                }
                break;
            // 3、结算失败（包括超时自动处理）
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
     * 跳转到支付结果界面
     *
     * @param isSuccess true 正式结算成功，false 正式结算失败
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
     * 设置个人支付金额
     */
    public void setPersonalPayAmount(String amount) {
        if (!TextUtils.isEmpty(amount)) {
            // 设置个人需要支付的金额
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
     * 发起正式结算
     *
     * @param isPureYiBao 是否是纯医保
     */
    private void sendOfficialPay(boolean isPureYiBao) {
        mPresenter.sendOfficialPay(isPureYiBao, mCurrentToState, mYiBaoToken, mOrgCode,
                SettleUtil.getOfficialSettleParam(details));
    }

    /**
     * 发起试结算请求
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
     * 获取账单明细
     */
    public void getOrderDetails(String hisOrderNo, int position) {
        mClickItemPosition = position;
        mPresenter.getOrderDetails(hisOrderNo, mOrgCode);
    }

    /**
     * 页面跳转的 action
     *
     * @param context  上下文
     * @param orgCode  机构代码
     * @param orgName  机构名称
     * @param isFinish 是否需要销毁跳转前的页面
     */
    public static void actionStart(Context context, String orgCode, String orgName, String hiCode, boolean isFinish) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, PaymentDetailsActivity.class);
        intent.putExtra(IntentExtra.ORG_CODE, orgCode);
        intent.putExtra(IntentExtra.ORG_NAME, orgName);
        intent.putExtra(IntentExtra.HI_CODE, hiCode);
        context.startActivity(intent);
        if (isFinish) {
            ((Activity) context).finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 统一支付平台，调用结束
        WDPay.ReleasePayserver();
        // 页面销毁将保存的 mYiBaoToken 和 mYiBaoToken time 清空
        SpUtil.getInstance().save(SpKey.YIBAO_TOKEN, "");
        SpUtil.getInstance().save(SpKey.TOKEN_TIME, "");
        if (mLoading != null) {
            mLoading.dispose();
        }
    }

    public void requestTryToSettleToken(String businessType) {
        mBusinessType = businessType;
        mPresenter.applyElectronicSocialSecurityCardToken(mBusinessType,mHiCode);
    }

    public void checkElectronicSocialSecurityCardPassword() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String idNum = SpUtil.getInstance().getString(SpKey.ID_NUM, "");

//        ElectronicSocialSecurityCard.getSign(
//                ElectronicSocialSecurityCard.getVerifyElectronicSocialSecurityCardPasswordParams(),
//                s -> startSdk(idNum, name, s)
//        );
        // TODO: 2021/5/6 外部修改参数获取记录
        startSdk(idNum, name, WondersExternImp.getExternParams().getSign());
    }

    /**
     * 启动SDK
     *
     * @param idCard 身份证
     * @param name   姓名
     * @param s      签名
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
     * 独立服务回调处理
     */
    private void handleScene(String data) {
        EleCardEntity eleCardEntity = new Gson().fromJson(data, EleCardEntity.class);
        String actionType = eleCardEntity.getActionType();
        // 密码验证
        if ("009".equals(actionType)) {
            ZjEsscSDK.closeSDK();
            String busiSeq = eleCardEntity.getBusiSeq();
            SpUtil.getInstance().save(SpKey.BUSI_SEQ, busiSeq);
            requestTryToSettleToken(OrgConfig.SRJ);
        }
    }
}
