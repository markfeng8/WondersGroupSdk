package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.DetailHeadBean;
import com.wondersgroup.android.jkcs_sdk.entity.DetailPayBean;
import com.wondersgroup.android.jkcs_sdk.entity.FeeBillEntity;

import java.util.List;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "DetailsAdapter";
    private static final int TYPE_HEADER = 1;  // 头部信息类型
    private static final int TYPE_LIST = 2;    // 未缴清账单类型
    private static final int TYPE_PAY = 3;    // 支付视图类型
    private Context mContext;
    private List<Object> mItemList;
    private LayoutInflater mLayoutInflater; // 初始化布局加载器
    private int mCurrentType = -1; // 当前Item的类型

    public DetailsAdapter(Context context, List<Object> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * 设置并刷新数据
     *
     * @param itemList
     */
    public void setmItemList(List<Object> itemList) {
        this.mItemList = itemList;
        notifyDataSetChanged(); // 刷新适配器
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEADER:
                viewHolder = new HeaderViewHolder(mLayoutInflater.inflate(
                        R.layout.wonders_group_item_detail_pay_header, parent, false));
                break;
            case TYPE_LIST:
                viewHolder = new ListViewHolder(mLayoutInflater.inflate(
                        R.layout.wonders_group_item_detail_pay_list, parent, false));
                break;
            case TYPE_PAY:
                viewHolder = new PayViewHolder(mLayoutInflater.inflate(
                        R.layout.wonders_group_item_detail_pay_type, parent, false));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.setData((DetailHeadBean) mItemList.get(position));
                break;
            case TYPE_LIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                listViewHolder.setData((FeeBillEntity.DetailsBean) mItemList.get(position));
                break;
            case TYPE_PAY:
                PayViewHolder payViewHolder = (PayViewHolder) holder;
                payViewHolder.setData((DetailPayBean) mItemList.get(position));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mItemList != null && position < mItemList.size()) {
            Object object = mItemList.get(position);
            if (object instanceof DetailHeadBean) {
                mCurrentType = TYPE_HEADER;
            } else if (object instanceof FeeBillEntity.DetailsBean) {
                mCurrentType = TYPE_LIST;
            } else if (object instanceof DetailPayBean) {
                mCurrentType = TYPE_PAY;
            }
        }
        return mCurrentType;
    }

    // 1.Header 类型
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvSocialNum;
        private TextView tvHospitalName;
        private TextView tvOrderNum;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSocialNum = (TextView) itemView.findViewById(R.id.tvSocialNum);
            tvHospitalName = (TextView) itemView.findViewById(R.id.tvHospitalName);
            tvOrderNum = (TextView) itemView.findViewById(R.id.tvOrderNum);
        }

        public void setData(DetailHeadBean headBean) {
            String name = headBean.getName();
            String socialNum = headBean.getSocialNum();
            String hospitalName = headBean.getHospitalName();
            String orderNum = headBean.getOrderNum();

            if (!TextUtils.isEmpty(name)) {
                tvName.setText(name);
            }
            if (!TextUtils.isEmpty(socialNum)) {
                tvSocialNum.setText(socialNum);
            }
            if (!TextUtils.isEmpty(hospitalName)) {
                tvHospitalName.setText(hospitalName);
            }
            if (!TextUtils.isEmpty(orderNum)) {
                tvOrderNum.setText(orderNum);
            }
        }
    }

    // 2.List 数据类型
    class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderName;
        private TextView tvMoney;
        private TextView tvOrderTime;
        private TextView tvDetail;

        public ListViewHolder(View itemView) {
            super(itemView);
            tvOrderName = (TextView) itemView.findViewById(R.id.tvOrderName);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            tvOrderTime = (TextView) itemView.findViewById(R.id.tvOrderTime);
            tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);
        }

        public void setData(FeeBillEntity.DetailsBean detailsBean) {
            if (detailsBean != null) {
                String orderName = detailsBean.getOrdername();
                String feeOrder = detailsBean.getFee_order();
                String orderTime = detailsBean.getHis_order_time();

                if (!TextUtils.isEmpty(orderName)) {
                    tvOrderName.setText(orderName);
                }
                if (!TextUtils.isEmpty(feeOrder)) {
                    tvMoney.setText(feeOrder);
                }
                if (!TextUtils.isEmpty(orderTime)) {
                    tvOrderTime.setText(orderTime);
                }
                tvDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "暂无详情", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    // 3.支付的数据类型
    class PayViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTotalMoney;
        private TextView tvPersonalPay;
        private TextView tvYiBaoPay;

        public PayViewHolder(View itemView) {
            super(itemView);
            tvTotalMoney = (TextView) itemView.findViewById(R.id.tvTotalMoney);
            tvPersonalPay = (TextView) itemView.findViewById(R.id.tvPersonalPay);
            tvYiBaoPay = (TextView) itemView.findViewById(R.id.tvYiBaoPay);
        }

        public void setData(DetailPayBean payBean) {
            if (payBean != null) {
                String totalPay = payBean.getTotalPay();
                String personalPay = payBean.getPersonalPay();
                String yibaoPay = payBean.getYibaoPay();

                if (!TextUtils.isEmpty(totalPay)) {
                    tvTotalMoney.setText(totalPay);
                }
                if (!TextUtils.isEmpty(personalPay)) {
                    tvPersonalPay.setText(personalPay);
                }
                if (!TextUtils.isEmpty(yibaoPay)) {
                    tvYiBaoPay.setText(yibaoPay);
                }
            }
        }
    }
}
