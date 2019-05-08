package cn.com.epsoft.zjessc.demo;

import android.app.Application;
import android.widget.Toast;
import cn.com.epsoft.zjessc.ZjEsscSDK;
import cn.com.epsoft.zjessc.demo.model.User;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author 启研
 * @created at 2018/11/15 14:56
 */
public class App extends Application {

  public static String channelNo = "3300000001";

  private static App app;

  /**
   * 获取Application单例
   */
  public static App getInstance() {
    if (app == null) {
      throw new NullPointerException("MyApplication is null!");
    }
    return app;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    app = this;
    RxJavaPlugins.setErrorHandler(throwable -> Toast
        .makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show());
    //集成省电子社保卡
    ZjEsscSDK.init(BuildConfig.DEBUG, this, channelNo);
//    ZjEsscSDK.openDev(false);
    ZjEsscSDK.setTitleColor("#147038");
    ZjEsscSDK.setTextColor("#FFFFFF");
  }

  public static User getUser() {
    return app.user;
  }

  private User user = new User("421221198709254816", "禹波", "");
}
