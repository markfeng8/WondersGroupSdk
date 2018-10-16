package com.wondersgroup.android.jkcs_sdk.widget.timepicker.listener;


import com.wondersgroup.android.jkcs_sdk.widget.timepicker.DateScrollerDialog;

/**
 * 日期设置的监听器
 *
 * @author C.L. Wang
 */
public interface OnDateSetListener {
    void onDateSet(DateScrollerDialog timePickerView, long milliseconds);
}
