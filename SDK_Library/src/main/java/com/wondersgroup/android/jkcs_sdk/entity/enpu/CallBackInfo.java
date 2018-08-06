package com.wondersgroup.android.jkcs_sdk.entity.enpu;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/3/30  14:42
 */

public class CallBackInfo {
    private String code;
    private String msg;
    private DataInfo data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataInfo getData() {
        return data;
    }

    public void setData(DataInfo data) {
        this.data = data;
    }

    public static class DataInfo{
        private String tradeNum;
        private String type;
        private String payInfo;

        public String getTradeNum() {
            return tradeNum;
        }

        public void setTradeNum(String tradeNum) {
            this.tradeNum = tradeNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPayInfo() {
            return payInfo;
        }

        public void setPayInfo(String payInfo) {
            this.payInfo = payInfo;
        }
    }
}
