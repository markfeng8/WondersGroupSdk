/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.sdk.widget.selecthospital;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wondersgroup.android.sdk.entity.CityBean;
import com.wondersgroup.android.sdk.entity.HospitalBean;
import com.wondersgroup.android.sdk.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by x-sir on 2019/01/21 :)
 * Function:解析辅助类
 */
public class ParseHelper {

    private static final String TAG = "ParseHelper";
    /**
     * 城市列表数据
     */
    private ArrayList<CityBean> mCityBeanArrayList = new ArrayList<>();
    /**
     * 医院列表数据
     */
    private List<List<HospitalBean>> mHospitalBeanArrayList;

    private CityBean[] mCityBeanArray;
    private ArrayList<CityBean> cityBeans;
    private CityBean mCityBean;
    private HospitalBean mHospitalBean;

    /**
     * key - 城市地区 values - 医院列表
     */
    private Map<String, HospitalBean[]> mCityHosMap = new HashMap<>();

    /**
     * key - 城市地区医院 values - 医院
     */
    private Map<String, HospitalBean> mHospitalMap = new HashMap<>();

    public ParseHelper() {

    }

    public Map<String, HospitalBean> getmHospitalMap() {
        return mHospitalMap;
    }

    public void setmHospitalMap(Map<String, HospitalBean> mHospitalMap) {
        this.mHospitalMap = mHospitalMap;
    }

    public ArrayList<CityBean> getmCityBeanArrayList() {
        return mCityBeanArrayList;
    }

    public void setmCityBeanArrayList(ArrayList<CityBean> mCityBeanArrayList) {
        this.mCityBeanArrayList = mCityBeanArrayList;
    }

    public List<List<HospitalBean>> getmHospitalBeanArrayList() {
        return mHospitalBeanArrayList;
    }

    public void setmHospitalBeanArrayList(List<List<HospitalBean>> mHospitalBeanArrayList) {
        this.mHospitalBeanArrayList = mHospitalBeanArrayList;
    }

    public CityBean[] getmCityBeanArray() {
        return mCityBeanArray;
    }

    public void setmCityBeanArray(CityBean[] mCityBeanArray) {
        this.mCityBeanArray = mCityBeanArray;
    }

    public ArrayList<CityBean> getCityBeans() {
        return cityBeans;
    }

    public void setCityBeans(ArrayList<CityBean> cityBeans) {
        this.cityBeans = cityBeans;
    }

    public CityBean getmCityBean() {
        return mCityBean;
    }

    public void setmCityBean(CityBean mCityBean) {
        this.mCityBean = mCityBean;
    }

    public HospitalBean getmHospitalBean() {
        return mHospitalBean;
    }

    public void setmHospitalBean(HospitalBean mHospitalBean) {
        this.mHospitalBean = mHospitalBean;
    }

    public Map<String, HospitalBean[]> getmCityHosMap() {
        return mCityHosMap;
    }

    public void setmCityHosMap(Map<String, HospitalBean[]> mCityHosMap) {
        this.mCityHosMap = mCityHosMap;
    }

    /**
     * 初始化数据，解析 json 数据
     */
    public void initData(String json) {
        Type type = new TypeToken<ArrayList<CityBean>>() {
        }.getType();

        // 2.解析出城市地区数据
        mCityBeanArrayList = new Gson().fromJson(json, type);
        // 3.判空
        if (mCityBeanArrayList == null || mCityBeanArrayList.isEmpty()) {
            return;
        }

        int citySize = mCityBeanArrayList.size();
        LogUtil.i(TAG, "citySize===" + citySize);
        // 4.创建一个城市数量大小的集合，你可以理解为有多少个城市的组合，每个组合下又有医院
        mHospitalBeanArrayList = new ArrayList<>(citySize);

        // 6.创建一个城市地区数量长度的空数组
        mCityBeanArray = new CityBean[citySize];

        // 5.初始化默认选中的市、医院，默认选中第一个市区中的第一个医院
        if (!mCityBeanArrayList.isEmpty()) {
            // 取出第一个地区
            mCityBean = mCityBeanArrayList.get(0);
            // 取出第一个地区的医院列表
            List<HospitalBean> hospitalList = mCityBean.getDetails();
            if (hospitalList != null && !hospitalList.isEmpty()) {
                // 拿出医院列中的一个医院数据
                mHospitalBean = hospitalList.get(0);
            }
        }

        // 7.遍历每个城市地区
        for (int i = 0; i < citySize; i++) {
            CityBean city = mCityBeanArrayList.get(i);
            // 取出城市对应下面的医院的集合
            List<HospitalBean> hospitalList = city.getDetails();
            // 创建一个医院的集合长度的医院对象的空数组
            HospitalBean[] hospitalArray = new HospitalBean[hospitalList.size()];

            // 8.遍历当前城市下面的所有医院
            for (int j = 0; j < hospitalList.size(); j++) {
                HospitalBean hospitalBean = hospitalList.get(j);
                // 存放数据（key 为城市名 + 医院的组织名，value 为医院的 Bean 对象）
                mHospitalMap.put(city.getArea_name() + hospitalBean.getOrg_name(), hospitalBean);
                // 给空数组赋值
                hospitalArray[j] = hospitalBean;
            }

            // 9.地区-医院的数据，保存到 mCityHosMap（key 为地区名，value 为对应的医院的数组）
            mCityHosMap.put(city.getArea_name(), hospitalArray);

            // 10.然后把一个地区下的医院列表的数据放入到之前创建的集合中
            mHospitalBeanArrayList.add(hospitalList);

            // 11.赋值所有地区的 city
            mCityBeanArray[i] = city;
        }
    }

}
