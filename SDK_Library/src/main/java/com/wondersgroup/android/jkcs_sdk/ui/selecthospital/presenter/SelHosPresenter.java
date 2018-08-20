package com.wondersgroup.android.jkcs_sdk.ui.selecthospital.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.contract.SelHosContract;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.listener.OnHospitalListListener;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.model.SelHosModel;
import com.wondersgroup.android.jkcs_sdk.utils.LogUtil;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:
 */
public class SelHosPresenter<T extends SelHosContract.IView>
        extends MvpBasePresenter<T> implements SelHosContract.IPresenter {

    private static final String TAG = SelHosPresenter.class.getSimpleName();
    private SelHosContract.IModel mModel = new SelHosModel();

    public SelHosPresenter() {
    }

    @Override
    public void getHospitalList() {
        mModel.getHospitalList(new OnHospitalListListener() {
            @Override
            public void onSuccess(HospitalEntity body) {
                LogUtil.i(TAG, "get hospital list success~");
                if (isNonNull()) {
                    mViewRef.get().returnHospitalList(body);
                }
            }

            @Override
            public void onFailed() {
                LogUtil.e(TAG, "get hospital list failed!");
            }
        });
    }
}
