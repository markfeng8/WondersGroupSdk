package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.OrgConfig;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterHeaderBean;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OpenStatusBean;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.AfterPayAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter.AfterPayHomePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.view.PersonalPayActivity;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.utils.SettleUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.TimeUtil;
import com.wondersgroup.android.jkcs_sdk.widget.DividerItemDecoration;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.SelectHospitalWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 医后付首页
public class AfterPayHomeActivity extends MvpBaseActivity<AfterPayHomeContract.IView,
        AfterPayHomePresenter<AfterPayHomeContract.IView>> implements AfterPayHomeContract.IView {

    private static final String TAG = AfterPayHomeActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private View activityView;
    private LinearLayout llNeedPay;
    private String mPageNumber = "1"; // 页数
    private String mPageSize = "100"; // 每页的条数
    private String mNotice = "温馨提示";
    private LoadingView mLoading;
    private SelectHospitalWindow mSelectHospitalWindow;
    private AfterHeaderBean mHeaderBean;
    private List<Object> mItemList = new ArrayList<>();
    private AfterPayAdapter mAdapter;
    private HashMap<String, String> mPassParamMap;
    private boolean mAfterPayOpenSuccess;
    private String mOrgName;
    private String mOrgCode;
    private int mYd0008Size = -1;
    private List<HospitalEntity.DetailsBean> mHospitalBeanList;
    private SelectHospitalWindow.OnLoadingListener mOnLoadingListener =
            () -> BrightnessManager.lighton(AfterPayHomeActivity.this);
    private String payPlatTradeNo;
    private String mFeeOrgCode;
    private String mFeeOrgName;
    private String mFeeTotals;
    private String mFeeCashTotal;
    private String mFeeYbTotal;

    @Override
    protected AfterPayHomePresenter<AfterPayHomeContract.IView> createPresenter() {
        return new AfterPayHomePresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_after_pay_home);
        findViews();
        initData();
        initListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i(TAG, "onRestart()");
        mAfterPayOpenSuccess = SpUtil.getInstance().getBoolean(SpKey.AFTER_PAY_OPEN_SUCCESS, false);
        if (mAfterPayOpenSuccess) {
            refreshAfterPayState();
        }
    }

    /**
     * 刷新当前医后付签约状态
     */
    private void refreshAfterPayState() {
        mPresenter.getAfterPayState(mPassParamMap);
    }

    private void initListener() {
        tvPayMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterPayHomeActivity.this, PaymentDetailsActivity.class);
                intent.putExtra(IntentExtra.ORG_CODE, mOrgCode);
                intent.putExtra(IntentExtra.ORG_NAME, mOrgName);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        AuthCall.initSDK(AfterPayHomeActivity.this, "6151490102",
                result -> LogUtil.e(TAG, "result===" + result));

        mLoading = new LoadingView.Builder(this)
                .setDropView(activityView)
                .build();

        initHeaderData();
        getIntentAndFindAfterPayState();

        // 获取未完成订单列表
        getFeeState();
    }

    private void getFeeState() {
        String startDate = "2018-01-01";
        String endDate = TimeUtil.getCurrentDate();
        mPresenter.getFeeRecord(OrgConfig.FEE_STATE00, startDate,
                endDate, mPageNumber, mPageSize); // 00 未完成订单
    }

    private void initHeaderData() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String socialNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mHeaderBean = new AfterHeaderBean();
        mHeaderBean.setName(name);
        mHeaderBean.setSocialNum(socialNum);

        mItemList.add(mHeaderBean); // 第一次添加数据
        mItemList.add(mNotice); // 第二次添加数据

        setAdapter();
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mAdapter = new AfterPayAdapter(this, mItemList);
            recyclerView.setAdapter(mAdapter);
            // 设置布局管理器
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
    }

    public void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.setmItemList(mItemList);
        }
    }

    private void getIntentAndFindAfterPayState() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                SerializableHashMap sMap = (SerializableHashMap) bundle.get(IntentExtra.SERIALIZABLE_MAP);
                if (sMap != null) {
                    mPassParamMap = sMap.getMap();
                    // 查询当前医后付签约状态
                    mPresenter.getAfterPayState(mPassParamMap);
                    // 查询当前医保移动支付状态
                    getMobilePayState();
                }
            }
        }
    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
        llNeedPay = findViewById(R.id.llNeedPay);
        activityView = findViewById(R.id.activity_after_pay_home);
    }

    @Override
    public void afterPayResult(AfterPayStateEntity entity) {
        if (entity != null) {
            String signingStatus = entity.getSigning_status();
            String paymentStatus = entity.getOne_payment_status();
            String phone = entity.getPhone();
            String signDate = entity.getCt_date();
            String feeTotal = entity.getFee_total();
            mOrgCode = entity.getOrg_code();
            mOrgName = entity.getOrg_name();

            if (!TextUtils.isEmpty(feeTotal)) {
                tvMoneyNum.setText(feeTotal);
            }

            SpUtil.getInstance().save(SpKey.SIGNING_STATUS, signingStatus);
            SpUtil.getInstance().save(SpKey.PAYMENT_STATUS, paymentStatus);
            SpUtil.getInstance().save(SpKey.PHONE, phone);
            SpUtil.getInstance().save(SpKey.SIGN_DATE, signDate);
            SpUtil.getInstance().save(SpKey.FEE_TOTAL, feeTotal);

            mHeaderBean.setOrgCode(mOrgCode);
            mHeaderBean.setOrgName(mOrgName);
            mHeaderBean.setSigningStatus(signingStatus);
            mHeaderBean.setPaymentStatus(paymentStatus);
            mHeaderBean.setFeeTotal(feeTotal);

            mItemList.set(0, mHeaderBean); // 第二次添加数据(放到下标为0处)
            refreshAdapter();

            // 重置医后付开通标志
            if (mAfterPayOpenSuccess) {
                SpUtil.getInstance().save(SpKey.AFTER_PAY_OPEN_SUCCESS, false);
            }
        }
    }

    @Override
    public void feeBillResult(FeeBillEntity entity) {
        if (entity != null) {
            llNeedPay.setVisibility(View.VISIBLE);
            String feeTotal = entity.getFee_total();
            tvMoneyNum.setText(feeTotal);
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
            mItemList.addAll(1, details); // 添加医院欠费信息数据(放到下标为 1 处)
            mItemList.add(mNotice); // 第二次添加数据
            refreshAdapter();
        } else {
            llNeedPay.setVisibility(View.GONE);
            mItemList.add(mNotice); // 第二次添加数据
            refreshAdapter();
        }
    }

    @Override
    public void onFeeRecordResult(FeeRecordEntity entity) {
        mYd0008Size = -1;
        if (entity != null) {
            List<FeeRecordEntity.DetailsBean> details = entity.getDetails();
            if (details != null && details.size() > 0) {
                mYd0008Size = details.size();
                FeeRecordEntity.DetailsBean detailsBean = details.get(0);
                String feeState = detailsBean.getFee_state();
                String feeTotals = detailsBean.getFee_total();
                String feeCashTotal = detailsBean.getFee_cash_total();
                String feeYbTotal = detailsBean.getFee_yb_total();
                String feeOrgName = detailsBean.getOrg_name();
                String feeOrgCode = detailsBean.getOrg_code();
                payPlatTradeNo = detailsBean.getPayplat_tradno();

                mHeaderBean.setFeeState(feeState);
                mHeaderBean.setFeeTotals(feeTotals);
                mHeaderBean.setFeeCashTotal(feeCashTotal);
                mHeaderBean.setFeeYbTotal(feeYbTotal);
                mHeaderBean.setFeeOrgName(feeOrgName);
                mHeaderBean.setFeeOrgCode(feeOrgCode);
                refreshAdapter();

            } else {
                LogUtil.e(TAG, "没有查询到未完成订单记录！");
            }
        }

        SpUtil.getInstance().save(SpKey.YD0008_SIZE, mYd0008Size);
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

    private SelectHospitalWindow.OnItemClickListener mOnItemClickListener = new SelectHospitalWindow.OnItemClickListener() {
        @Override
        public void onClick(int position) {
            if (mHospitalBeanList != null && position < mHospitalBeanList.size()) {
                HospitalEntity.DetailsBean bean = mHospitalBeanList.get(position);
                if (bean != null) {
                    mOrgCode = bean.getOrg_code();
                    mOrgName = bean.getOrg_name();
                }
            }

            mHeaderBean.setHospitalName(mOrgName);

            // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
            if (mItemList.size() > 0) {
                mItemList.clear();
            }
            mItemList.add(mHeaderBean); // 选择医院后添加数据
            //mItemList.add(mNotice); // 第二次添加数据
            refreshAdapter();

            HashMap<String, String> map = new HashMap<>();
            map.put(MapKey.ORG_CODE, mOrgCode);
            map.put(MapKey.PAGE_NUMBER, mPageNumber);
            map.put(MapKey.PAGE_SIZE, mPageSize);
            mPresenter.getUnclearedBill(map);
        }
    };

    @Override
    public void onHospitalListResult(HospitalEntity body) {
        if (body != null) {
            mHospitalBeanList = body.getDetails();
            if (mHospitalBeanList != null && mHospitalBeanList.size() > 0) {
                if (mSelectHospitalWindow == null) {
                    mSelectHospitalWindow = new SelectHospitalWindow.Builder(this)
                            .setDropView(activityView)
                            .setListener(mOnLoadingListener)
                            .setOnItemClickListener(mOnItemClickListener)
                            .build();
                }

                BrightnessManager.lightoff(this);
                mSelectHospitalWindow.setBeanList(mHospitalBeanList);
                mSelectHospitalWindow.show();
            }
        }
    }

    @Override
    public void onFeeDetailResult(FeeBillEntity entity) {
        if (entity != null) {
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
            // 传递参数过去，发起正式结算，此种情况是个人已经支付完成，医保未支付完成
            PersonalPayActivity.actionStart(AfterPayHomeActivity.this, true,
                    false, mFeeOrgName, mFeeOrgCode, mFeeTotals, mFeeCashTotal,
                    mFeeYbTotal, SettleUtil.getOfficialSettleParam(details));
        }
    }

    /**
     * 查询医保移动支付状态
     */
    private void getMobilePayState() {
        AuthCall.queryOpenStatus(AfterPayHomeActivity.this, MakeArgsFactory.getOpenStatusArgs(), new AuthCall.CallBackListener() {
            @Override
            public void callBack(String result) {
                String mobPayStatus = "00";
                if (!TextUtils.isEmpty(result)) {
                    LogUtil.i(TAG, "result===" + result);
                    OpenStatusBean statusBean = new Gson().fromJson(result, OpenStatusBean.class);
                    int isYbPay = statusBean.getIsYbPay();
                    if (isYbPay == 1) { // 已开通
                        mobPayStatus = "01";
                        mPresenter.uploadMobilePayState(mobPayStatus);
                    } else { // 未开通
                        mobPayStatus = "00";
                    }
                }

                SpUtil.getInstance().save(SpKey.MOB_PAY_STATUS, mobPayStatus);
                mHeaderBean.setMobPayStatus(mobPayStatus);

                mItemList.set(0, mHeaderBean); // 第三次添加数据(放到下标为0处)
                refreshAdapter();
            }
        });
    }

    public void getHospitalList() {
        mPresenter.getHospitalList();
    }

    public void requestYd0009(String feeOrgCode, String feeOrgName, String feeTotals,
                              String feeCashTotal, String feeYbTotal) {
        this.mFeeOrgCode = feeOrgCode;
        this.mFeeOrgName = feeOrgName;
        this.mFeeTotals = feeTotals;
        this.mFeeCashTotal = feeCashTotal;
        this.mFeeYbTotal = feeYbTotal;

        if (!TextUtils.isEmpty(payPlatTradeNo)) {
            mPresenter.getFeeDetail(payPlatTradeNo);
        } else {
            LogUtil.e(TAG, "request yd0009 failed, because payPlatTradeNo is null!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoading != null) {
            mLoading.dispose();
        }
    }
}
