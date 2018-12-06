/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.net.service;

import com.wondersgroup.android.jkcs_sdk.entity.Cy0006Entity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:
 */
public interface Cy0006Service {

    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Call<Cy0006Entity> cy0006(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);
}
