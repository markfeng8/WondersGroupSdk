package cn.com.epsoft.zjessc.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cn.com.epsoft.zjessc.demo.BuildConfig;
import cn.com.epsoft.zjessc.demo.R;
import cn.com.epsoft.zjessc.demo.base.BaseActivity;
import cn.com.epsoft.zjessc.tools.ZjBiap;

/**
 * @author 启研
 * @created at 2018/11/15 14:59
 */
public class SupportMethodActivity extends BaseActivity {

  private ListView listView;
  private String[] datas = {
      "电子社保卡(申领)",
      "电子社保卡",
      "支付密码验证",
      "短信验证",
      "人脸识别验证",
      "身份二维码",
      "独立扫码登录",
//      "收银台",
//      "独立服务支付码",
//      "设置指纹支付"
  };

  Toolbar toolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.act_support_method);
    toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(BuildConfig.DEBUG ? "测试环境" : "正式环境");
    setSupportActionBar(toolbar);
    listView = findViewById(R.id.listView);
    listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas));
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = "";
        boolean hideSigin = false;
        switch (position) {
          case 0:
            hideSigin = true;
          case 1:
            url = ZjBiap.getInstance().getIndexUrl();
            break;
          case 2:
            url = ZjBiap.getInstance().getValidPwd();
            break;
          case 3:
            url = ZjBiap.getInstance().getValidSms();
            break;
          case 4:
            url = ZjBiap.getInstance().getFaceValidate();
            break;
          case 5:
            url = ZjBiap.getInstance().getQr();
            break;
          case 6:
            url = ZjBiap.getInstance().getScanValidateUrl();
            break;
//          case 6:
//            url = ZjBiap.getInstance().getPayUrl();
//            break;
//          case 7:
//            url = ZjBiap.getInstance().getPayCodeUrl();
//            break;
//          case 6:
//            url = ZjBiap.getInstance().getSetFingerprint();
//            break;
        }
        Intent intent = new Intent(getBaseContext(), UserInfoActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", datas[position]);
        intent.putExtra("hide", hideSigin);
        startActivity(intent);
      }
    });
  }
}
