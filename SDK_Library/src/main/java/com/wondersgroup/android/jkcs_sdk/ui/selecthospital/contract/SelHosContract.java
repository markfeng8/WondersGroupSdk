package com.wondersgroup.android.jkcs_sdk.ui.selecthospital.contract;

import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.ui.selecthospital.listener.OnHospitalListListener;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:
 */
public interface SelHosContract {

    interface IModel {
        void getHospitalList(OnHospitalListListener listener);
    }

    interface IView {
        void returnHospitalList(HospitalEntity body);
    }

    interface IPresenter {

        void getHospitalList();
    }
}
