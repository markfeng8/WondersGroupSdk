/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.mock;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by x-sir on 2019/1/23 :)
 * Function:
 */
public class MockRequest {

    public static Map<String, String> getQuery(Request request) {
        Map<String, String> query = new HashMap<>();
        String url = request.url().toString();
        if (url.contains("?")) {
            String[] data = url.split("\\?");
            if (data[1] != null) {
                String[] params = data[1].split("&");
                for (String param : params) {
                    String[] s = param.split("=");
                    query.put(s[0], s[1]);
                }
            }
        }

        return query;
    }
}
