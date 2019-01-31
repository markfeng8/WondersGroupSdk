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
 * Created by x-sir on 2019/1/23 :)
 * Function:mock 数据源服务类，只要在这个类中写了某个接口的方法，就会自动返回这里的数据
 */
public class MockService {

    /**
     * -------------- XY0001 -------------------------------------
     */

    @MOCK("/huzh_credit/ct/xy0001")
    public MockResult xy0001(Request request) {
        return getXy0001Result(request);
    }

    @MOCK("/test/huzh_credit/ct/xy0001")
    public MockResult xy0001Test(Request request) {
        return getXy0001Result(request);
    }

    private MockResult getXy0001Result(Request request) {
        boolean state = SpUtil.getInstance().getBoolean(SpKey.MOCK_XY0001, true);
        String fileName = state ? "mock/XY0001_SUCCESS.json" : "mock/FAILED.json";
        return MockResult.create(request, getJson(fileName));
    }

    /**
     * -------------- xy0008 --------------------------------------
     */

    @MOCK("/huzh_credit/ct/xy0008")
    public MockResult xy0008(Request request) {
        return getXy0008Result(request);
    }

    @MOCK("/test/huzh_credit/ct/xy0008")
    public MockResult xy0008Test(Request request) {
        return getXy0008Result(request);
    }

    private MockResult getXy0008Result(Request request) {
        boolean state = SpUtil.getInstance().getBoolean(SpKey.MOCK_XY0008, true);
        String fileName = state ? "mock/XY0008_SUCCESS.json" : "mock/FAILED.json";
        return MockResult.create(request, getJson(fileName));
    }

    /**
     * -------------- yd0003 ----------------------------------------
     */

    @MOCK("/huzh_credit/sdk/yd0003")
    public MockResult yd0003(Request request) {
        return getYd0003Result(request);
    }

    @MOCK("/test/huzh_credit/sdk/yd0003")
    public MockResult yd0003Test(Request request) {
        return getYd0003Result(request);
    }

    private MockResult getYd0003Result(Request request) {
        boolean state = SpUtil.getInstance().getBoolean(SpKey.MOCK_YD0003, true);
        String fileName = state ? "mock/YD0003_SUCCESS.json" : "mock/FAILED.json";
        return MockResult.create(request, getJson(fileName));
    }

    private String getJson(String fileName) {
        return AssetUtils.getJson(WondersApplication.getsContext(), fileName);
    }

    @MOCK("/getUserInfo")
    public MockResult action(Request request) {
        // 如果是 GET 请求，可以取出 ？后面拼接的参数，然后拼接到返回结果中
        Map<String, String> query = MockRequest.getQuery(request);
        String name = query.get("name");
        return MockResult.create(request, "{\n" +
                "    \"status\": true,\n" +
                "    \"msg\": \"SUCCESS\",\n" +
                "    \"data\": {\n" +
                "        \"bond\": \"100000\",\n" +
                "        \"bio\": [\n" +
                "            {\n" +
                "                \"id\": \"1\",\n" +
                "                \"name\": \"hell0\",\n" +
                "                \"rule_url\": \"http://www.x-sir.com\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");
    }
}
