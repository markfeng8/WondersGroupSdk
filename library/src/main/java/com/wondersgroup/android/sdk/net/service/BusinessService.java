/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.net.service;

import com.wondersgroup.android.sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.sdk.entity.BaseEntity;
import com.wondersgroup.android.sdk.entity.Cy0001Entity;
import com.wondersgroup.android.sdk.entity.Cy0005Entity;
import com.wondersgroup.android.sdk.entity.Cy0006Entity;
import com.wondersgroup.android.sdk.entity.Cy0007Entity;
import com.wondersgroup.android.sdk.entity.EleCardTokenEntity;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.sdk.entity.HospitalEntity;
import com.wondersgroup.android.sdk.entity.LockOrderEntity;
import com.wondersgroup.android.sdk.entity.MobilePayEntity;
import com.wondersgroup.android.sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.sdk.entity.PayParamEntity;
import com.wondersgroup.android.sdk.entity.SettleEntity;
import com.wondersgroup.android.sdk.entity.SignEntity;
import com.wondersgroup.android.sdk.entity.SmsEntity;
import com.wondersgroup.android.sdk.entity.Yd0001Entity;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:业务接口服务类
 */
public interface BusinessService {

    /**
     * 查询医后付状态
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<AfterPayStateEntity>> findAfterPayState(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 查询账单信息
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<FeeBillEntity>> getBillInfo(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 获取医院列表
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<HospitalEntity>> getHosList(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 查询移动支付状态
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<MobilePayEntity>> findMobilePayState(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * yd0001 查询电子社保卡开通状态
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<Yd0001Entity>> yd0001(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 锁单接口
     *
     * @param url  request url
     * @param body request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<LockOrderEntity>> lockOrder(
            @Path(value = "url", encoded = true) String url,
            @Body RequestBody body);

    /**
     * 获取账单明细
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<OrderDetailsEntity>> getOrderDetails(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 发起试结算、正式结算
     *
     * @param url  request url
     * @param body request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<SettleEntity>> toSettle(
            @Path(value = "url", encoded = true) String url,
            @Body RequestBody body);

    /**
     * 获取支付参数（要素）
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<PayParamEntity>> getPayParams(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 获取电子社保卡 token
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Flowable<Response<EleCardTokenEntity>> getToken(
            @Url String url,
            @Body Map<String, String> maps);

    /**
     * cy0001 查询住院信息
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<Cy0001Entity>> cy0001(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * cy0005
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<Cy0005Entity>> cy0005(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * cy0006
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<Cy0006Entity>> cy0006(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * cy0007
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Flowable<Response<Cy0007Entity>> cy0007(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 获取缴费记录
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<FeeRecordEntity>> getFeeRecord(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 获取短信验证码
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<SmsEntity>> sendSmsCode(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 修改手机号
     *
     * @param url  request url
     * @param maps request parameters
     * @return result that request
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("{url}")
    Flowable<Response<BaseEntity>> updatePhone(
            @Path(value = "url", encoded = true) String url,
            @Body Map<String, String> maps);

    /**
     * 获取签名(测试用途，接口随时会关掉，需集成方App服务端去实现)
     *
     * @param url  请求的 url，适用于动态域名访问，当url为全域名时，会使用url的全域访问，当为非全域时，会拼接到BASE_URL的后面
     * @param maps 签名数据
     * @return result that request
     */
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @POST("{url}")
    Observable<SignEntity> getSign(@Path(value = "url", encoded = true) String url,
                                   @Body Map<String, String> maps);
}
