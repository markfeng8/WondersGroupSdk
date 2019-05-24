package com.wondersgroup.android.sdk.utils;

import com.wondersgroup.android.sdk.constants.MapKey;
import com.wondersgroup.android.sdk.entity.FeeBillDetailsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by x-sir on 2018/10/17 :)
 * Function:发起结算时的工具类
 */
public class SettleUtil {

    /**
     * 获取发起试结算时的参数
     */
    public static HashMap<String, Object> getTryToSettleParam(String payPlatTradeNo, List<FeeBillDetailsBean> details) {
        HashMap<String, Object> map = new HashMap<>();
        List<HashMap<String, String>> detailsList = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            FeeBillDetailsBean detailsBean = details.get(i);
            HashMap<String, String> detailItem = new HashMap<>();
            detailItem.put(MapKey.HIS_ORDER_NO, detailsBean.getHis_order_no());
            detailItem.put(MapKey.ORDER_NO, payPlatTradeNo);
            detailsList.add(detailItem);
        }

        if (detailsList.size() > 0) {
            map.put(MapKey.DETAILS, detailsList);
        }

        return map;
    }

    /**
     * 获取发起正式结算时的参数
     *
     * @param details
     */
    public static HashMap<String, Object> getOfficialSettleParam(List<FeeBillDetailsBean> details) {
        HashMap<String, Object> map = new HashMap<>();
        List<HashMap<String, String>> detailsList = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            FeeBillDetailsBean detailsBean = details.get(i);
            HashMap<String, String> detailItem = new HashMap<>();
            detailItem.put(MapKey.HIS_ORDER_NO, detailsBean.getHis_order_no());
            detailItem.put(MapKey.ORDER_NO, "1"); // 正式结算 order_no 传 1
            detailsList.add(detailItem);
        }

        if (detailsList.size() > 0) {
            map.put(MapKey.DETAILS, detailsList);
        }

        return map;
    }
}
