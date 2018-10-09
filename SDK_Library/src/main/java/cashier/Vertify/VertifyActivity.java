package cashier.Vertify;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cashier.RandomUtils;
import cashier.ParamConfig;
import cn.wd.checkout.api.CheckOut;
import cn.wd.checkout.api.WDCallBack;
import cn.wd.checkout.api.WDPay;
import cn.wd.checkout.api.WDPayResult;
import cn.wd.checkout.api.WDResult;
import cn.wd.checkout.api.WDVertifyData;
import cn.wd.checkout.api.WDVertifyData.WDVertifyChannel;

/**
 * 支付宝认证
 *
 * @author kevin
 */
public class VertifyActivity extends Activity {

    private int wayid;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHss", Locale.CHINA);
    SimpleDateFormat simpleDateFormattemp = new SimpleDateFormat("SSS", Locale.CHINA);
    private EditText edtAppid, edtWorkKey, edtSubmerno, edtTransationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_vertify);

        CheckOut.setIsPrint(true);

        edtAppid = (EditText) findViewById(R.id.edt_vertify_appid);
        edtWorkKey = (EditText) findViewById(R.id.edt_vertify_work_key);
        edtSubmerno = (EditText) findViewById(R.id.edt_vertify_submerno);
        edtTransationId = (EditText) findViewById(R.id.edt_vertify_transation_id);
        initData();
    }

    private void initData() {
        wayid = ParamConfig.Networke_way;
        //	沙箱环境
        edtAppid.setText("303111700000271");
        edtWorkKey.setText("SdgBNDOPxbwQ2AliC7L9hKFs6r5nTWIG");
        edtSubmerno.setText("303111700000271");
    }

    public void getTransationId(View view) {
        edtTransationId.setText(getTransationId());
    }

    public void alipayVertify(View view) {
        String appid = edtAppid.getText().toString().trim();
        String workKey = edtWorkKey.getText().toString().trim();
        String submerno = edtSubmerno.getText().toString().trim();
        String transationid = edtTransationId.getText().toString().trim();

        /**
         * 第一步 设置访问环境 不设置为生产环境
         */
        if (wayid == 1) {
            Toast.makeText(getApplicationContext(), "联调测试环境", Toast.LENGTH_SHORT).show();
            CheckOut.setNetworkWay("CT");
        } else if (wayid == 2) {
            Toast.makeText(getApplicationContext(), "正式环境", Toast.LENGTH_SHORT).show();
            CheckOut.setNetworkWay("");

        } else {
            Toast.makeText(getApplicationContext(), "开发测试环境", Toast.LENGTH_SHORT).show();
            CheckOut.setNetworkWay("CST");
        }

        /**
         * 第二步 准备认证参数
         */
        WDVertifyData vertifyData = new WDVertifyData();
        vertifyData.setAppid(appid); // 商户id
        vertifyData.setSubmerno(submerno); // 商户号
        vertifyData.setStrAppsecret(workKey); // 商户工作密钥
        vertifyData.setTransationId(transationid); // 认证号 认证号生成规则 ： 最长32位字符串 由
        // 商户标识 + 日期 + 序列号
        vertifyData.setVertifyChannel(WDVertifyChannel.alipay_vertify); // 认证渠道
        // 暂时只支持支付宝认证渠道
        WDPay.wDVertify(this, vertifyData, new WDCallBack() {
            String info;

            @Override
            public void done(WDResult result) {
                WDPayResult r = (WDPayResult) result;
                info = r.getResult() + " : " + r.getErrMsg() + " : " + r.getDetailInfo();
                Log.i("TAG", info);
                if (!r.RESULT_SUCCESS.equals(r.getResult())) {
                    if (WDPayResult.FAIL_ALIPAY_VERSION_ERROR.equals(r.getErrMsg())) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                new AlertDialog.Builder(VertifyActivity.this).setMessage("是否下载并安装支付宝完成认证?")
                                        .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent action = new Intent(Intent.ACTION_VIEW);
                                                action.setData(Uri.parse("https://m.alipay.com"));
                                                VertifyActivity.this.startActivity(action);
                                            }
                                        }).setNegativeButton("算了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                            }
                        });
                    } else if (WDPayResult.RESULT_UNKNOWN.equals(r.getErrMsg())) {
                        //此状态需要 app请求商户自己后台认证查询接口  具体见《万达信息统一支付平台接口规范》  实名认证查询接口(WDJR-QUERY-VERTIFY-2-100)
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }

    String getTransationId() {
        return "WDXX" + simpleDateFormat.format(new Date()) + simpleDateFormattemp.format(new Date())
                + RandomUtils.generateString(10);
    }

}
