/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk;

import com.wondersgroup.android.sdk.utils.LogUtil;
import com.wondersgroup.android.sdk.utils.WToastUtil;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by x-sir on 2018/12/10 :)
 * Function:
 */
public class CommonPayUnitTest {

    private static final String TAG = "CommonPayUnitTest";

    @Test
    public void formatAmount() {
        String amount = "7.70";
        long formatCents = 0L;

        try {
            BigDecimal original = new BigDecimal(amount);
            BigDecimal hundred = new BigDecimal("100");
            formatCents = original.multiply(hundred).longValueExact();
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        if (!isNumeric(String.valueOf(formatCents))) {
            WToastUtil.show("请输入正确的交易金额（单位：分）");
            return;
        }

        LogUtil.i(TAG, "formatCents===" + formatCents);
    }

    private boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]+(.[0-9]{1,2})?$");
    }
}
