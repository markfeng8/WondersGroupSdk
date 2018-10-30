package com.wondersgroup.android.jkcs_sdk.ui.afterpayhome.contract;

import android.app.Activity;

import com.wondersgroup.android.jkcs_sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnAfterPayStateListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnHospitalListListener;
import com.wondersgroup.android.jkcs_sdk.listener.OnYiBaoMobStatusListener;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页接口的契约类
 */
public interface AfterPayHomeContract {

    interface IModel {
        void getAfterPayState(HashMap<String, String> map, OnAfterPayStateListener listener);

        void requestYd0003(HashMap<String, String> map, OnFeeDetailListener listener);

        void getHospitalList(OnHospitalListListener listener);

        void queryYiBaoOpenStatus(WeakReference<Activity> weakReference, OnYiBaoMobStatusListener listener);
    }

    interface IView {
        void afterPayResult(AfterPayStateEntity entity);

        void onYd0003Result(FeeBillEntity entity);

        void showLoading();

        void dismissLoading();

        void onHospitalListResult(HospitalEntity body);

        void onYiBaoOpenStatusResult(String status);
    }

    interface IPresenter {
        void getAfterPayState(HashMap<String, String> map);

        void requestYd0003(HashMap<String, String> map);

        void getHospitalList();

        void queryYiBaoOpenStatus(Activity activity);
    }
}
