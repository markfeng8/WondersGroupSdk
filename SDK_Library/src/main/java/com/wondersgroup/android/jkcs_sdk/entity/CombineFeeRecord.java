package com.wondersgroup.android.jkcs_sdk.entity;

import java.util.List;

/**
 * Created by x-sir on 2018/9/19 :)
 * Function:缴费记录页面 List Item 组合数据 Bean
 */
public class CombineFeeRecord {

    private FeeRecordEntity.DetailsBean recordDetail;
    private List<FeeBillEntity.DetailsBean> feeDetail;

    public CombineFeeRecord() {
    }

    public CombineFeeRecord(FeeRecordEntity.DetailsBean recordDetail, List<FeeBillEntity.DetailsBean> feeDetail) {
        this.recordDetail = recordDetail;
        this.feeDetail = feeDetail;
    }

    public FeeRecordEntity.DetailsBean getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(FeeRecordEntity.DetailsBean recordDetail) {
        this.recordDetail = recordDetail;
    }

    public List<FeeBillEntity.DetailsBean> getFeeDetail() {
        return feeDetail;
    }

    public void setFeeDetail(List<FeeBillEntity.DetailsBean> feeDetail) {
        this.feeDetail = feeDetail;
    }
}
