package com.wondersgroup.android.sdk.ui.afterpayrules;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.utils.StatusBarUtils;

/**
 * Created by x-sir on 2018/8/1 :)
 * Function:医后付协议页面
 */
public class AfterPayRuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wonders_group_activity_after_pay_rule);
        StatusBarUtils.tint(this);
    }
}
