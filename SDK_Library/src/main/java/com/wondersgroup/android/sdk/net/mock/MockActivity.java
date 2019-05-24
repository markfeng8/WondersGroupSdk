/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.net.mock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wondersgroup.android.sdk.R;
import com.wondersgroup.android.sdk.constants.TranCode;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.SpUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by x-sir on 2019/1/23 :)
 * Function:
 */
public class MockActivity extends AppCompatActivity {

    private static final String TAG = "MockActivity";
    private TextView tvBack;
    private TextView tvReset;
    private TextView tvSave;
    private RecyclerView recyclerView;

    private static final String[] mApi = {TranCode.TRAN_XY0001, TranCode.TRAN_XY0002, TranCode.TRAN_XY0003, TranCode.TRAN_XY0004, TranCode.TRAN_XY0005, TranCode.TRAN_XY0006, TranCode.TRAN_XY0008, TranCode.TRAN_YD0001, TranCode.TRAN_YD0002, TranCode.TRAN_YD0003, TranCode.TRAN_YD0004, TranCode.TRAN_YD0005, TranCode.TRAN_YD0006, TranCode.TRAN_YD0007, TranCode.TRAN_YD0008, TranCode.TRAN_YD0009, TranCode.TRAN_YD0010, TranCode.TRAN_CY0001, TranCode.TRAN_CY0002, TranCode.TRAN_CY0003, TranCode.TRAN_CY0004, TranCode.TRAN_CY0005, TranCode.TRAN_CY0006, TranCode.TRAN_CY0007};

    private List<MockBean> mList;
    private MockAdapter mMockAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);
        tvBack = findViewById(R.id.tvBack);
        tvReset = findViewById(R.id.tvReset);
        tvSave = findViewById(R.id.tvSave);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        initData();
        initListener();
    }

    private void initListener() {
        tvBack.setOnClickListener(v -> {
            WToastUtil.show("设置取消~");
            finish();
        });
        tvReset.setOnClickListener(v -> {
            for (MockBean mockBean : mList) {
                mockBean.state = Boolean.TRUE;
            }

            if (mMockAdapter != null) {
                mMockAdapter.notifyDataSetChanged();
            }
        });
        tvSave.setOnClickListener(v -> saveMockState());
    }

    private void saveMockState() {
        HashMap<String, Object> map = new HashMap<>();
        for (MockBean mockBean : mList) {
            String key = "Mock_" + mockBean.api;
            boolean value = mockBean.state;
            map.put(key, value);
        }

        SpUtil.getInstance().save(map);

        Intent intent = new Intent();
        intent.putExtra("isMocker", true);
        setResult(RESULT_OK, intent);
        WToastUtil.show("设置成功~");
        finish();
    }

    private void initData() {
        mList = new ArrayList<>();
        for (String aMApi : mApi) {
            MockBean mockBean = new MockBean();
            mockBean.api = aMApi;
            mockBean.state = SpUtil.getInstance().getBoolean("Mock_" + mockBean.api, true);
            mList.add(mockBean);
        }

        mMockAdapter = new MockAdapter(mList);
        recyclerView.setAdapter(mMockAdapter);
    }

    class MockBean {
        public String api;
        public Boolean state;
    }

    class MockAdapter extends BaseQuickAdapter<MockBean, BaseViewHolder> {

        public MockAdapter(@Nullable List<MockBean> data) {
            super(R.layout.wonders_group_item_mock, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, MockBean item) {
            helper.setText(R.id.tvApiName, item.api);
            ToggleButton toggleButton = helper.getView(R.id.tbState);
            toggleButton.setChecked(item.state);
            helper.setOnCheckedChangeListener(R.id.tbState, (buttonView, isChecked) -> {
                item.state = isChecked ? Boolean.TRUE : Boolean.FALSE;
            });
        }
    }

    public static void actionStart(Context context, int requestCode) {
        if (context != null) {
            Intent intent = new Intent(context, MockActivity.class);
            ((Activity) context).startActivityForResult(intent, requestCode);
        } else {
            LogUtil.e(TAG, "context is null!");
        }
    }
}
