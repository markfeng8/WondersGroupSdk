/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.wondersgroup.android.jkcs_sdk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.view.RecordDetailActivity;

import java.util.List;

/**
 * Created by x-sir on 2018/9/19 :)
 * Function:门诊缴费记录页面的 Adapter
 */
public class FeeRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<FeeRecordEntity.DetailsBean> mDetails;

    public FeeRecordAdapter(Context context, List<FeeRecordEntity.DetailsBean> details) {
        this.mContext = context;
        this.mDetails = details;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDetails(List<FeeRecordEntity.DetailsBean> details) {
        this.mDetails = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(
                R.layout.wonders_group_fee_record_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(mDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return mDetails == null ? 0 : mDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout llHospitalItem;
        private TextView tvHospitalName;
        private TextView tvFeeDate;
        private TextView tvTradeNo;
        private TextView tvFeeTotal;
        private String payPlatTradeNo;
        private String orgName;
        private String orgCode;
        private String shopOrderTime;
        private String feeCashTotal;
        private String feeYbTotal;
        private String feeTotal;

        MyViewHolder(View itemView) {
            super(itemView);
            tvHospitalName = itemView.findViewById(R.id.tvHospitalName);
            tvFeeDate = itemView.findViewById(R.id.tvFeeDate);
            tvTradeNo = itemView.findViewById(R.id.tvTradeNo);
            tvFeeTotal = itemView.findViewById(R.id.tvFeeTotal);
            llHospitalItem = itemView.findViewById(R.id.llHospitalItem);
            initListener();
        }

        private void initListener() {
            llHospitalItem.setOnClickListener(v -> {
                RecordDetailActivity.actionStart(mContext, orgCode, orgName, shopOrderTime,
                        payPlatTradeNo, feeTotal, feeCashTotal, feeYbTotal);
            });
        }

        @SuppressLint("SetTextI18n")
        void setData(FeeRecordEntity.DetailsBean detailsBean) {
            if (detailsBean != null) {
                orgName = detailsBean.getOrg_name();
                orgCode = detailsBean.getOrg_code();
                feeTotal = detailsBean.getFee_total();
                feeCashTotal = detailsBean.getFee_cash_total();
                feeYbTotal = detailsBean.getFee_yb_total();
                payPlatTradeNo = detailsBean.getPayplat_tradno();
                shopOrderTime = detailsBean.getShop_order_time();
                tvHospitalName.setText(orgName);
                tvFeeDate.setText("订单日期：" + shopOrderTime.substring(0, 10));
                tvTradeNo.setText("订单号：" + payPlatTradeNo);
                tvFeeTotal.setVisibility(View.VISIBLE);
                tvFeeTotal.setText("总金额：" + feeTotal + "元");
            }
        }
    }

}
