/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.Cy0001Entity;

import java.util.List;

/**
 * Created by x-sir on 2018/12/18 :)
 * Function:历史住院记录适配器
 */
public class HosHistoryAdapter extends BaseQuickAdapter<Cy0001Entity.DetailsBean, BaseViewHolder> {

    public HosHistoryAdapter(int layoutResId, @Nullable List<Cy0001Entity.DetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Cy0001Entity.DetailsBean item) {
        helper.setText(R.id.tvHospitalName, item.getOrgName());
        helper.setText(R.id.tvFeeDate, "入院时间：" + item.getRysj());
        helper.setText(R.id.tvTradeNo, "出院时间：" + item.getCysj());
        helper.setText(R.id.tvFeeTotal, "总金额：" + item.getFee_total());
    }
}
