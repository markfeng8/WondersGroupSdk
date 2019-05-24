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
import com.wondersgroup.android.sdk.entity.Cy0005Entity;

import java.util.List;

/**
 * Created by x-sir on 2018/11/1 :)
 * Function:日清单列表数据的 Adapter
 */
public class DayDetailedListAdapter extends BaseQuickAdapter<Cy0005Entity.DetailsBean, BaseViewHolder> {

    private static final String TAG = "DayDetailedListAdapter";

    public DayDetailedListAdapter(@Nullable List<Cy0005Entity.DetailsBean> data) {
        super(R.layout.wonders_group_day_detailed_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Cy0005Entity.DetailsBean item) {
        helper.setText(R.id.tvDrugName, item.getItemName())
                .setText(R.id.tvDrugNo, item.getItemCode())
                .setText(R.id.tvStandard, "规格：" + item.getStandard())
                .setText(R.id.tvNum, "数量：" + item.getAmount())
                .setText(R.id.tvUnit, "单位：" + item.getUnit())
                .setText(R.id.tvPrice, "单价：" + item.getPrice())
                .setText(R.id.tvFee, "费用：" + item.getTotal())
                .setText(R.id.tvType, "类别：" + item.getCustomerFeeId());
    }
}
