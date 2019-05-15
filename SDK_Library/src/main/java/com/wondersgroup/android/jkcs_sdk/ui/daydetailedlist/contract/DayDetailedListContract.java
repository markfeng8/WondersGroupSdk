/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.contract;

import com.wondersgroup.android.jkcs_sdk.entity.Cy0005Entity;
import com.wondersgroup.android.jkcs_sdk.net.callback.HttpRequestCallback;

/**
 * Created by x-sir on 2018/11/1 :)
 * Function:
 */
public interface DayDetailedListContract {
    interface IModel {
        void requestCy0005(String orgCode, String jzlsh, String startDate, HttpRequestCallback<Cy0005Entity> callback);
    }

    interface IView {
        void showLoading(boolean show);

        void onCy0005Result(Cy0005Entity entity);
    }

    interface IPresenter {
        void requestCy0005(String orgCode, String jzlsh, String startDate);
    }
}
