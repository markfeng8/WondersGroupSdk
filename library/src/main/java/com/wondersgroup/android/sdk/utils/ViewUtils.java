/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.utils;

import android.view.View;

/**
 * Created by x-sir on 2019/10/14.
 * Function:
 */
public class ViewUtils {

    public static void goneView(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void visibleView(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void inVisibleView(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

}
