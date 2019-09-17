package com.wondersgroup.android.sdk.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wondersgroup.android.sdk.adapter.ExpandableItemAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by x-sir on 2018/9/13 :)
 * Function:
 */
public class OrderDetailsEntity extends BaseEntity implements Serializable {

    /**
     * fee_order : 10.00
     * diagnosis :
     * illnesscode :
     * details : [{"billingdoctor":"","billingdepartment":"急诊内科","itemname":"急诊挂号诊查费","standard":"","unit":"次","postcount":"","price":"10.00","amount":"1","total":"10.00"}]
     */

    private String fee_order;
    private String diagnosis;
    private String illnesscode;
    private List<DetailsBean> details;

    public String getFee_order() {
        return fee_order;
    }

    public void setFee_order(String fee_order) {
        this.fee_order = fee_order;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getIllnesscode() {
        return illnesscode;
    }

    public void setIllnesscode(String illnesscode) {
        this.illnesscode = illnesscode;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean implements MultiItemEntity, Serializable {
        /**
         * billingdoctor :
         * billingdepartment : 急诊内科
         * itemname : 急诊挂号诊查费
         * standard :
         * unit : 次
         * postcount :
         * price : 10.00
         * amount : 1
         * total : 10.00
         */

        private String billingdoctor;
        private String billingdepartment;
        private String itemname;
        private String standard;
        private String unit;
        private String postcount;
        private String price;
        private String amount;
        private String total;

        public String getBillingdoctor() {
            return billingdoctor;
        }

        public void setBillingdoctor(String billingdoctor) {
            this.billingdoctor = billingdoctor;
        }

        public String getBillingdepartment() {
            return billingdepartment;
        }

        public void setBillingdepartment(String billingdepartment) {
            this.billingdepartment = billingdepartment;
        }

        public String getItemname() {
            return itemname;
        }

        public void setItemname(String itemname) {
            this.itemname = itemname;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPostcount() {
            return postcount;
        }

        public void setPostcount(String postcount) {
            this.postcount = postcount;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        @Override
        public int getItemType() {
            return ExpandableItemAdapter.TYPE_LEVEL_1;
        }
    }
}
