/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.recorddetail.contract;

import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.listener.OnFeeDetailListener;

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:
 */
public interface RecordDetailContract {

    interface IModel {

        void requestYd0009(String tradeNo, OnFeeDetailListener listener);
    }

    interface IView {
        void showLoading();

        void dismissLoading();

        void onOrderDetailsResult(OrderDetailsEntity entity);

        void onYd0009Result(FeeBillEntity entity);
    }

    interface IPresenter {

        void getOrderDetails(String hisOrderNo, String orgCode);

        void requestYd0009(String tradeNo);
    }
}
