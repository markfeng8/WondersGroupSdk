package com.epsoft.hzauthsdk.utils;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/3/18  12:58
 */

public class TestArgs {

/*    public static String name	="周振峰";//	姓名
    public static  String cardNum	="D11694418";//	社保卡号
    public static  String account	="330521195210221732";//	社会保障号
    public static  String  cbd="330521";//	参保地行政区划*/
    
    public static String name	="谈子平";//	姓名
    public static String cardNum	="D12034585";//	社保卡号
    public static String account	="330521194701031322";//	社会保障号
    public static String cbd="330521";//	参保地行政区划
    
/*    public static String name	="张三";//	姓名
    public static  String cardNum	="M01373896";//	社保卡号
    public static  String account	="330521199010151586";//	社会保障号
    public static  String  cbd="330599";//	参保地行政区划*/
    
    public static String phoneNum="13754326266";//	手机号码
    //平台id
    public static String appId="8934050918";//		身份证号码
    
    public static String certNum="330521199010151586";//		身份证号码
    public static String cbdName="德清";//	参保地名称
    public static String lyInfo="E";//	参保地名称
    public static String mrn="445343434";//	病历号
    public static String idSettlement(){
        return System.currentTimeMillis()+"";
    };//		订单编号
    public static String comTime(){
        return Utils.getCurrentTimeStr()+"";
    };//	提交时间
    public static String notify_url="后台调用地址";//	后台调用地址
    public static Double total=0.01d;//	总费用
    public static String hospitalID="1";//		医院ID
    public static String hospitalName="大白" ;//		医院名称
    public static String ad="z88888";//		住院号
    public static String district	="院区";//		院区
    public static String extendedField="扩展字段";//	扩展字段
    public static String recipeCode(){
        return System.currentTimeMillis()+"";
    }
    public static String source="5";
    
    
    public static String preid(){
        return System.currentTimeMillis()+"";
    };
    public static String pbCode="1";
    public static String date(){
        return Utils.getCurrentTimeYMD();
    };
    public static String seqNum="2";
    public static String timePart="1";
    public static String docCode="1";
    public static String docName="未知";
    public static String visitTime(){
        return Utils.getCurrentTimeStr();
    };
    public static String cusName="1111";
    public static String cftype="1";
    public static String ybLevel="1";
    public static Double fee=0.01d;
    public static Double num=10d;
    public static Double zfbl=0.5d;
    public static Double zffee=0.05d;
    

}
