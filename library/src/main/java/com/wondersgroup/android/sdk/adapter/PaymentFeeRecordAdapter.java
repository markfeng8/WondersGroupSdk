/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.entity.FeeRecordEntity;

import java.util.List;

/**
 * Created by x-sir on 2018/12/19 :)
 * Function:门诊缴费记录页面的 Adapter
 */
public class PaymentFeeRecordAdapter extends BaseQuickAdapter<FeeRecordEntity.DetailsBean, BaseViewHolder> {

    public PaymentFeeRecordAdapter(int layoutResId, @Nullable List<FeeRecordEntity.DetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FeeRecordEntity.DetailsBean detailsBean) {
        if (detailsBean != null) {
            helper.setText(R.id.tvHospitalName, detailsBean.getOrg_name());
            helper.setText(R.id.tvFeeDate, "订单日期：" + detailsBean.getShop_order_time().substring(0, 10));
            helper.setText(R.id.tvTradeNo, "订单号：" + detailsBean.getPayplat_tradno());
            helper.setVisible(R.id.tvFeeTotal, true);
            helper.setText(R.id.tvFeeTotal, "总金额：" + detailsBean.getFee_total() + "元");
        }
    }
}
