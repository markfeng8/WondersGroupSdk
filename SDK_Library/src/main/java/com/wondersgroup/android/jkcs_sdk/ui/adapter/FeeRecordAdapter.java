package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.MvpBaseFragment;
import com.wondersgroup.android.jkcs_sdk.entity.CombineFeeRecord;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.FeeRecordEntity;
import com.wondersgroup.android.jkcs_sdk.widget.FeeRecordLayout;

import java.util.List;

/**
 * Created by x-sir on 2018/9/19 :)
 * Function:
 */
public class FeeRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CombineFeeRecord> mDetails;
    private Context mContext;
    private MvpBaseFragment mBaseFragment;
    private LayoutInflater mLayoutInflater;

    public FeeRecordAdapter(Context context, MvpBaseFragment baseFragment, List<CombineFeeRecord> details) {
        this.mContext = context;
        this.mBaseFragment = baseFragment;
        this.mDetails = details;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setDetails(List<CombineFeeRecord> mDetails) {
        this.mDetails = mDetails;
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
        myViewHolder.setData(mDetails.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDetails == null ? 0 : mDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llHospitalItem;
        private LinearLayout llHideLayout;
        private TextView tvHospitalName;
        private TextView tvFeeNum;
        private ImageView ivArrow;
        private String payPlatTradeNo;
        private int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvHospitalName = (TextView) itemView.findViewById(R.id.tvHospitalName);
            tvFeeNum = (TextView) itemView.findViewById(R.id.tvFeeNum);
            llHideLayout = (LinearLayout) itemView.findViewById(R.id.llHideLayout);
            llHospitalItem = (LinearLayout) itemView.findViewById(R.id.llHospitalItem);
            ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
            initListener();
        }

        private void initListener() {
            llHospitalItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int visibility = llHideLayout.getVisibility();
                    boolean visible = visibility == View.VISIBLE;
                    llHideLayout.setVisibility(visible ? View.GONE : View.VISIBLE);
                    ivArrow.setImageResource(visible ? R.drawable.wonders_group_down_arrow : R.drawable.wonders_group_up_arrow);
                    if (!visible) {
                        mBaseFragment.getFeeDetails(payPlatTradeNo, position);
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setData(CombineFeeRecord item, int position) {
            this.position = position;
            if (item != null) {
                FeeRecordEntity.DetailsBean detailsBean = item.getRecordDetail();
                List<FeeBillEntity.DetailsBean> feeDetail = item.getFeeDetail();
                if (detailsBean != null) {
                    String orgName = detailsBean.getOrg_name();
                    String feeTotal = detailsBean.getFee_total();
                    payPlatTradeNo = detailsBean.getPayplat_tradno();
                    tvHospitalName.setText(orgName);
                    tvFeeNum.setText("未支付：" + feeTotal + "元");
                }

                if (feeDetail != null) {
                    if (llHideLayout.getChildCount() > 0) {
                        llHideLayout.removeAllViews();
                    }
                    for (int i = 0; i < feeDetail.size(); i++) {
                        FeeBillEntity.DetailsBean bean = feeDetail.get(i);
                        FeeRecordLayout layout = new FeeRecordLayout(mContext);
                        layout.setFeeName(bean.getOrdername());
                        layout.setFeeNum(bean.getFee_order());
                        layout.setTimestamp(bean.getHis_order_time());
                        llHideLayout.addView(layout);

                        // TODO: 2018/9/19 如果点击的是屏幕最后一个可见的位置就向上滑动
                    }
                }
            }
        }
    }

}
