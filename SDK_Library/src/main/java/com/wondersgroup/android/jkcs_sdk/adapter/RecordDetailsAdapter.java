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
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.CombineDetailsBean;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;
import com.wondersgroup.android.jkcs_sdk.entity.OrderDetailsEntity;
import com.wondersgroup.android.jkcs_sdk.ui.recorddetail.view.RecordDetailActivity;
import com.wondersgroup.android.jkcs_sdk.widget.FeeDetailLayout;

import java.util.List;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:订单记录详情的 Adapter
 */
public class RecordDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "PaymentDetailsAdapter";
    private Context mContext;
    private List<CombineDetailsBean> mItemList;
    private LayoutInflater mLayoutInflater; // 初始化布局加载器

    public RecordDetailsAdapter(Context context, List<CombineDetailsBean> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 刷新适配器
     */
    public void refreshAdapter() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(
                R.layout.wonders_group_item_detail_pay_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(mItemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderName;
        private TextView tvMoney;
        private TextView tvOrderTime;
        private ImageView ivArrow;
        private LinearLayout llItem;
        private LinearLayout llDetails;
        private String hisOrderNo;
        private int position;

        MyViewHolder(View itemView) {
            super(itemView);
            tvOrderName = itemView.findViewById(R.id.tvOrderName);
            tvMoney = itemView.findViewById(R.id.tvMoney);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            ivArrow = itemView.findViewById(R.id.ivArrow);
            llItem = itemView.findViewById(R.id.llItem);
            llDetails = itemView.findViewById(R.id.llDetails);
            initListener();
        }

        private void initListener() {
            llItem.setOnClickListener(v -> {
                boolean visible = llDetails.getVisibility() == View.GONE;
                llDetails.setVisibility((visible) ? View.VISIBLE : View.GONE);
                ivArrow.setImageResource(visible ? R.drawable.wonders_group_up_arrow : R.drawable.wonders_group_down_arrow);
                int childCount = llDetails.getChildCount();
                if (visible && childCount == 0) {
                    if (mContext instanceof RecordDetailActivity) {
                        ((RecordDetailActivity) mContext).getOrderDetails(hisOrderNo, position);
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        void setData(CombineDetailsBean combineDetails, int position) {
            this.position = position;
            if (combineDetails != null) {
                FeeBillEntity.DetailsBean defaultDetails = combineDetails.getDefaultDetails();
                List<OrderDetailsEntity.DetailsBean> openDetails = combineDetails.getOpenDetails();
                if (defaultDetails != null) {
                    String orderName = defaultDetails.getOrdername();
                    String order_name = defaultDetails.getOrder_name();
                    String feeOrder = defaultDetails.getFee_order();
                    String orderTime = defaultDetails.getHis_order_time();
                    hisOrderNo = defaultDetails.getHis_order_no();

                    // 后台返回的数据很垃圾，哪个 orderName 不为空就显示哪个！
                    if (!TextUtils.isEmpty(orderName)) {
                        tvOrderName.setText(orderName);
                    }
                    if (!TextUtils.isEmpty(order_name)) {
                        tvOrderName.setText(order_name);
                    }
                    if (!TextUtils.isEmpty(feeOrder)) {
                        tvMoney.setText(feeOrder);
                    }
                    if (!TextUtils.isEmpty(orderTime)) {
                        tvOrderTime.setText("账单时间：" + orderTime);
                    }
                }

                if (openDetails != null) {
                    if (openDetails.size() > 0) {
                        // 先清除就布局中的 Item
                        int count = llDetails.getChildCount();
                        if (count > 1) {
                            llDetails.removeViews(1, count - 1);
                        }
                        for (int i = 0; i < openDetails.size(); i++) {
                            StringBuilder stringBuilder = new StringBuilder();
                            OrderDetailsEntity.DetailsBean detailsBean = openDetails.get(i);
                            String itemName = detailsBean.getItemname();
                            String price = detailsBean.getPrice();
                            String amount = detailsBean.getAmount();
                            String unit = detailsBean.getUnit();

                            stringBuilder
                                    .append(price)
                                    .append("*")
                                    .append(amount)
                                    .append(unit);

                            FeeDetailLayout layout = new FeeDetailLayout(mContext);
                            layout.setFeeName(itemName);
                            layout.setFeeNum(stringBuilder.toString());
                            llDetails.addView(layout);
                        }
                    }
                }
            }
        }
    }

}
