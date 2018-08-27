package cashier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.R;

/*
 *@作者: Administrator
 *@版本:
 *@version create time：2016-4-25 下午2:05:31
 */
public class SetWorkKeyActivity extends Activity {

    private EditText edtAppid;
    private EditText edtAppSecret;
    private Button btnSet;
    private EditText edtAppSubmerno;
    private EditText edtAppSubmerName;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_set_work_key);
        initVeiw();
        initListener();
    }

    private void initVeiw() {
        edtAppid = (EditText) findViewById(R.id.edt_set_key_appid);
        edtAppSecret = (EditText) findViewById(R.id.edt_set_key_app_secret);
        edtAppSubmerno = (EditText) findViewById(R.id.edt_set_key_app_submerno);
        edtAppSubmerName = (EditText) findViewById(R.id.edt_set_key_app_submer_name);
        btnSet = (Button) findViewById(R.id.btn_set_key_set);
        rg = (RadioGroup) findViewById(R.id.rg_main_huanjing);

        edtAppid.setText("wd2015tst001");
        edtAppSecret.setText("6XtC7H8NuykaRv423hrf1gGS09FEZQoB");
        edtAppSubmerno.setText("wdtstsub00001");
        edtAppSubmerName.setText("万达信息");
    }

    private void initListener() {
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String strAppid = edtAppid.getText().toString().trim();
                String strAppsecret = edtAppSecret.getText().toString().trim();
                String strAppSubmernot = edtAppSubmerno.getText().toString().trim();
                String strAppSubmerName = edtAppSubmerName.getText().toString().trim();

                if (TextUtils.isEmpty(strAppSubmerName) || TextUtils.isEmpty(strAppid) || TextUtils.isEmpty(strAppsecret) || strAppsecret.length() != 32 || TextUtils.isEmpty(strAppSubmernot)) {
                    Toast.makeText(getApplicationContext(), "请设置参数", Toast.LENGTH_LONG).show();
                    return;
                }

                SPUtils.put(SetWorkKeyActivity.this, SPUtils.MERAPPID, strAppid);
                SPUtils.put(SetWorkKeyActivity.this, SPUtils.MERAPPKEY, strAppsecret);
                SPUtils.put(SetWorkKeyActivity.this, SPUtils.MERSUBMERNO, strAppSubmernot);
                SPUtils.put(SetWorkKeyActivity.this, SPUtils.MERAPPNAME, strAppSubmerName);

                Intent intent = new Intent(getApplicationContext(), PayFunctionActivity.class);
                startActivity(intent);
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                int buttonId = arg0.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) SetWorkKeyActivity.this.findViewById(buttonId);
                if (buttonId == R.id.radioButton1) { // 联调环境
                    ParamConfig.Networke_way = 1;
                } else if (buttonId == R.id.radioButton2) { // 正式环境
                    ParamConfig.Networke_way = 2;
                } else if (buttonId == R.id.radioButton3) { // 开发测试环境
                    ParamConfig.Networke_way = 0;
                }
            }
        });
    }

}


