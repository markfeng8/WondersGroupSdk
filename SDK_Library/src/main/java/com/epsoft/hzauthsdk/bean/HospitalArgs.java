package com.epsoft.hzauthsdk.bean;

/**
 * 标题     : 住院支付参数
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/3/8  17:25
 */

public class HospitalArgs {
    private String name;//	姓名
    private String cardNum;//	卡号
    private String account;//	社会保障号
    private String certNum;//		身份证号码
    private String cbd;//	参保地行政区划
    private String cbdName;//	参保地名称
    //private String mrn;//	病历号
    private String phoneNum;//	手机号码
   // private String idSettlement;//		订单编号
    private String comTime;//	提交时间
    //private String notify_url;//	后台调用地址
    //private String front_url;//		前端跳转地址
    //private Double total;//	总费用
   // private String hospitalID;//		医院ID
   // private String hospitalName ;//		医院名称
  //  private String ad;//		住院号
  //  private String district	;//		院区
  //  private String  docCode;//		医生编号
  //  private String  docName;//	医生姓名
   // private String source;
    private String extendedField;//	扩展字段

    public HospitalArgs() {
    }

    private HospitalArgs(Builder builder) {
        name = builder.name;
        cardNum = builder.cardNum;
        account = builder.account;
        certNum = builder.certNum;
        cbd = builder.cbd;
        cbdName = builder.cbdName;
        phoneNum = builder.phoneNum;
        comTime = builder.comTime;
        extendedField = builder.extendedField;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setCertNum(String certNum) {
        this.certNum = certNum;
    }

    public void setCbd(String cbd) {
        this.cbd = cbd;
    }

    public void setCbdName(String cbdName) {
        this.cbdName = cbdName;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    public void setExtendedField(String extendedField) {
        this.extendedField = extendedField;
    }

    public String getName() {
        return name;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getAccount() {
        return account;
    }

    public String getCertNum() {
        return certNum;
    }

    public String getCbd() {
        return cbd;
    }

    public String getCbdName() {
        return cbdName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getComTime() {
        return comTime;
    }

    public String getExtendedField() {
        return extendedField;
    }

    public static final class Builder {
        private String name;
        private String cardNum;
        private String account;
        private String certNum;
        private String cbd;
        private String cbdName;
        private String phoneNum;
        private String comTime;
        private String extendedField;
        
        public Builder() {
        }

        public Builder setName(String val) {
            name = val;
            return this;
        }

        public Builder setCardNum(String val) {
            cardNum = val;
            return this;
        }

        public Builder setAccount(String val) {
            account = val;
            return this;
        }

        public Builder setCertNum(String val) {
            certNum = val;
            return this;
        }

        public Builder setCbd(String val) {
            cbd = val;
            return this;
        }

        public Builder setCbdName(String val) {
            cbdName = val;
            return this;
        }
        

        public Builder setPhoneNum(String val) {
            phoneNum = val;
            return this;
        }

        public Builder setComTime(String val) {
            comTime = val;
            return this;
        }

        public Builder setExtendedField(String val) {
            extendedField = val;
            return this;
        }

        public HospitalArgs build() {
            return new HospitalArgs(this);
        }
    }
}
