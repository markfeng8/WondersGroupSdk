/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.presenter;

import com.wondersgroup.android.jkcs_sdk.base.MvpBasePresenter;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.contract.DayDetailedListContract;
import com.wondersgroup.android.jkcs_sdk.ui.daydetailedlist.model.DayDetailedListModel;

/**
 * Created by x-sir on 2018/11/1 :)
 * Function:
 */
public class DayDetailedListPresenter<T extends DayDetailedListContract.IView>
        extends MvpBasePresenter<T> implements DayDetailedListContract.IPresenter {

    private static final String TAG = "DayDetailedListPresenter";
    private DayDetailedListContract.IModel mModel = new DayDetailedListModel();

}
