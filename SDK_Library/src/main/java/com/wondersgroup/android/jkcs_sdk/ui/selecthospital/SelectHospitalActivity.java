package com.wondersgroup.android.jkcs_sdk.ui.selecthospital;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;

// 选择医院页面
public class SelectHospitalActivity extends AppCompatActivity {

    private ImageView ivBackBtn;
    private TextView tvTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hospital);
        findViews();
        initData();
        initListener();
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectHospitalActivity.this.finish();
            }
        });
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_select_hospital_please));
    }

    private void findViews() {
        ivBackBtn = findViewById(R.id.ivBackBtn);
        tvTitleName = findViewById(R.id.tvTitleName);
    }
}
