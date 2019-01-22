/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.widget.selecthospital;

import com.wondersgroup.android.jkcs_sdk.entity.CityBean;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalBean;

/**
 * Created by x-sir on 2019/1/21 :)
 * Function:城市选择器样式配置
 */
public abstract class OnCityItemClickListener {

    /**
     * 当选择城市、医院选择器时，需要重写此方法
     *
     * @param cityBean
     * @param hospitalBean
     */
    public void onSelected(CityBean cityBean, HospitalBean hospitalBean) {

    }

    /**
     * 取消
     */
    public void onCancel() {

    }
}
