package com.wondersgroup.android.sdk.entity;

public class WondersExternParams {

    private String channelNo;
    private String QDCODE;
    private String sign;

    public WondersExternParams() {

    }

    /**
     * 返回sign
     *
     * @param sign
     */
    public WondersExternParams(String sign) {
        this.sign = sign;
    }

    /**
     * @param channelNo 渠道编号
     * @param QDCODE    渠道信息
     */
    public WondersExternParams(String channelNo, String QDCODE) {
        this.channelNo = channelNo;
        this.QDCODE = QDCODE;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getQDCODE() {
        return QDCODE;
    }

    public void setQDCODE(String QDCODE) {
        this.QDCODE = QDCODE;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "WondersExternParams{" +
                "channelNo='" + channelNo + '\'' +
                ", QDCODE='" + QDCODE + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
