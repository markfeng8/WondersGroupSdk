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
 * Created by gengqiquan on 2017/9/27.
 */

public class MockInterceptor implements Interceptor {

    private Parrot mParrot;

    public MockInterceptor(Parrot parrot) {
        this.mParrot = parrot;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = mParrot.mockResult(request);
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


