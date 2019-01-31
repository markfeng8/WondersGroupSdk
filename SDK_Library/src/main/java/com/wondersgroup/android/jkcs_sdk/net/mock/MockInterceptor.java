/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.mock;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by x-sir on 2019/1/23 :)
 * Function:
 */
public class MockInterceptor implements Interceptor {

    private Mocker mMocker;

    public MockInterceptor(Mocker mocker) {
        this.mMocker = mocker;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = mMocker.mockResult(request);
        if (response != null) {
            return response;
        }

        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}


