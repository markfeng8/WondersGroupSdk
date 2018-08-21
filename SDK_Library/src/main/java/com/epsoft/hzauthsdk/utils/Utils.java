package com.epsoft.hzauthsdk.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 标题     :
 * 逻辑简介  ：
 * Company  : dabay
 * Author   : yangpf
 * Date     : 2018/3/19  19:28
 */

public class Utils {
    /**
     * 解析微信关键字
     * @param 
     * @return
     */
/*    public static PayReq WXInfoParse(String info) throws JSONException {

        String[] orders = info.split("&");

        String[] split = null;


        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < orders.length; i++) {
            split = orders[i].split("=");
            for (int j = 0; j < split.length; j++) {
                map.put(split[0], split[1]);
            }
        }


        Gson gson = new Gson();

        String json = gson.toJson(map);

        JSONObject jsonData = new JSONObject(json);

        String appId = jsonData.getString("appId");

        String partnerId = jsonData.getString("partnerId");

        String prepayId = jsonData.getString("prepayId");

        String packageValue = jsonData.getString("packageValue");

        String nonceStr = jsonData.getString("nonceStr");

        String timeStamp = jsonData.getString("timeStamp");

        String sign = jsonData.getString("sign");
        
        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;
        return req;
    }*/

    //获取当前时间
    public static String getCurrentTimeStr() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
    //获取当前时间
    public static String getCurrentTimeYMD() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    //解析json
    public static <T> T decodeJSON(String jsonString, Class<T> cls)
            throws JsonSyntaxException {
        Gson gson = new Gson();
        T model = gson.fromJson(jsonString, cls);
        return model;
    }
}
