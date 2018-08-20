package com.wondersgroup.android.jkcs_sdk.ui.selecthospital.view;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseActivity;
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

    @Override
    protected SelHosPresenter<SelHosContract.IView> createPresenter() {
        return new SelHosPresenter<>();
    }

    @Override
    protected void bindView() {
        setContentView(R.layout.activity_select_hospital);
        findViews();
        initData();
        initListener();
    }

    private void initListener() {
        ivBackBtn.setOnClickListener(v -> SelectHospitalActivity.this.finish());
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
            List<HospitalEntity.DetailsBean> beanList = body.getDetails();
            if (beanList != null && beanList.size() > 0) {
                mAdapter = new HospitalAdapter(SelectHospitalActivity.this, beanList);
                listView.setAdapter(mAdapter);
            }
        }
    }
}
