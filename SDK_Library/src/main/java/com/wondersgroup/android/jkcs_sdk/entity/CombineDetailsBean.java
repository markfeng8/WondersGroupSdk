package com.wondersgroup.android.jkcs_sdk.entity;

import java.util.List;

/**
 * Created by x-sir on 2018/9/13 :)
 * Function:缴费详情 List 数据的组合类(默认数据和展开数据)
 */
public class CombineDetailsBean {

    private FeeBillDetailsBean defaultDetails;
    private List<OrderDetailsEntity.DetailsBean> openDetails;

    public CombineDetailsBean() {
    }

    public CombineDetailsBean(FeeBillDetailsBean defaultDetails,
                              List<OrderDetailsEntity.DetailsBean> openDetails) {
        this.defaultDetails = defaultDetails;
        this.openDetails = openDetails;
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

    @Override
    public String toString() {
        return "CombineDetailsBean{" +
                "defaultDetails=" + defaultDetails +
                ", openDetails=" + openDetails +
                '}';
    }
}
