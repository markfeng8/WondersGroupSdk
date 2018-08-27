package com.wondersgroup.android.jkcs_sdk.ui.selecthospital.view;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
import com.wondersgroup.android.jkcs_sdk.cons.IntentExtra;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.ui.adapter.HospitalAdapter;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.contract.SelHosContract;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.presenter.SelHosPresenter;

import java.util.List;

// 选择医院页面
public class SelectHospitalActivity extends MvpBaseActivity<SelHosContract.IView,
        SelHosPresenter<SelHosContract.IView>> implements SelHosContract.IView {

    private ImageView ivBackBtn;
    private TextView tvTitleName;
    private ListView listView;
    private HospitalAdapter mAdapter;
    private List<HospitalEntity.DetailsBean> mBeanList;

    @Override
    protected SelHosPresenter<SelHosContract.IView> createPresenter() {
        return new SelHosPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.wonders_group_activity_select_hospital);
        findViews();
        initData();
        initListener();
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(v -> SelectHospitalActivity.this.finish());
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (mBeanList != null && mBeanList.size() > 0) {
                HospitalEntity.DetailsBean bean = mBeanList.get(position);
                String orgCode = bean.getOrg_code();
                String orgName = bean.getOrg_name();
                Intent intent = new Intent();
                intent.putExtra(IntentExtra.ORG_CODE, orgCode);
                intent.putExtra(IntentExtra.ORG_NAME, orgName);
                setResult(IntentExtra.RESULT_CODE, intent);
                SelectHospitalActivity.this.finish();
            }
        });
    }

    private void initData() {
        tvTitleName.setText(getString(R.string.wonders_select_hospital_please));
        mPresenter.getHospitalList();
    }

    private void findViews() {
        ivBackBtn = findViewById(R.id.ivBackBtn);
        tvTitleName = findViewById(R.id.tvTitleName);
        listView = findViewById(R.id.listView);
    }

    @Override
    public void returnHospitalList(HospitalEntity body) {
        if (body != null) {
            mBeanList = body.getDetails();
            if (mBeanList != null && mBeanList.size() > 0) {
                mAdapter = new HospitalAdapter(SelectHospitalActivity.this, mBeanList);
                listView.setAdapter(mAdapter);
            }
        }
    }
}
