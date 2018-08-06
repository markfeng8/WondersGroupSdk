package cashier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.wondersgroup.android.jkcs_sdk.R;

import cashier.Vertify.VertifyActivity;

/*
 *@作者: kevin
 *@版本:
 *@version create time：2017-5-10 下午2:33:35
 */
public class PayFunctionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_function);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayFunctionActivity.this, WdPayMainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayFunctionActivity.this, VertifyActivity.class);
                startActivity(intent);
            }
        });
    }

}


