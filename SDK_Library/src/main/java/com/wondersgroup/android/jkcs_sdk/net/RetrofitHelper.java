package com.wondersgroup.android.jkcs_sdk.net;

import com.wondersgroup.android.jkcs_sdk.BuildConfig;
import com.wondersgroup.android.jkcs_sdk.cons.RequestUrl;
import com.wondersgroup.android.jkcs_sdk.cons.SpKey;
import com.wondersgroup.android.jkcs_sdk.net.interceptor.LoggerInterceptor;
import com.wondersgroup.android.jkcs_sdk.net.mock.MockInterceptor;
import com.wondersgroup.android.jkcs_sdk.net.mock.MockService;
import com.wondersgroup.android.jkcs_sdk.net.mock.Parrot;
import com.wondersgroup.android.jkcs_sdk.net.service.ApiService;
import com.wondersgroup.android.jkcs_sdk.utils.SpUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:Retrofit 2.x 网络请求的封装类
 */
public class RetrofitHelper {

    private static final String TAG = "RetrofitHelper";
    private static final String BASE_URL = RequestUrl.HOST; // host
    private static final long DEFAULT_TIMEOUT = 60000L; // timeout millis

    private Retrofit retrofit;
    private OkHttpClient client;

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    /**
     * private constructor.
     */
    private RetrofitHelper() {
        // HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(MyApplication.getIntstance(), new int[0], R.raw.ivms8700, STORE_PASS);
        // 包含header、body数据

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new LoggerInterceptor());
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        client = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                //.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //.hostnameVerifier(HttpsUtils.getHostnameVerifier())
                //.addInterceptor(new HeaderInterceptor()) // 添加请求头
                .addNetworkInterceptor(loggingInterceptor) // 添加日志打印拦截器
                .build();

        // 是否需要设置模拟请求数据
        boolean isMock = SpUtil.getInstance().getBoolean(SpKey.IS_MOCK, false);
        if (isMock) {
            client = client.newBuilder()
                    .addInterceptor(new MockInterceptor(Parrot.create(MockService.class)))
                    .build();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) // 添加 Gson 解析
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加rxJava
                .build();
    }

    /**
     * default service.
     *
     * @return
     */
    public ApiService createService() {
        return createService(ApiService.class);
    }

    /**
     * 这里返回一个泛型类，主要返回的是定义的接口类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T createService(Class<T> clazz) {
        if (clazz == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(clazz);
    }
}
