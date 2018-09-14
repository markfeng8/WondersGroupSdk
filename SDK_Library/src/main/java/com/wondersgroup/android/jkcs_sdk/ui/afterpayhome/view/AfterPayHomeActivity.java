package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epsoft.hzauthsdk.all.AuthCall;
import com.epsoft.hzauthsdk.bean.OpenStatusBean;
import com.epsoft.hzauthsdk.utils.MakeArgsFactory;
import com.google.gson.Gson;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.entity.AfterHeaderBean;
import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.SerializableHashMap;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.AfterPayAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract.AfterPayHomeContract;
import com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.presenter.AfterPayHomePresenter;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;
import com.wondersgroup.android.jkcs_sdk.widget.DividerItemDecoration;
import com.wondersgroup.android.jkcs_sdk.widget.LoadingView;

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

    private LoadingView mLoading;
    private AfterHeaderBean mHeaderBean;
    private List<Object> mItemList = new ArrayList<>();
    private AfterPayAdapter mAdapter;
    private HashMap<String, String> mPassParamMap;
    private boolean mAfterPayOpenSuccess;

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

        // TODO: 2018/9/10 处理Loading
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
                Toast.makeText(AfterPayHomeActivity.this, "暂未开通", Toast.LENGTH_SHORT).show();
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
    }

    private void initHeaderData() {
        String name = SpUtil.getInstance().getString(SpKey.NAME, "");
        String socialNum = SpUtil.getInstance().getString(SpKey.CARD_NUM, "");
        mHeaderBean = new AfterHeaderBean();
        mHeaderBean.setName(name);
        mHeaderBean.setSocialNum(socialNum);

        mItemList.add(mHeaderBean); // 第一次添加数据

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
                    // 查询当前移动支付状态
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
            String noticePhone = entity.getPhone();
            String signDate = entity.getCt_date();
            String feeTotal = entity.getFee_total();
            if (!TextUtils.isEmpty(feeTotal)) {
                tvMoneyNum.setText(feeTotal);
            }

            SpUtil.getInstance().save(SpKey.SIGNING_STATUS, signingStatus);
            SpUtil.getInstance().save(SpKey.PAYMENT_STATUS, paymentStatus);
            SpUtil.getInstance().save(SpKey.NOTICE_PHONE, noticePhone);
            SpUtil.getInstance().save(SpKey.SIGN_DATE, signDate);
            SpUtil.getInstance().save(SpKey.FEE_TOTAL, feeTotal);

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
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
            mItemList.addAll(details); // 添加医院欠费信息数据(放到下标为0处)
            refreshAdapter();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentExtra.REQUEST_CODE && resultCode == IntentExtra.RESULT_CODE) {
            String orgName = data.getStringExtra(IntentExtra.ORG_NAME);
            String orgCode = data.getStringExtra(IntentExtra.ORG_CODE);
            mHeaderBean.setHospitalName(orgName);

            // 判断集合中是否有旧数据，先移除旧的，然后再添加新的
            if (mItemList.size() > 0) {
                mItemList.clear();
            }
            mItemList.add(mHeaderBean); // 选择医院后添加数据
            refreshAdapter();

            HashMap<String, String> map = new HashMap<>();
            map.put(MapKey.ORG_CODE, orgCode);
            map.put(MapKey.PAGE_NUMBER, "1");
            map.put(MapKey.PAGE_SIZE, "10");
            mPresenter.getUnclearedBill(map);
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
}
