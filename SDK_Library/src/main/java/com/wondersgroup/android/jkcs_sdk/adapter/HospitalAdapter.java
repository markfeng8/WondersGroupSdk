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
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;

import java.util.List;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:医院列表数据的适配器
 */
public class HospitalAdapter extends BaseQuickAdapter<HospitalEntity.DetailsBean, BaseViewHolder> {

    public HospitalAdapter(@Nullable List<HospitalEntity.DetailsBean> data) {
        super(R.layout.wonders_group_item_hospital, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HospitalEntity.DetailsBean item) {
        helper.setText(R.id.tvHospitalName, item.getOrg_name());
    }
}
