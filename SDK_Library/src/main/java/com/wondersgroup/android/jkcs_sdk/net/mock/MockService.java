/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.mock;


import java.util.Map;

import okhttp3.Request;

/**
 * Created by gengqiquan on 2017/9/27.
 */

public class MockService {

    @MOCK("github_user_info")
    public MockResult auction(Request request) {
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
}
