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

/**
 * Created by x-sir on 2018/11/19 :)
 * Function:
 */
public interface RecordDetailContract {
    interface IModel {
    }

    interface IView {
        void showLoading();

        void dismissLoading();

        void onYd0003Result(FeeBillEntity entity);

        void onOrderDetailsResult(OrderDetailsEntity entity);
    }

    interface IPresenter {

        void requestYd0003(String orgCode);

        void getOrderDetails(String hisOrderNo, String orgCode);
    }
}
