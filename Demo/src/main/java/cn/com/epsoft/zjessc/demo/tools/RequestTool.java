package cn.com.epsoft.zjessc.demo.tools;

import android.widget.Toast;

import cn.com.epsoft.zjessc.demo.App;
import cn.com.epsoft.zjessc.demo.api.UserApi;
import cn.com.epsoft.zjessc.demo.base.BaseActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 签名测试类
 *
 * @author 启研
 * @created at 2018/11/23 15:26
 */
@Deprecated
public class RequestTool {


    private static RequestBody getSignBody(String channelNo, String aac002, String aac003,
                                           String aab301, String signNo, String busiSeq, String aac067, String historyFlag,
                                           String returnUrl, String qrCode) {
        return RequestBody.create(MediaType.parse("Content-Type, application/json"),
                "{\"channelNo\":\"" + channelNo + "\","
                        + "\"aac002\":\"" + aac002 + "\","
                        + "\"aac003\":\"" + aac003 + "\","
                        + "\"aab301\":\"" + aab301 + "\","
                        + "\"signNo\":\"" + signNo + "\","
                        + "\"busiSeq\":\"" + busiSeq + "\","
                        + "\"aac067\":\"" + aac067 + "\","
                        + "\"historyFlag\":\"" + historyFlag + "\","
                        + "\"returnUrl\":\"" + returnUrl + "\","
                        + "\"qrCode\":\"" + qrCode + "\""
                        + "}");
    }

    public static void getSign(BaseActivity activity, String aac002, String aac003,
                               String aab301, String signNo, String busiSeq, String aac067, String historyFlag,
                               String returnUrl, String qrCode, Consumer<String> consumer) {

        Disposable d = UserApi.request()
                .getSign(RequestTool.getSignBody(App.channelNo, aac002, aac003, aab301,
                        signNo, busiSeq, aac067, historyFlag, returnUrl, qrCode))
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> activity.showProgress(false))
                .subscribe(r -> {
                    if (r.success) {
                        consumer.accept(r.body);
//            startSdk(idcard, name, r.body);
                    } else {
                        Toast.makeText(activity, r.message, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
