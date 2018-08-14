package com.wondersgroup.android.jkcs_sdk.net.service;

import com.wondersgroup.android.jkcs_sdk.entity.MobilePayEntity;

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
public interface MobilePayService {

    //  POST Map body 请求
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Call<MobilePayEntity> findMobilePayState(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);
}
