package com.wondersgroup.android.jkcs_sdk.net.service;

import com.wondersgroup.android.jkcs_sdk.entity.SettleEntity;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:发起试结算、正式结算的 Service
 */
public interface SettleService {

    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Call<SettleEntity> toSettle(
            @Path(value = "url", encoded = true) String url,
            @Body RequestBody body);
}
