/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.net.service;

import com.wondersgroup.android.sdk.entity.SignEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by x-sir on 2019/3/29 :)
 * Function:获取请求参数签名的接口
 */
public interface SignatureService {

    /**
     * 获取签名(测试用途，接口随时会关掉，需集成方App服务端去实现)
     *
     * @param url  请求的 url，适用于动态域名访问，当url为全域名时，会使用url的全域访问，当为非全域时，会拼接到BASE_URL的后面
     * @param body 签名数据
     */
    // BuildConfig.DEBUG?"http://115.236.191.153:9090/":"http://dzsbk.zjhrss.gov.cn:8080/"
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST
//    Observable<Response<String>> getSign(@Url String url, @Body RequestBody body);
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Observable<SignEntity> getSign(@Path(value = "url", encoded = true) String url,
                                             @Body Map<String, String> maps);
}
