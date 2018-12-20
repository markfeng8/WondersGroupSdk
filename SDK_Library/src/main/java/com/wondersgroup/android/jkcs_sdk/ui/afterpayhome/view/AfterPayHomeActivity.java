package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.adapter.AfterPayHomeAdapter;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.Exceptions;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterHeaderBean;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter.AfterPayHomePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view.PaymentDetailsActivity;
import com.wondersgroup.android.jkcs_sdk.utils.BrightnessManager;
import com.wondersgroup.android.jkcs_sdk.utils.EpSoftUtils;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.utils.WToastUtil;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;
import com.wondersgroup.android.jkcs_sdk.widget.SelectHospitalWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页
 */
public class AfterPayHomeActivity extends MvpBaseActivity<AfterPayHomeContract.IView,
        AfterPayHomePresenter<AfterPayHomeContract.IView>> implements AfterPayHomeContract.IView {

    private static final String TAG = AfterPayHomeActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private View activityView;
    private LinearLayout llNeedPay;
    private LoadingView mLoading;
    private SelectHospitalWindow mSelectHospitalWindow;
    private AfterPayHomeAdapter mAdapter;
    private HashMap<String, String> mPassParamMap;
    private String mOrgName;
    private String mOrgCode;
    private boolean mAfterPayOpenSuccess;
    private List<HospitalEntity.DetailsBean> mHospitalBeanList;
    private SelectHospitalWindow.OnLoadingListener mOnLoadingListener =
            () -> BrightnessManager.lighton(AfterPayHomeActivity.this);
    /**
     * 头部数据类型
     */
    private AfterHeaderBean mHeaderBean = new AfterHeaderBean();
    /**
     * 中间的门诊账单的数据类型
     */
    private List<FeeBillEntity.DetailsBean> mFeeBillList = new ArrayList<>();
    /**
     * 尾部温馨提示的数据类型
     */
    private String mNotice = "温馨提示";
    /**
     * 装所有数据的 List 集合
     */
    private List<Object> mItemList = new ArrayList<>();

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

    /**
     * 返回到主页面刷新所有数据
     */
    private void backRefreshPager() {
        // 回来就隐藏付款的布局
        llNeedPay.setVisibility(View.GONE);
        // 刷新医后付&医保移动支付状态
        refreshAfterPayState();
        getMobilePayState();
        // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
        mHeaderBean.setHospitalName("湖州市");
        mItemList.removeAll(mFeeBillList);
        refreshAdapter();
    }

    /**
     * 刷新当前医后付签约状态
     */
    private void refreshAfterPayState() {
        mPresenter.getAfterPayState(mPassParamMap);
    }

    private void initListener() {
        tvPayMoney.setOnClickListener(v -> PaymentDetailsActivity.actionStart(
                AfterPayHomeActivity.this, mOrgCode, mOrgName, false));
    }

    private void initData() {
        mLoading = new LoadingView.Builder(this)
                .build();
        initHeaderData();
        getIntentAndFindAfterPayState();
    }

    private void initHeaderData() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String socialNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mHeaderBean.setName(name);
        mHeaderBean.setSocialNum(socialNum);
        // 第一次添加头部数据
        mItemList.add(mHeaderBean);
        // 第二次添加门诊账单数据
        mItemList.addAll(mFeeBillList);
        // 第三次添加尾部数据
        mItemList.add(mNotice);
        setAdapter();
    }

    private void setAdapter() {
        if (mItemList != null && mItemList.size() > 0) {
            mAdapter = new AfterPayHomeAdapter(this, mItemList);
            recyclerView.setAdapter(mAdapter);
            // 设置布局管理器
            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
    }

    public void refreshAdapter() {
        if (mAdapter != null) {
            mAdapter.refreshAdapter();
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
        recyclerView = findViewById(R.id.recyclerView);
        tvMoneyNum = findViewById(R.id.tvMoneyNum);
        tvPayMoney = findViewById(R.id.tvPayMoney);
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
            refreshAdapter();

            // 重置医后付开通标志
            if (mAfterPayOpenSuccess) {
                SpUtil.getInstance().save(SpKey.AFTER_PAY_OPEN_SUCCESS, false);
            }
        }
    }

    @Override
    public void onYd0003Result(FeeBillEntity entity) {
        // 先移除旧的门诊账单数据
        mItemList.removeAll(mFeeBillList);
        if (entity != null) {
            llNeedPay.setVisibility(View.VISIBLE);
            String feeTotal = entity.getFee_total();
            // 00 未结算 01 正在结算
            String payState = entity.getPayState();
            if ("01".equals(payState)) {
                tvPayMoney.setText("支付中");
                tvPayMoney.setEnabled(false);
            }
            tvMoneyNum.setText(feeTotal);
            mFeeBillList = entity.getDetails();
            mItemList.addAll(1, mFeeBillList);
        } else {
            llNeedPay.setVisibility(View.GONE);
        }
        refreshAdapter();
    }

    /**
     * 请求 yd0003 接口
     */
    public void requestYd0003() {
        mPresenter.requestYd0003(mOrgCode);
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

    private SelectHospitalWindow.OnItemClickListener mOnItemClickListener = new SelectHospitalWindow.OnItemClickListener() {
        @Override
        public void onClick(int position) {
            if (mHospitalBeanList != null && position < mHospitalBeanList.size()) {
                HospitalEntity.DetailsBean bean = mHospitalBeanList.get(position);
                if (bean != null) {
                    mOrgCode = bean.getOrg_code();
                    mOrgName = bean.getOrg_name();
                    mHeaderBean.setHospitalName(mOrgName);
                }
            }

            requestYd0003();
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
            } else {
                WToastUtil.show("未查询到门诊账单！");
            }
        } else {
            LogUtil.w(TAG, "onHospitalListResult() -> body is null!");
        }
    }

    /**
     * 查询医保移动支付开通状态
     */
    private void getMobilePayState() {
        EpSoftUtils.queryYiBaoOpenStatus(this, status -> {
            if ("01".equals(status)) {
                mPresenter.uploadMobilePayState();
            }
            mHeaderBean.setMobPayStatus(status);
            refreshAdapter();
        });
    }

    public void getHospitalList() {
        mPresenter.getHospitalList();
    }

    public static void actionStart(Context context, HashMap<String, String> param) {
        if (context != null) {
            if (param != null && !param.isEmpty()) {
                // 传递数据
                SerializableHashMap sMap = new SerializableHashMap();
                sMap.setMap(param); // 将map数据添加到封装的sMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable(IntentExtra.SERIALIZABLE_MAP, sMap);
                Intent intent = new Intent(context, AfterPayHomeActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            } else {
                throw new IllegalArgumentException(Exceptions.MAP_SET_NULL);
            }

        } else {
            throw new IllegalArgumentException(Exceptions.PARAM_CONTEXT_NULL);
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
