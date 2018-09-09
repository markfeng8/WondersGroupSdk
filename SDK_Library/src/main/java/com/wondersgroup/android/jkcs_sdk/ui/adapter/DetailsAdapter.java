package com.wondersgroup.android.jkcs_sdk.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.entity.AfterHeaderBean;
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
                //headerViewHolder.setData((AfterHeaderBean) mItemList.get(position));
                break;
            case TYPE_LIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                //listViewHolder.setData((FeeBillEntity.DetailsBean) mItemList.get(position));
                break;
            case TYPE_PAY:
                PayViewHolder payViewHolder = (PayViewHolder) holder;
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
            if (object instanceof AfterHeaderBean) {
                mCurrentType = TYPE_HEADER;
            } else if (object instanceof FeeBillEntity.DetailsBean) {
                mCurrentType = TYPE_LIST;
            }
        }
        return mCurrentType;
    }

    // 1.Header 类型
    class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    // 2.List 数据类型
    class ListViewHolder extends RecyclerView.ViewHolder {

        public ListViewHolder(View itemView) {
            super(itemView);
        }
    }

    // 3.支付的数据类型
    class PayViewHolder extends RecyclerView.ViewHolder {

        public PayViewHolder(View itemView) {
            super(itemView);
        }
    }
}
