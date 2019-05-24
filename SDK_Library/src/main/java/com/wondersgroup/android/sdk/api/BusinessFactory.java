/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.api;

/**
 * Created by x-sir on 2019/1/22 :)
 * Function:业务静态工厂类
 */
public class BusinessFactory {

    public static AbstractBusiness getBusiness(int flag) {
        if (flag == 0) {
            return new AfterPayBusiness();
        } else if (flag == 1) {
            return new SelfPayBusiness();
        } else if (flag == 2) {
            return new InHospitalBusiness();
        } else {
            throw new IllegalArgumentException("请传入正确的业务类型flag的值!");
        }
    }
}
