/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by x-sir on 2019/1/21 :)
 * Function:
 */
public class HospitalBean implements Parcelable {

    /**
     * org_code : 47117170333050211A1001
     * org_name : 湖州市中心医院
     */

    private String org_code;
    private String org_name;

    public HospitalBean() {
    }

    protected HospitalBean(Parcel in) {
        this.org_code = in.readString();
        this.org_name = in.readString();
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.org_code);
        dest.writeString(this.org_name);
    }

    /**
     * 必须要重写 toString 方法，此处的返回值就是 item 中显示的名称
     */
    @Override
    public String toString() {
        return org_name;
    }

    public static final Creator<HospitalBean> CREATOR = new Creator<HospitalBean>() {
        @Override
        public HospitalBean createFromParcel(Parcel source) {
            return new HospitalBean(source);
        }

        @Override
        public HospitalBean[] newArray(int size) {
            return new HospitalBean[size];
        }
    };
}
