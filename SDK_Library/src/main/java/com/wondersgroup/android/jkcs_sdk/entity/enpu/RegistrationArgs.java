package com.wondersgroup.android.jkcs_sdk.entity.enpu;

import java.util.List;

/**
 * 标题     :挂号支付参数类
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/3/8  17:03
 */

public class RegistrationArgs{
    private String name	;//姓名
    private String cardNum;//	卡号
    private String account;//	社会保障号
    private String certNum	;//	身份证号码
    private String cbd	;//	参保地行政区划
    private String cbdName	;//参保地名称
    private String phoneNum	;//	手机号码
    private String lyInfo	;//	来源
    private String mrn;//	病历号
    private String idSettlement;//	订单编号
    private String comTime	;//	提交时间
    private String notify_url;//	后台调用地址
    private Double total;//	总费用
    private String hospitalID;//	医院编号
    private String hospitalName;//医院名称
    private String district;//院区
    private String source;
    private String extendedField;//扩展字段
    private List<info> medicalInformationList;//详细列表列表
    
    public RegistrationArgs() {
    }

    private RegistrationArgs(Builder builder) {
        name = builder.name;
        cardNum = builder.cardNum;
        account = builder.account;
        certNum = builder.certNum;
        cbd = builder.cbd;
        cbdName = builder.cbdName;
        phoneNum = builder.phoneNum;
        lyInfo = builder.lyInfo;
        mrn = builder.mrn;
        idSettlement = builder.idSettlement;
        comTime = builder.comTime;
        notify_url = builder.notify_url;
        total = builder.total;
        hospitalID = builder.hospitalID;
        hospitalName = builder.hospitalName;
        district = builder.district;
        extendedField = builder.extendedField;
        medicalInformationList = builder.medicalInformationList;
        source=builder.source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public void setLyInfo(String lyInfo) {
        this.lyInfo = lyInfo;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public void setIdSettlement(String idSettlement) {
        this.idSettlement = idSettlement;
    }

    public void setComTime(String comTime) {
        this.comTime = comTime;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setExtendedField(String extendedField) {
        this.extendedField = extendedField;
    }

    public void setMedicalInformationList(List<info> medicalInformationList) {
        this.medicalInformationList = medicalInformationList;
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

    public String getLyInfo() {
        return lyInfo;
    }

    public String getMrn() {
        return mrn;
    }

    public String getIdSettlement() {
        return idSettlement;
    }

    public String getComTime() {
        return comTime;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public Double getTotal() {
        return total;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getDistrict() {
        return district;
    }

    public String getExtendedField() {
        return extendedField;
    }

    public List<info> getMedicalInformationList() {
        return medicalInformationList;
    }

    public static class info{
        private String preid;//	预约编号
        private String pbCode;//排班编号
        private String date;//	挂号日期
        private String seqNum;//挂号序号
        private String timePart;//上下午时间段
        private String docCode;//	医生编号
        private String docName;//医生姓名
        private String visitTime;//	就诊时间
        private String cusName;//通用中文名称
        private String cftype;//类型
        private String ybLevel;//	规格类
        private Double fee;//金额
        private Double num;//数量
        private Double zfbl;//自付比例
        private Double zffee;//自付金额
        private String extendedField;//扩展字段

        public String getPreid() {
            return preid;
        }

        public void setPreid(String preid) {
            this.preid = preid;
        }

        public String getPbCode() {
            return pbCode;
        }

        public void setPbCode(String pbCode) {
            this.pbCode = pbCode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSeqNum() {
            return seqNum;
        }

        public void setSeqNum(String seqNum) {
            this.seqNum = seqNum;
        }

        public String getTimePart() {
            return timePart;
        }

        public void setTimePart(String timePart) {
            this.timePart = timePart;
        }

        public String getDocCode() {
            return docCode;
        }

        public void setDocCode(String docCode) {
            this.docCode = docCode;
        }

        public String getDocName() {
            return docName;
        }

        public void setDocName(String docName) {
            this.docName = docName;
        }

        public String getVisitTime() {
            return visitTime;
        }

        public void setVisitTime(String visitTime) {
            this.visitTime = visitTime;
        }

        public String getCusName() {
            return cusName;
        }

        public void setCusName(String cusName) {
            this.cusName = cusName;
        }

        public String getCftype() {
            return cftype;
        }

        public void setCftype(String cftype) {
            this.cftype = cftype;
        }

        public String getYbLevel() {
            return ybLevel;
        }

        public void setYbLevel(String ybLevel) {
            this.ybLevel = ybLevel;
        }

        public Double getFee() {
            return fee;
        }

        public void setFee(Double fee) {
            this.fee = fee;
        }

        public Double getNum() {
            return num;
        }

        public void setNum(Double num) {
            this.num = num;
        }

        public Double getZfbl() {
            return zfbl;
        }

        public void setZfbl(Double zfbl) {
            this.zfbl = zfbl;
        }

        public Double getZffee() {
            return zffee;
        }

        public void setZffee(Double zffee) {
            this.zffee = zffee;
        }

        public String getExtendedField() {
            return extendedField;
        }

        public void setExtendedField(String extendedField) {
            this.extendedField = extendedField;
        }
    }

    public static final class Builder {
        private String name;
        private String cardNum;
        private String account;
        private String certNum;
        private String cbd;
        private String cbdName;
        private String phoneNum;
        private String lyInfo;
        private String mrn;
        private String idSettlement;
        private String comTime;
        private String notify_url;
        private Double total;
        private String hospitalID;
        private String hospitalName;
        private String district;
        private String extendedField;
        private List<info> medicalInformationList;
        private String source;

        public Builder setSource(String source) {
            this.source = source;
            return this;
        }

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

        public Builder setLyInfo(String val) {
            lyInfo = val;
            return this;
        }

        public Builder setMrn(String val) {
            mrn = val;
            return this;
        }

        public Builder setIdSettlement(String val) {
            idSettlement = val;
            return this;
        }

        public Builder setComTime(String val) {
            comTime = val;
            return this;
        }

        public Builder setNotify_url(String val) {
            notify_url = val;
            return this;
        }
        
        public Builder setTotal(Double val) {
            total = val;
            return this;
        }

        public Builder setHospitalID(String val) {
            hospitalID = val;
            return this;
        }

        public Builder setHospitalName(String val) {
            hospitalName = val;
            return this;
        }

        public Builder setDistrict(String val) {
            district = val;
            return this;
        }

        public Builder setExtendedField(String val) {
            extendedField = val;
            return this;
        }

        public Builder setMedicalInformationList(List<info> val) {
            medicalInformationList = val;
            return this;
        }

        public RegistrationArgs build() {
            return new RegistrationArgs(this);
        }
    }
}
