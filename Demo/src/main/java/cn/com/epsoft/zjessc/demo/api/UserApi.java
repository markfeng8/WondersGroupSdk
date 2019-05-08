package cn.com.epsoft.zjessc.demo.api;

import cn.com.epsoft.zjessc.demo.model.Response;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 用户相关请求
 *
 * @author 启研
 * @created at 2018/9/14 17:14
 */
public class UserApi {

  static Api api;

  public static synchronized Api request() {
    if (api == null) {
      synchronized (UserApi.class) {
        if (api == null) {
          api = APIRetrofit.getRetrofit().create(Api.class);
        }
      }
    }
    return api;
  }

  public interface Api {

    /**
     * 获取签名(测试用途，接口随时会关掉，需集成方App服务端去实现)
     *
     * @param body 签名数据
     */
    @Deprecated
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    //@POST("sdk/csb/signTest")
    @POST("access/api/gateway")
    Observable<Response<String>> getSign(@Body RequestBody body);
  }
}
