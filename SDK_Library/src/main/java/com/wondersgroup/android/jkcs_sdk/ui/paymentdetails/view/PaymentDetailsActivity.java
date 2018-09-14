package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view;

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
import com.epsoft.hzauthsdk.utils.ToastUtils;
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
import com.wondersgroup.android.jkcs_sdk.ui.adapter.DetailsAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.DetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter.DetailsPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.NumberUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.iwgang.countdownview.CountdownView;

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
    private int mClickItemPos = -1; // 记录点击的 Item 的位置
    private List<CombineDetailsBean> mCombineList = new ArrayList<>(); // 组合 Item 数据的集合

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

        Intent intent = getIntent();
        if (intent != null) {
            mOrgCode = intent.getStringExtra(IntentExtra.ORG_CODE);
            mOrgName = intent.getStringExtra(IntentExtra.ORG_NAME);
        }

        // mOrgCode 暂时写死
        // 中心医院(朱凯)：47117170333050211A1001
        // 第一医院(陆晓明)：47117166633050211A1001
        // mOrgCode = "47117166633050211A1001";

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
        mHeadBean.setOrderNum("92829389283");
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
                getYiBaoToken();
            }
        });
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
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
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
                detailItem.put(MapKey.FEE_ORDER, NumberUtil.twoBitDecimal(detailsBean.getFee_order()));
                detailItem.put(MapKey.ORDER_NAME, detailsBean.getOrdername());
                detailsList.add(detailItem);
            }

            if (detailsList.size() > 0) {
                map.put(MapKey.DETAILS, detailsList);
            }

            // 调用锁单接口
            mPresenter.lockOrder(map, detailsList.size());

            getYiBaoToken();
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
            String payPlatTradeNo = entity.getPayplat_tradno();
            LogUtil.i(TAG, "lockStartTime===" + lockStartTime + ",payPlatTradeNo===" + payPlatTradeNo);
            SpUtil.getInstance().save(SpKey.LOCK_START_TIME, lockStartTime);
            SpUtil.getInstance().save(SpKey.PAY_PLAT_TRADE_NO, payPlatTradeNo);

            long countDownMillis = TimeUtil.getCountDownMillis(lockStartTime);
            countDownView.start(countDownMillis);
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
                    // 发起试结算
                    mPresenter.tryToSettle(siCardCode, mOrgCode);
                }
            }
        });
    }

    private void openYiBaoKeyBoard() {
        AuthCall.getToken(PaymentDetailsActivity.this, MakeArgsFactory.getKeyboardArgs(), new AuthCall.CallBackListener() {
            @Override
            public void callBack(String result) {
                ToastUtils.showToast(PaymentDetailsActivity.this, result);
            }
        });
    }
}
