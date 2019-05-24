package com.wondersgroup.android.sdk.ui.afterpayhome.contract;

import com.wondersgroup.android.sdk.entity.AfterPayStateEntity;
import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.HospitalEntity;
import com.wondersgroup.android.sdk.entity.Yd0001Entity;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;

import java.util.HashMap;

/**
 * Created by x-sir on 2018/8/10 :)
 * Function:医后付首页接口的契约类
 */
public interface AfterPayHomeContract {

    interface IModel {

        void requestXy0001(HashMap<String, String> map, HttpRequestCallback<AfterPayStateEntity> callback);

        void requestYd0001(HttpRequestCallback<Yd0001Entity> callback);

        /**
         * 门诊账单基本信息查询
         *
         * @param orgCode  组织机构(医院)编码
         * @param callback 结果的回调
         */
        void requestYd0003(String orgCode, HttpRequestCallback<FeeBillEntity> callback);

        void getHospitalList(String version, String type, HttpRequestCallback<HospitalEntity> callback);

        void requestYd0002();
    }

    interface IView {

        void onXy0001Result(AfterPayStateEntity entity);

        void onYd0001Result(Yd0001Entity entity);

        void onYd0003Result(FeeBillEntity entity);

        void showLoading(boolean show);

        void onHospitalListResult(HospitalEntity body);
    }

    interface IPresenter {

        void requestXy0001(HashMap<String, String> map);

        void requestYd0001();

        /**
         * 门诊账单基本信息查询
         *
         * @param orgCode 组织机构(医院)编码
         */
        void requestYd0003(String orgCode);

        void getHospitalList(String version, String type);

        void requestYd0002();
    }
}
