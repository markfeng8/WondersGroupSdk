package com.wondersgroup.android.jkcs_sdk.net.service;

import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;

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
public interface FeeRecordService {

    //  POST Map body 请求
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Call<FeeRecordEntity> getFeeRecord(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);
}
