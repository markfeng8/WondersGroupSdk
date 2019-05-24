/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by x-sir on 2019/1/10 :)
 * Function:
 */
public class Cy0005Entity extends BaseEntity {

    private String rqdzje;

    public String getRqdzje() {
        return rqdzje;
    }

    public void setRqdzje(String rqdzje) {
        this.rqdzje = rqdzje;
    }

    private List<DetailsBean> details;

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        @SerializedName("itemnum")
        private String itemNum;
        @SerializedName("customerfeeid")
        private String customerFeeId;
        @SerializedName("itemcode")
        private String itemCode;
        @SerializedName("itemname")
        private String itemName;
        private String standard;
        private String unit;
        @SerializedName("postcount")
        private String postCount;
        private String price;
        private String amount;
        private String total;

        public String getItemNum() {
            return itemNum;
        }

        public void setItemNum(String itemNum) {
            this.itemNum = itemNum;
        }

        public String getCustomerFeeId() {
            return customerFeeId;
        }

        public void setCustomerFeeId(String customerFeeId) {
            this.customerFeeId = customerFeeId;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
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

        public String getPostCount() {
            return postCount;
        }

        public void setPostCount(String postCount) {
            this.postCount = postCount;
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
    }
}
