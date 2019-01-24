/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.mock;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by gengqiquan on 2017/9/27.
 */

public class MockResult {

    private MockResult(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    private Response response;

    public static MockResult create(Request request, String json) {
        return new Builder(request).content(json).build();
    }

    public static class Builder {
        Response.Builder responseBuilder;
        MediaType mediaType = MediaType.parse("application/json;charset=UTF-8");

        public Builder(Request request) {
            responseBuilder = new Response.Builder()
                    .request(request)
                    .code(200)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .sentRequestAtMillis(-1L)
                    .message("OK")
                    .protocol(Protocol.HTTP_1_1);
        }

        public Builder content(String content) {
            ResponseBody body = ResponseBody.create(mediaType, content);
            responseBuilder.body(body);
            return this;
        }

        public Builder code(int code) {
            responseBuilder.code(code);
            return this;
        }

        public Builder body(ResponseBody body) {
            responseBuilder.body(body);
            return this;
        }

        public Builder header(String name, String value) {
            responseBuilder.header(name, value);
            return this;
        }

        public Builder headers(Headers headers) {
            responseBuilder.headers(headers);
            return this;
        }

        public Builder protocol(Protocol protocol) {
            responseBuilder.protocol(protocol);
            return this;
        }

        public Builder message(String message) {
            responseBuilder.message(message);
            return this;
        }

        public MockResult build() {
            return new MockResult(responseBuilder.build());
        }
    }
}
