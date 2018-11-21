package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.CombineDetailsBean;
import com.wondersgroup.android.jkcs_sdk.entity.DetailHeadBean;
import com.wondersgroup.android.jkcs_sdk.entity.DetailPayBean;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.entity.PayParamEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;
import com.wondersgroup.android.jkcs_sdk.adapter.PaymentDetailsAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.PaymentDetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter.PaymentDetailsPresenter;
import com.wondersgroup.android.jkcs_sdk.ui.paymentresult.view.PaymentResultActivity;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NumberUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SettleUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.SelectPayTypeWindow;
import com.wondersgroup.android.jkcs_sdk.widget.TitleBarLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.wd.checkout.api.WDPay;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:缴费详情页面
 */
public class PaymentDetailsActivity extends MvpBaseActivity<PaymentDetailsContract.IView,
        PaymentDetailsPresenter<PaymentDetailsContract.IView>> implements PaymentDetailsContract.IView {

    private static final String TAG = "PaymentDetailsActivity";
    private RecyclerView recyclerView; // 使用分类型的 RecyclerView 来实现
    private TextView tvPayName;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private TextView tvNotice;
    private View activityView;
    private TitleBarLayout titleBar;
    private String mOrgCode;
    private String mOrgName;
    private DetailHeadBean mHeadBean;
    private List<Object> mItemList = new ArrayList<>();
    private PaymentDetailsAdapter mAdapter;
    private DetailPayBean mDetailPayBean;
    private LoadingView mLoading;
    private SelectPayTypeWindow mSelectPayTypeWindow;
    private int mClickItemPos = -1; // 记录点击的 Item 的位置
    private List<CombineDetailsBean> mCombineList = new ArrayList<>(); // 组合 Item 数据的集合
    private int mPayType = 1;
    private PaymentDetailsAdapter.OnCheckedCallback mOnCheckedCallback;
    private String mFeeTotal;
    private String mFeeCashTotal;
    private String mFeeYbTotal;
    private String payPlatTradeNo;

    private SelectPayTypeWindow.OnCheckedListener mCheckedListener = type -> {
        mPayType = type;
        if (mOnCheckedCallback != null) {
            mOnCheckedCallback.onSelected(mPayType);
        }
    };
    private SelectPayTypeWindow.OnLoadingListener onLoadingListener =
            () -> BrightnessManager.lighton(PaymentDetailsActivity.this);
    private List<FeeBillEntity.DetailsBean> details;
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
        mLoading = new LoadingView.Builder(this)
                .build();

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
        }

        // 获取未结清账单详情
        mPresenter.requestYd0003(mOrgCode);

        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");

        mHeadBean = new DetailHeadBean();
        mHeadBean.setName(name);
        mHeadBean.setOrderNum(payPlatTradeNo);
        mHeadBean.setSocialNum(cardNum);
        mHeadBean.setHospitalName(mOrgName);

        mDetailPayBean = new DetailPayBean();
        mDetailPayBean.setTotalPay("");
        mDetailPayBean.setPersonalPay("");
        mDetailPayBean.setYibaoPay("");

        mItemList.add(mHeadBean);
        setAdapter();
    }

    /**
     * 设置点击事件的监听回调
     */
    private void initListener() {
        tvPayMoney.setOnClickListener(v -> {
            // 处理已经在结算中的再次点击
            if (mLoading != null && mLoading.isShowing()) {
                WToastUtil.show("正在处理中，请勿重复点击！");
            } else {
                // 如果是门诊才需要获取医保 token，如果是自费卡不需要获取
                String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
                if ("0".equals(cardType)) {
                    mPresenter.getYiBaoToken(PaymentDetailsActivity.this);
                } else if ("2".equals(cardType)) {
                    // 直接进行现金部分自费结算，先获取统一支付所需的参数
                    mPresenter.getPayParam(mOrgCode);
                }
            }
        });
        titleBar.setOnBackListener(this::showAlertDialog);
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
            mFeeTotal = entity.getFee_total();
            mFeeCashTotal = entity.getFee_total();
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
                FeeBillEntity.DetailsBean detailsBean = details.get(i);
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
    private void getCombineListData(List<FeeBillEntity.DetailsBean> details) {
        for (int i = 0; i < details.size(); i++) {
            CombineDetailsBean bean = new CombineDetailsBean();
            bean.setDefaultDetails(details.get(i));
            mCombineList.add(bean);
        }
    }

    @Override
    public void lockOrderResult(LockOrderEntity entity) {
        if (entity != null) {
            String lockStartTime = entity.getLock_start_time();
            payPlatTradeNo = entity.getPayplat_tradno();
            LogUtil.i(TAG, "lockStartTime===" + lockStartTime + ",payPlatTradeNo===" + payPlatTradeNo);
            SpUtil.getInstance().save(SpKey.LOCK_START_TIME, lockStartTime);
            SpUtil.getInstance().save(SpKey.PAY_PLAT_TRADE_NO, payPlatTradeNo);
            // 锁单成功后刷新订单号
            mHeadBean.setOrderNum(payPlatTradeNo);
            mItemList.set(0, mHeadBean);
            refreshAdapter();

            // 如果是门诊才需要进行试结算，如果是自费卡不需要试结算
            String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
            if ("0".equals(cardType)) {
                // 进行医保移动状态查询并发起试结算
                mPresenter.queryYiBaoOpenStatus(PaymentDetailsActivity.this);
            } else {
                // 显示需要结算的金额
                tvPayName.setText("需现金支付：");
                // 显示现金需要支付的金额
                tvMoneyNum.setText(mFeeCashTotal);

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
                mItemList.add(mHeadBean); // 先添加头部数据
                mItemList.addAll(mCombineList);// 再添加 List 数据
                mItemList.add(mDetailPayBean); // 添加支付数据
                refreshAdapter();
            }
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
                mCombineList.get(mClickItemPos - 1).setOpenDetails(details);

                // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
                if (mItemList.size() > 0) {
                    mItemList.clear();
                }
                mItemList.add(mHeadBean); // 先添加头部数据
                mItemList.addAll(mCombineList);// 再添加 List 数据
                mItemList.add(mDetailPayBean); // 添加支付数据
                refreshAdapter();
            }
        }
    }

    @Override
    public void onTryToSettleResult(SettleEntity body) {
        if (body != null) {
            mFeeTotal = body.getFee_total();
            mFeeCashTotal = body.getFee_cash_total();
            mFeeYbTotal = body.getFee_yb_total();
            LogUtil.i(TAG, "mFeeTotal===" + mFeeTotal + ",mFeeCashTotal==="
                    + mFeeCashTotal + ",mFeeYbTotal===" + mFeeYbTotal);

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
            mItemList.add(mHeadBean); // 先添加头部数据
            mItemList.addAll(mCombineList);// 再添加 List 数据
            mItemList.add(mDetailPayBean); // 添加支付数据
            refreshAdapter();
        }
    }

    @Override
    public void onPayParamResult(PayParamEntity body) {
        if (body != null) {
            String appId = body.getAppid();
            String version = body.getVersion();
            String subMerNo = body.getSubmerno();
            String apiKey = body.getApikey();
            LogUtil.i(TAG, "appId===" + appId + ",version===" + version + ",subMerNo===" + subMerNo + ",apiKey===" + apiKey);
            // 发起万达统一支付，支付现金部分
            mPresenter.toSettleCashPay(PaymentDetailsActivity.this, appId, subMerNo, apiKey,
                    mOrgName, payPlatTradeNo, mPayType, mFeeCashTotal);
        }
    }

    @Override
    public void onOfficialSettleResult(SettleEntity body) {
        // 正式结算成功~
        if (body != null) {
            String feeTotal = body.getFee_total();
            String feeCashTotal = body.getFee_cash_total();
            String feeYbTotal = body.getFee_yb_total();
            LogUtil.i(TAG, "feeTotal===" + feeTotal + ",feeCashTotal===" + feeCashTotal + ",feeYbTotal===" + feeYbTotal);
            // 如果全部金额不为 null，说明时发起正式结算的回调，否则是上传 token 的回调
            if (!TextUtils.isEmpty(feeTotal) && !TextUtils.isEmpty(feeCashTotal) && !TextUtils.isEmpty(feeYbTotal)) {
                // 跳转过去，显示全部支付完成 true 代表全部支付完成
                jumpToPaymentResultPage(true);
            }
        } else { // 正式结算失败！
            jumpToPaymentResultPage(false);
        }
    }

    /**
     * 跳转到支付结果界面
     *
     * @param isSuccess true 正式结算成功，false 正式结算失败
     */
    private void jumpToPaymentResultPage(boolean isSuccess) {
        PaymentResultActivity.actionStart(PaymentDetailsActivity.this, isSuccess, true,
                mOrgName, mOrgCode, mFeeTotal, mFeeCashTotal, mFeeYbTotal);
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
            tvMoneyNum.setText(amount);
        }
    }

    @Override
    public void showLoading() {
        if (mLoading != null) {
            mLoading.showLoadingDialog();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismissLoadingDialog();
        }
    }

    /**
     * 获取医保 mYiBaoToken 的回调
     */
    @Override
    public void onYiBaoTokenResult(String token) {
        this.mYiBaoToken = token;
        LogUtil.i(TAG, "onYiBaoTokenResult() -> mYiBaoToken===" + token);
        if (!TextUtils.isEmpty(mFeeCashTotal)) {
            // 发起正式结算保存 token
            sendOfficialPay(false, "1");
            /*
             * 如果个人支付为 0，携带 mYiBaoToken，直接调用正式结算接口发起正式结算，如果不为 0，
             * 那就先个人支付(统一支付)，再进行医保支付
             */
            if (Double.parseDouble(mFeeCashTotal) == 0) {
                // 携带 mYiBaoToken 发起正式结算
                sendOfficialPay(true, "2");
            } else {
                // 进行现金部分结算，先获取统一支付所需的参数
                mPresenter.getPayParam(mOrgCode);
            }
        } else {
            LogUtil.e(TAG, "to pay money failed, because mFeeCashTotal is null!");
        }
    }

    @Override
    public void onCashPaySuccess() {
        // 如果是门诊才需要进行试结算，如果是自费卡不需要试结算
        String cardType = SpUtil.getInstance().getString(SpKey.CARD_TYPE, "");
        if ("0".equals(cardType)) {
            // 现金部分结算成功，继续支付医保部分（如果有）
            if (!TextUtils.isEmpty(mFeeYbTotal)) {
                // 如果医保部分为0，说明已经全部结算完成
                if (Double.parseDouble(mFeeYbTotal) == 0) {
                    // 跳转过去，显示全部支付完成 true 代表全部支付完成
                    jumpToPaymentResultPage(true);

                } else {
                    // 进行医保部分结算，携带 mYiBaoToken 发起正式结算
                    sendOfficialPay(false, "2");
                }
            } else {
                LogUtil.e(TAG, "to pay money failed, because mFeeYbTotal is null!");
            }
        } else if ("2".equals(cardType)) {
            // 发起正式结算，token 传 0
            mPresenter.sendOfficialPay(false, "2", "0", mOrgCode,
                    SettleUtil.getOfficialSettleParam(details));
        }
    }

    @Override
    public void onYiBaoOpenSuccess() {
        mPresenter.getTryToSettleToken(PaymentDetailsActivity.this);
    }

    @Override
    public void onTryToSettleTokenResult(String token) {
        tryToSettle(token);
    }

    /**
     * 发起正式结算
     *
     * @param isPureYiBao 是否是纯医保
     * @param toState     1 保存 token 2 正式结算
     */
    private void sendOfficialPay(boolean isPureYiBao, String toState) {
        mPresenter.sendOfficialPay(isPureYiBao, toState, mYiBaoToken, mOrgCode,
                SettleUtil.getOfficialSettleParam(details));
    }

    /**
     * 发起试结算请求
     */
    private void tryToSettle(String siCardCode) {
        mPresenter.tryToSettle(siCardCode, mOrgCode, SettleUtil.getTryToSettleParam(payPlatTradeNo, details));
    }

    public void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.setItemList(mItemList);
        }
    }

    /**
     * 获取账单明细
     */
    public void getOrderDetails(String hisOrderNo, int position) {
        mClickItemPos = position;
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
    public static void actionStart(Context context, String orgCode, String orgName, boolean isFinish) {
        if (context != null) {
            Intent intent = new Intent(context, PaymentDetailsActivity.class);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            context.startActivity(intent);
            if (isFinish) {
                ((Activity) context).finish();
            }
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 统一支付平台，调用结束
        WDPay.ReleasePayserver();
        if (mLoading != null) {
            mLoading.dispose();
        }
        // 页面销毁将保存的 mYiBaoToken 和 mYiBaoToken time 清空
        SpUtil.getInstance().save(SpKey.YIBAO_TOKEN, "");
        SpUtil.getInstance().save(SpKey.TOKEN_TIME, "");
    }
}
