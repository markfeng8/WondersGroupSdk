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

import java.util.List;

/**
 * Created by x-sir on 2019/1/21 :)
 * Function:
 */
public class CityBean implements Parcelable {

    private String area;
    private String area_name;
    private List<HospitalBean> details;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public List<HospitalBean> getDetails() {
        return details;
    }

    public void setDetails(List<HospitalBean> details) {
        this.details = details;
    }

    /**
     * 必须要重写 toString 方法，此处的返回值就是 item 中显示的名称
     */
    @Override
    public String toString() {
        return area_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.area);
        dest.writeString(this.area_name);
        dest.writeTypedList(this.details);
    }

    public CityBean() {
    }

    protected CityBean(Parcel in) {
        this.area = in.readString();
        this.area_name = in.readString();
        this.details = in.createTypedArrayList(HospitalBean.CREATOR);
    }

    public static final Creator<CityBean> CREATOR = new Creator<CityBean>() {
        @Override
        public CityBean createFromParcel(Parcel source) {
            return new CityBean(source);
        }

        @Override
        public CityBean[] newArray(int size) {
            return new CityBean[size];
        }
    };
}
