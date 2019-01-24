/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.mock;


import com.wondersgroup.android.jkcs_sdk.WondersApplication;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.utils.AssetUtils;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

import java.util.Map;

import okhttp3.Request;

/**
 * Created by gengqiquan on 2017/9/27.
 */

public class MockService {

    @MOCK("github_user_info")
    public MockResult action(Request request) {
        // 如果是 GET 请求，可以取出 ？后面拼接的参数，然后拼接到返回结果中
        Map<String, String> query = MockRequest.getQuery(request);
        String name = query.get("name");
        return MockResult.create(request, "{\n" +
                "    \"status\": true,\n" +
                "    \"msg\": \"操作成功\",\n" +
                "    \"data\": {\n" +
                "        \"bond\": \"100000\",\n" +
                "        \"auction_plats\": [\n" +
                "            {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"汽车街\",\n" +
                "                \"rule_url\": \"http://www.rule.com\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");
    }

    @MOCK("/test/huzh_credit/ct/xy0001")
    public MockResult xy0001(Request request) {
        boolean state = SpUtil.getInstance().getBoolean(SpKey.MOCK_XY0001, true);
        String fileName = state ? "mock/XY0001_SUCCESS.json" : "mock/FAILED.json";
        String json = AssetUtils.getJson(WondersApplication.getsContext(), fileName);
        return MockResult.create(request, json);
    }
}
