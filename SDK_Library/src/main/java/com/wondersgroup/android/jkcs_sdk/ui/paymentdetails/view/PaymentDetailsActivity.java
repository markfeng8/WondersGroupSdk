package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.epsoft.hzauthsdk.bean.GetTokenBean;
import com.epsoft.hzauthsdk.bean.OpenStatusBean;
import com.epsoft.hzauthsdk.utils.MakeArgsFactory;
import com.google.gson.Gson;
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
import com.wondersgroup.android.jkcs_sdk.ui.adapter.DetailsAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.DetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter.DetailsPresenter;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.view.PersonalPayActivity;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NumberUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.SelectPayTypeWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;
import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDCallBack;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;
import cn.wd.checkout.api.WDReqParams;
import cn.wd.checkout.api.WDResult;

// 缴费详情页面
public class PaymentDetailsActivity extends MvpBaseActivity<DetailsContract.IView,
        DetailsPresenter<DetailsContract.IView>> implements DetailsContract.IView {

    private static final String TAG = "PaymentDetailsActivity";
    private RecyclerView recyclerView; // 使用分类型的 RecyclerView 来实现
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private CountdownView countDownView;
    private View activityView;
    private String mOrgCode;
    private String mOrgName;
    private DetailHeadBean mHeadBean;
    private List<Object> mItemList = new ArrayList<>();
    private DetailsAdapter mAdapter;
    private DetailPayBean mDetailPayBean;
    private LoadingView mLoading;
    private SelectPayTypeWindow mSelectPayTypeWindow;
    private int mClickItemPos = -1; // 记录点击的 Item 的位置
    private List<CombineDetailsBean> mCombineList = new ArrayList<>(); // 组合 Item 数据的集合
    private int mPayType = 1;
    private DetailsAdapter.OnCheckedCallback mOnCheckedCallback;
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

    // 支付结果返回入口
    private WDCallBack bcCallback = new WDCallBack() {
        @Override
        public void done(final WDResult bcResult) {
            final WDPayResult bcPayResult = (WDPayResult) bcResult;
            PaymentDetailsActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String result = bcPayResult.getResult();
                    LogUtil.i(TAG, "done result=" + result);
                    if (result.equals(WDPayResult.RESULT_SUCCESS)) {
                        WToastUtil.show("支付成功~");
                        // 传递参数过去
                        PersonalPayActivity.actionStart(PaymentDetailsActivity.this,
                                mOrgName, mOrgCode, mFeeTotal, mFeeCashTotal, mFeeYbTotal, getOfficialSettleParam());
                    } else if (result.equals(WDPayResult.RESULT_CANCEL)) {
                        WToastUtil.show("用户取消支付");
                    } else if (result.equals(WDPayResult.RESULT_FAIL)) {
                        String info = "支付失败, 原因: " + bcPayResult.getErrMsg() + ", " + bcPayResult.getDetailInfo();
                        WToastUtil.show(info);
                    } else if (result.equals(WDPayResult.FAIL_UNKNOWN_WAY)) {
                        WToastUtil.show("未知支付渠道");
                    } else if (result.equals(WDPayResult.FAIL_WEIXIN_VERSION_ERROR)) {
                        WToastUtil.show("针对微信 支付版本错误（版本不支持）");
                    } else if (result.equals(WDPayResult.FAIL_EXCEPTION)) {
                        WToastUtil.show("支付过程中的Exception");
                    } else if (result.equals(WDPayResult.FAIL_ERR_FROM_CHANNEL)) {
                        WToastUtil.show("从第三方app支付渠道返回的错误信息，原因: " + bcPayResult.getErrMsg());
                    } else if (result.equals(WDPayResult.FAIL_INVALID_PARAMS)) {
                        WToastUtil.show("参数不合法造成的支付失败");
                    } else if (result.equals(WDPayResult.RESULT_PAYING_UNCONFIRMED)) {
                        WToastUtil.show("表示支付中，未获取确认信息");
                    } else {
                        WToastUtil.show("invalid return");
                    }
                }
            });
        }
    };

    @Override
    protected DetailsPresenter<DetailsContract.IView> createPresenter() {
        return new DetailsPresenter<>();
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
                .setDropView(activityView)
                .build();

        mSelectPayTypeWindow = new SelectPayTypeWindow.Builder(this)
                .setDropView(activityView)
                .setListener(onLoadingListener)
                .setCheckedListener(mCheckedListener)
                .build();

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
        }

        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.ORG_CODE, mOrgCode);
        map.put(MapKey.PAGE_NUMBER, "1");
        map.put(MapKey.PAGE_SIZE, "10");
        // 获取未结清账单详情
        mPresenter.getUnclearedBill(map);

        // ------------------下面是一些写死的数据-----------------
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String cardNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        //String hospitalName = "中心医院";

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

    private void initListener() {
        tvPayMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点付款时，需要查询用户的医保移动支付是否开通？如果未开通就提示开通
                //getYiBaoToken();
                // 获取支付所需的参数
                mPresenter.getPayParam(mOrgCode);
            }
        });
        countDownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                tvPayMoney.setEnabled(false);
            }
        });
    }

    private void toPayMoney(String appId, String subMerNo, String apiKey) {
        CheckOut.setIsPrint(true);
        CheckOut.setNetworkWay("");

        Long i = 0L;

        long formatCents = (long) (Double.parseDouble(mFeeCashTotal) * 100);

        if (isNumeric(String.valueOf(formatCents))) {
            i = Long.parseLong(String.valueOf(formatCents));
        } else {
            WToastUtil.show("请输入正确的交易金额（单位：分）");
            return;
        }

        WDPay.reqPayAsync(PaymentDetailsActivity.this,
                appId, apiKey,
                getWdPayType(), subMerNo,
                mOrgName, // 订单标题
                "药品费", i, // 订单金额(分)
                payPlatTradeNo, // 订单流水号
                "药品费", null, // 扩展参数(可以null)
                bcCallback);
    }

    public boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]+(.[0-9]{1,2})?$");
    }

    /**
     * 获取万达统一支付的支付类型
     */
    private WDReqParams.WDChannelTypes getWdPayType() {
        WDReqParams.WDChannelTypes wdChannelTypes = null;
        if (mPayType == 1) {
            wdChannelTypes = WDReqParams.WDChannelTypes.alipay;
        } else if (mPayType == 2) {
            wdChannelTypes = WDReqParams.WDChannelTypes.wepay;
        } else if (mPayType == 3) {
            wdChannelTypes = WDReqParams.WDChannelTypes.uppaydirect_appand;
        }
        return wdChannelTypes;
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
        activityView = findViewById(R.id.activityView);
        countDownView = findViewById(R.id.countDownView);
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mAdapter = new DetailsAdapter(this, mItemList);
            recyclerView.setAdapter(mAdapter);
            // 设置布局管理器
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
    }

    @Override
    public void feeBillResult(FeeBillEntity entity) {
        if (entity != null) {
            String feeTotal = entity.getFee_total();
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
            map.put(MapKey.FEE_TOTAL, feeTotal);
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
     *
     * @param details
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

            long countDownMillis = TimeUtil.getCountDownMillis(lockStartTime);
            countDownView.start(countDownMillis);

            // 锁单成功后刷新订单号
            mHeadBean.setOrderNum(payPlatTradeNo);
            mItemList.set(0, mHeadBean);
            refreshAdapter();

            // 进行医保移动状态查询并发起试结算
            getMobilePayState();
        }
    }

    /**
     * 订单明细列表结果回调
     *
     * @param entity
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

    @Override
    public void onPayParamResult(PayParamEntity body) {
        if (body != null) {
            String appId = body.getAppid();
            String version = body.getVersion();
            String subMerNo = body.getSubmerno();
            String apiKey = body.getApikey();
            toPayMoney(appId, subMerNo, apiKey);
        }
    }

    public void showSelectPayTypeWindow(DetailsAdapter.OnCheckedCallback onCheckedCallback) {
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
            mLoading.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    public void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.setmItemList(mItemList);
        }
    }

    /**
     * 获取账单明细
     *
     * @param hisOrderNo
     * @param position
     */
    public void getOrderDetails(String hisOrderNo, int position) {
        mClickItemPos = position;
        mPresenter.getOrderDetails(hisOrderNo, mOrgCode);
    }

    /**
     * 查询医保移动支付状态
     */
    public void getMobilePayState() {
        AuthCall.queryOpenStatus(PaymentDetailsActivity.this, MakeArgsFactory.getOpenStatusArgs(), new AuthCall.CallBackListener() {
            @Override
            public void callBack(String result) {
                if (!TextUtils.isEmpty(result)) {
                    LogUtil.i(TAG, "result===" + result);
                    OpenStatusBean statusBean = new Gson().fromJson(result, OpenStatusBean.class);
                    int isYbPay = statusBean.getIsYbPay();
                    if (isYbPay == 1) { // 已开通
                        getYiBaoToken();
                    } else { // 未开通
                        WToastUtil.show("您未开通医保移动支付，不能进行医保结算！");
                    }
                }
            }
        });
    }

    private void getYiBaoToken() {
        AuthCall.getToken(PaymentDetailsActivity.this, MakeArgsFactory.getTokenArgs(), new AuthCall.CallBackListener() {
            @Override
            public void callBack(String result) {
                LogUtil.i(TAG, "result===" + result);
                if (!TextUtils.isEmpty(result)) {
                    GetTokenBean bean = new Gson().fromJson(result, GetTokenBean.class);
                    String siCardCode = bean.getSiCardCode();
                    LogUtil.i(TAG, "siCardCode===" + siCardCode);
                    if (siCardCode != null) {
                        tryToSettle(siCardCode);
                    }
                }
            }
        });
    }

    /**
     * 发起试结算请求
     *
     * @param siCardCode
     */
    private void tryToSettle(String siCardCode) {
        HashMap<String, Object> map = new HashMap<>();
        List<HashMap<String, String>> detailsList = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            FeeBillEntity.DetailsBean detailsBean = details.get(i);
            HashMap<String, String> detailItem = new HashMap<>();
            detailItem.put(MapKey.HIS_ORDER_NO, detailsBean.getHis_order_no());
            detailItem.put(MapKey.ORDER_NO, payPlatTradeNo);
            detailsList.add(detailItem);
        }

        if (detailsList.size() > 0) {
            map.put(MapKey.DETAILS, detailsList);
        }

        // 发起试结算
        mPresenter.tryToSettle(siCardCode, mOrgCode, map);
    }

    /**
     * 获取发起正式结算时的参数
     */
    private HashMap<String, Object> getOfficialSettleParam() {
        HashMap<String, Object> map = new HashMap<>();
        List<HashMap<String, String>> detailsList = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            FeeBillEntity.DetailsBean detailsBean = details.get(i);
            HashMap<String, String> detailItem = new HashMap<>();
            detailItem.put(MapKey.HIS_ORDER_NO, detailsBean.getHis_order_no());
            detailItem.put(MapKey.ORDER_NO, "1");
            detailsList.add(detailItem);
        }

        if (detailsList.size() > 0) {
            map.put(MapKey.DETAILS, detailsList);
        }

        return map;
    }

    private void openYiBaoKeyBoard() {
        AuthCall.getToken(PaymentDetailsActivity.this, MakeArgsFactory.getKeyboardArgs(),
                result -> {
                    LogUtil.i(TAG, "result===" + result);
                    WToastUtil.show(String.valueOf(result));
                });
    }

    public static void actionStart(Context context, String orgCode, String orgName) {
        if (context != null) {
            Intent intent = new Intent(context, PaymentDetailsActivity.class);
            intent.putExtra(IntentExtra.ORG_CODE, orgCode);
            intent.putExtra(IntentExtra.ORG_NAME, orgName);
            context.startActivity(intent);
            ((Activity) context).finish();
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
    }

}
