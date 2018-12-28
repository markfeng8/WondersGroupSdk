/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;

import java.util.List;

/**
 * Created by x-sir on 2018/12/28 :)
 * Function:
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private static final String TAG = "ExpandableItemAdapter";
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.wonders_group_item_level0);
        addItemType(TYPE_LEVEL_1, R.layout.wonders_group_item_level1);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                if (item instanceof FeeBillEntity.DetailsBean) {
                    final FeeBillEntity.DetailsBean level0 = (FeeBillEntity.DetailsBean) item;

                    helper.setText(R.id.tvOrderName, level0.getOrder_name())
                            .setText(R.id.tvMoney, level0.getFee_order())
                            .setText(R.id.tvOrderTime, "账单时间：" + level0.getHis_order_time())
                            .setImageResource(R.id.ivArrow, level0.isExpanded() ? R.drawable.wonders_group_down_arrow : R.drawable.wonders_group_up_arrow);

                    helper.itemView.setOnClickListener(v -> {
                        int pos = helper.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (level0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    });
                }
                break;
            case TYPE_LEVEL_1:
                if (item instanceof OrderDetailsEntity.DetailsBean) {
                    final OrderDetailsEntity.DetailsBean level1 = (OrderDetailsEntity.DetailsBean) item;
                }
                break;
        }
    }
}
