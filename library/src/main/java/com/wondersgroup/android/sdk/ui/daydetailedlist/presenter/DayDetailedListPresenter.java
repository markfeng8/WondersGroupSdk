/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.ui.daydetailedlist.presenter;

import com.wondersgroup.android.sdk.base.MvpBasePresenter;
import com.wondersgroup.android.sdk.entity.Cy0005Entity;
import com.wondersgroup.android.sdk.net.callback.HttpRequestCallback;
import com.wondersgroup.android.sdk.ui.daydetailedlist.contract.DayDetailedListContract;
import com.wondersgroup.android.sdk.ui.daydetailedlist.model.DayDetailedListModel;
import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

/**
 * Created by x-sir on 2018/11/1 :)
 * Function:
 */
public class DayDetailedListPresenter<T extends DayDetailedListContract.IView>
        extends MvpBasePresenter<T> implements DayDetailedListContract.IPresenter {

    private static final String TAG = "DayDetailedListPresenter";
    private DayDetailedListContract.IModel mModel = new DayDetailedListModel();

    @Override
    public void requestCy0005(String orgCode, String jzlsh, String startDate) {
        showLoading(true);
        mModel.requestCy0005(orgCode, jzlsh, startDate, new HttpRequestCallback<Cy0005Entity>() {
            @Override
            public void onSuccess(Cy0005Entity entity) {
                LogUtil.i(TAG, "requestCy0005 success~");
                showLoading(false);
                if (isNonNull()) {
                    mViewRef.get().onCy0005Result(entity);
                }
            }

            @Override
            public void onFailed(String errCodeDes) {
                LogUtil.e(TAG, "requestCy0005 failed!" + errCodeDes);
                showLoading(false);
                WToastUtil.show(errCodeDes);
                if (isNonNull()) {
                    mViewRef.get().onCy0005Result(null);
                }
            }
        });
    }

    private void showLoading(boolean show) {
        if (isNonNull()) {
            mViewRef.get().showLoading(show);
        }
    }
}
