/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.recorddetail.contract;

import com.wondersgroup.android.sdk.entity.FeeBillEntity;
import com.wondersgroup.android.sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:
 */
public interface RecordDetailContract {

    interface IModel {

        void requestYd0009(String tradeNo, HttpRequestCallback<FeeBillEntity> callback);
    }

    interface IView {
        void showLoading(boolean show);

        void onOrderDetailsResult(OrderDetailsEntity entity);

        void onYd0009Result(FeeBillEntity entity);
    }

    interface IPresenter {

        void getOrderDetails(String hisOrderNo, String orgCode);

        void requestYd0009(String tradeNo);
    }
}
