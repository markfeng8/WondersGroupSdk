package com.epsoft.hzauthsdk.utils;

import com.epsoft.hzauthsdk.pub.BusinessArgs;
import com.epsoft.hzauthsdk.pub.ChangePWArgs;
import com.google.gson.Gson;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/3/18  15:28
 */

public class MakeArgsFactory {
    
    static {
        gson = new Gson();
    }

    private static Gson gson;
    

    //开通
    public static BusinessArgs getBussArgs(String mobile){
        return new BusinessArgs.Builder()
                .setAccount(TestArgs.account)
                .setAuthChannel("2")
                .setCardNum(TestArgs.cardNum)
                .setCbd(TestArgs.cbd)
                .setCbdName(TestArgs.cbdName)
                .setCertNum(TestArgs.certNum)
                .setExtendedField(TestArgs.extendedField)
                .setName(TestArgs.name)
                .setPhoneNum(mobile)
                .build();
    }
    //修改
    public static ChangePWArgs getChangeArgs(){
        return  new ChangePWArgs.Builder()
                .setAuthChannel("2")
                .setCardNum(TestArgs.cardNum)
                .setExtendedField(TestArgs.extendedField)
                .build();
       
    }
}
