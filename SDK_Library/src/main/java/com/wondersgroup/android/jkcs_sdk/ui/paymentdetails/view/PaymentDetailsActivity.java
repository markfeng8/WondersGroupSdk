package com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.view;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.MapKey;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.LockOrderEntity;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.contract.DetailsContract;
import com.wondersgroup.android.jkcs_sdk.ui.paymentdetails.presenter.DetailsPresenter;
import com.wondersgroup.android.jkcs_sdk.utils.NumberUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// 缴费详情页面
public class PaymentDetailsActivity extends MvpBaseActivity<DetailsContract.IView,
        DetailsPresenter<DetailsContract.IView>> implements DetailsContract.IView {

    private RecyclerView recyclerView; // 使用分类型的 RecyclerView 来实现
    private TextView tvMoneyNum;
    private TextView tvPayMoney;
    private String mOrgCode;

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
        // 目前使用中心医院朱凯 mOrgCode 暂时写死
        // 中心医院：47117170333050211A1001
        mOrgCode = "47117170333050211A1001";
        HashMap<String, String> map = new HashMap<>();
        map.put(MapKey.ORG_CODE, mOrgCode);
        map.put(MapKey.PAGE_NUMBER, "1");
        map.put(MapKey.PAGE_SIZE, "10");
        mPresenter.getUnclearedBill(map);
    }

    private void initListener() {

    }

    private void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvMoneyNum = (TextView) findViewById(R.id.tvMoneyNum);
        tvPayMoney = (TextView) findViewById(R.id.tvPayMoney);
    }

    @Override
    public void feeBillResult(FeeBillEntity entity) {
        if (entity != null) {
            String feeTotal = entity.getFee_total();
            List<HashMap<String, String>> detailsList = new ArrayList<>();
            List<FeeBillEntity.DetailsBean> details = entity.getDetails();
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
        }
    }

    @Override
    public void lockOrderResult(LockOrderEntity entity) {
        if (entity != null) {
            String lockStartTime = entity.getLock_start_time();
            String payPlatTradno = entity.getPayplat_tradno();
        }
    }
}
