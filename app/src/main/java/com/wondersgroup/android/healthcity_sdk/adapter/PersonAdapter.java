/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.healthcity_sdk.adapter;

import android.support.annotation.Nullable;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wondersgroup.android.healthcity_sdk.R;
import com.wondersgroup.android.healthcity_sdk.bean.PersonBean;

import java.util.List;

/**
 * Created by x-sir on 2019/1/9 :)
 * Function:
 */
public class PersonAdapter extends BaseQuickAdapter<PersonBean, BaseViewHolder> {

    public PersonAdapter(@Nullable List<PersonBean> data) {
        super(R.layout.item_person, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonBean item) {
        helper.setText(R.id.tvName, Html.fromHtml("<u>" + item.getName() + "</u>"));
    }
}
