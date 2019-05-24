/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.entity;

import java.util.List;

/**
 * Created by x-sir on 2018/9/13 :)
 * Function:缴费详情 List 数据的组合类(默认数据和展开数据)
 */
public class CombineDetailsBean {

    private FeeBillDetailsBean defaultDetails;
    private List<OrderDetailsEntity.DetailsBean> openDetails;
    /**
     * 是否展开，默认为否
     */
    private boolean spread = false;

    public CombineDetailsBean() {

    }

    public CombineDetailsBean(FeeBillDetailsBean defaultDetails, List<OrderDetailsEntity.DetailsBean> openDetails, boolean spread) {
        this.defaultDetails = defaultDetails;
        this.openDetails = openDetails;
        this.spread = spread;
    }

    public FeeBillDetailsBean getDefaultDetails() {
        return defaultDetails;
    }

    public void setDefaultDetails(FeeBillDetailsBean defaultDetails) {
        this.defaultDetails = defaultDetails;
    }

    public List<OrderDetailsEntity.DetailsBean> getOpenDetails() {
        return openDetails;
    }

    public void setOpenDetails(List<OrderDetailsEntity.DetailsBean> openDetails) {
        this.openDetails = openDetails;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }
}
