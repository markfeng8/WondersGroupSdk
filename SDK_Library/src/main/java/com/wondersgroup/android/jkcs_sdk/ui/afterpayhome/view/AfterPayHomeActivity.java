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
import com.wondersgroup.android.jkcs_sdk.ui.payrecord.view.FeeRecordActivity;
import com.wondersgroup.android.jkcs_sdk.ui.personalpay.view.PersonalPayActivity;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.MakeArgsFactory;
import com.wondersgroup.android.jkcs_sdk.utils.SettleUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.widget.DividerItemDecoration;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.SelectHospitalWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 医后付首页
 */
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
    private String mPayPlatTradeNo;
    private String mFeeState;
    private String mFeeOrgCode;
    private String mFeeOrgName;
    private String mFeeTotals;
    private String mFeeCashTotal;
    private String mFeeYbTotal;
    private boolean mIsOnlyRefreshPage;
    private boolean mIsYd0003Click;

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
        backRefreshPager();
    }

    private void backRefreshPager() {
        // 回来就隐藏付款的布局
        llNeedPay.setVisibility(View.GONE);
        /*
         * 回到主页面刷新状态
         */
        refreshAfterPayState();
        requestYd0008(true, false);
        getMobilePayState();

        // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
        if (mItemList.size() > 0) {
            mItemList.clear();
        }
        mHeaderBean.setHospitalName("湖州市");
        mItemList.add(mHeaderBean); // 选择医院后添加数据
        mItemList.add(mNotice);
        refreshAdapter();
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

        /*
         * 查询全部 00 未完成订单(YD0008)
         */
        requestYd0008(true, false);
    }

    /**
     * 查询全部 00 未完成订单(YD0008)
     *
     * @param isOnlyRefreshPage 是否只是仅仅刷新页面？
     *                          true : 只是仅仅刷新页面
     *                          false : 点击医后付首页缴费 & 中间欠费按钮时（yd0008 -> yd0009 -> 发起正式结算）
     * @param isYd0003Click     是否是点击医后付首页顶部 yd0003 的欠费
     */
    public void requestYd0008(boolean isOnlyRefreshPage, boolean isYd0003Click) {
        this.mIsOnlyRefreshPage = isOnlyRefreshPage;
        this.mIsYd0003Click = isYd0003Click;
        mPresenter.requestYd0008();
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
            mAdapter.setItemList(mItemList);
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
            // 添加医院欠费信息数据(放到下标为 1 处)
            mItemList.addAll(1, details);
            // 第二次添加数据
            mItemList.add(mNotice);
            refreshAdapter();
        } else {
            llNeedPay.setVisibility(View.GONE);
            mItemList.add(mNotice); // 第二次添加数据
            refreshAdapter();
        }
    }

    @Override
    public void onYd0008Result(FeeRecordEntity entity) {
        mYd0008Size = -1;
        if (entity != null) {
            List<FeeRecordEntity.DetailsBean> details = entity.getDetails();
            if (details != null && details.size() > 0) {
                mYd0008Size = details.size();
                FeeRecordEntity.DetailsBean detailsBean = details.get(0);
                mFeeState = detailsBean.getFee_state();
                mFeeTotals = detailsBean.getFee_total();
                mFeeCashTotal = detailsBean.getFee_cash_total();
                mFeeYbTotal = detailsBean.getFee_yb_total();
                mFeeOrgName = detailsBean.getOrg_name();
                mFeeOrgCode = detailsBean.getOrg_code();
                mPayPlatTradeNo = detailsBean.getPayplat_tradno();

                if (mIsOnlyRefreshPage) {
                    mHeaderBean.setFeeState(mFeeState);
                    mHeaderBean.setFeeTotals(mFeeTotals);
                    mHeaderBean.setFeeCashTotal(mFeeCashTotal);
                    mHeaderBean.setFeeYbTotal(mFeeYbTotal);
                    mHeaderBean.setFeeOrgName(mFeeOrgName);
                    mHeaderBean.setFeeOrgCode(mFeeOrgCode);
                    mHeaderBean.setYd0008Size(mYd0008Size);
                } else {
                    parseFeeState();
                }

            } else {
                LogUtil.e(TAG, "没有查询到未完成订单(YD0008)记录！");
                if (mIsOnlyRefreshPage) {
                    mHeaderBean.setFeeState(null);
                    mHeaderBean.setYd0008Size(-1);
                } else {
                    // 全部未结算，跳转到 "缴费详情" 页面
                    PaymentDetailsActivity.actionStart(AfterPayHomeActivity.this, mOrgCode, mOrgCode, false);
                }
            }

            if (mIsOnlyRefreshPage) {
                // 添加数据并刷新适配器
                mItemList.set(0, mHeaderBean);
                refreshAdapter();
            }

        } else {
            LogUtil.w(TAG, "onYd0008Result() -> entity is null!");
        }
    }

    private void parseFeeState() {
        if (!TextUtils.isEmpty(mFeeState)) {
            switch (mFeeState) {
                case "00": // 全部未结算
                    if (mIsYd0003Click) {
                        // 全部未结算，跳转到 "缴费详情" 页面
                        PaymentDetailsActivity.actionStart(AfterPayHomeActivity.this, mOrgCode, mOrgCode, false);
                    } else {
                        // 全部未结算，跳转到 "缴费详情" 页面
                        PaymentDetailsActivity.actionStart(AfterPayHomeActivity.this, mFeeOrgCode, mFeeOrgName, false);
                    }
                    break;
                case "01": // 个人已结算，医保未结算
                    // 医保未结算，跳转到医保结算页面
                    requestYd0009();
                    break;
                case "02": // 全部已结算，跳转到已完成订单页面
                    FeeRecordActivity.actionStart(AfterPayHomeActivity.this);
                    break;
                default:
                    break;
            }
        } else {
            // 全部未结算，跳转到 "缴费详情" 页面
            PaymentDetailsActivity.actionStart(AfterPayHomeActivity.this, mOrgCode, mOrgCode, false);
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

    /**
     * yd0009 的响应（当点击医后付首页的缴费和继续支付时，如果是个人已支付，医保未支付的情况下，
     * 携带 yd0009 返回来的 details 跳转到医保支付页面，携带医保的 token 去发起正式结算）
     *
     * @param entity
     */
    @Override
    public void onYd0009Result(FeeBillEntity entity) {
        if (entity != null) {
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();

            LogUtil.i(TAG, "mFeeOrgName===" + mFeeOrgName + ",mFeeOrgCode===" + mFeeOrgCode +
                    ",mFeeTotals===" + mFeeTotals + ",mFeeCashTotal===" + mFeeCashTotal +
                    ",mFeeYbTotal===" + mFeeYbTotal);

            /*
             * 传递参数过去，发起正式结算，此种情况是个人已经支付完成，医保未支付完成
             */
            PersonalPayActivity.actionStart(AfterPayHomeActivity.this, false,
                    false, mFeeOrgName, mFeeOrgCode, mFeeTotals, mFeeCashTotal,
                    mFeeYbTotal, SettleUtil.getOfficialSettleParam(details));
        } else {
            LogUtil.w(TAG, "onYd0009Result() -> entity is null!");
        }
    }

    /**
     * 查询医保移动支付状态
     */
    private void getMobilePayState() {
        AuthCall.queryOpenStatus(AfterPayHomeActivity.this, MakeArgsFactory.getOpenStatusArgs(), result -> {
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
        });
    }

    public void getHospitalList() {
        mPresenter.getHospitalList();
    }

    public void requestYd0009() {
        if (!TextUtils.isEmpty(mPayPlatTradeNo)) {
            mPresenter.requestYd0009(mPayPlatTradeNo);
        } else {
            LogUtil.e(TAG, "request yd0009 failed, because mPayPlatTradeNo is null!");
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
