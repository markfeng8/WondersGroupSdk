package cn.com.epsoft.zjessc.demo.api;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cn.com.epsoft.zjessc.demo.BuildConfig;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 启研
 * @created at 2018/9/14 17:17
 */
class APIRetrofit {

    private static APIRetrofit instance;
    private static final int DEFAULT_TIME_OUT = 15;//默认的超时时间15秒钟

    protected static Retrofit getRetrofit() {
        return get().retrofit;
    }

    private static synchronized APIRetrofit get() {
        if (instance == null) {
            synchronized (APIRetrofit.class) {
                if (instance == null) {
                    instance = new APIRetrofit();
                }
            }
        }
        return instance;
    }

    private Retrofit retrofit;

    private APIRetrofit() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(logging);
        Retrofit.Builder builder = new Retrofit.Builder()
//        .addCallAdapterFactory(SessionInvalidAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .serializeNulls()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                //http://61.153.183.132:9025/access/api/gateway
                //.baseUrl(BuildConfig.DEBUG ? "http://115.236.191.153:9090/" : "http://dzsbk.zjhrss.gov.cn:8080/");
                .baseUrl(BuildConfig.DEBUG ? "http://61.153.183.132:9025/" : "http://dzsbk.zjhrss.gov.cn:8080/");
        retrofit = builder.client(clientBuilder.build()).build();
    }
}
