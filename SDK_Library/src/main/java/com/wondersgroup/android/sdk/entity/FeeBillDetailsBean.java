/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wondersgroup.android.sdk.adapter.ExpandableItemAdapter;

/**
 * Created by x-sir on 2018/12/28 :)
 * Function:
 */
public class FeeBillDetailsBean extends AbstractExpandableItem<OrderDetailsEntity.DetailsBean> implements MultiItemEntity {

    /**
     * his_order_no : CF20839351
     * his_order_time : 2018-08-22 11:37:25
     * ordername : 处方
     * fee_order : 123.15
     */

    private String his_order_no;
    private String org_code;
    private String his_order_time;
    private String ordername;
    private String order_name;
    private String fee_order;

    public String getOrg_code() {
        return org_code;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getHis_order_no() {
        return his_order_no;
    }

    public void setHis_order_no(String his_order_no) {
        this.his_order_no = his_order_no;
    }

    public String getHis_order_time() {
        return his_order_time;
    }

    public void setHis_order_time(String his_order_time) {
        this.his_order_time = his_order_time;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getFee_order() {
        return fee_order;
    }

    public void setFee_order(String fee_order) {
        this.fee_order = fee_order;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }

}
