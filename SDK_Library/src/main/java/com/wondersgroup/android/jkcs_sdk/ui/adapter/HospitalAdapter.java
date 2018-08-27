package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.HospitalEntity;

import java.util.List;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:
 */
public class HospitalAdapter extends BaseAdapter {

    private Context mContext;
    private List<HospitalEntity.DetailsBean> mBeanList;

    public HospitalAdapter(Context context, List<HospitalEntity.DetailsBean> beanList) {
        this.mContext = context;
        this.mBeanList = beanList;
    }

    @Override
    public int getCount() {
        return mBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.wonders_group_item_hospital, null);
            holder = new ViewHolder();
            holder.tvHospitalName = convertView.findViewById(R.id.tvHospitalName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HospitalEntity.DetailsBean bean = mBeanList.get(position);
        holder.tvHospitalName.setText(bean.getOrg_name());
        return convertView;
    }

    static class ViewHolder {
        TextView tvHospitalName;
    }
}
