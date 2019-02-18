package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract;

import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页接口的契约类
 */
public interface AfterPayHomeContract {

    interface IModel {

        void getAfterPayState(HashMap<String, String> map, HttpRequestCallback<AfterPayStateEntity> callback);

        void requestYd0003(String orgCode, HttpRequestCallback<FeeBillEntity> callback);

        void getHospitalList(String version, String type, HttpRequestCallback<HospitalEntity> callback);

        void uploadMobilePayState();
    }

    interface IView {

        void afterPayResult(AfterPayStateEntity entity);

        void onYd0003Result(FeeBillEntity entity);

        void showLoading();

        void dismissLoading();

        void onHospitalListResult(HospitalEntity body);
    }

    interface IPresenter {

        void getAfterPayState(HashMap<String, String> map);

        void requestYd0003(String orgCode);

        void getHospitalList(String version, String type);

        void uploadMobilePayState();
    }
}
