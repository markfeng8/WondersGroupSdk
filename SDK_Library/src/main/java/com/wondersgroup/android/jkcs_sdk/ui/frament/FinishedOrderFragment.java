package com.wondersgroup.android.jkcs_sdk.ui.frament;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wondersgroup.android.jkcs_sdk.R;
import com.wondersgroup.android.jkcs_sdk.base.BaseFragment;

import widget.DateScrollerDialog;
import widget.data.Type;
import widget.listener.OnDateSetListener;

/**
 * Created by x-sir on 2018/8/9 :)
 * Function:已完成订单页面
 */
public class FinishedOrderFragment extends BaseFragment {

    private TextView tvStartDate;
    private TextView tvEndDate;
    private RecyclerView recyclerView;
    private long mLastTime = System.currentTimeMillis(); // 上次设置的时间
    private boolean isStartTime = true;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.wonders_group_fragment_finished_order, null);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        initListener();
    }

    private void initListener() {
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = true;
                showDate();
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = false;
                showDate();
            }
        });
    }

    /**
     * 显示日期(配置最大、最小时间伐值)
     */
    public void showDate() {
        DateScrollerDialog dialog = new DateScrollerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setTitleStringId(getString(R.string.wonders_select_date_please))
                //.setMinMilliseconds(dateScrollMin)
                //.setMaxMilliseconds(dateScrollMax)
                //.setCurMilliseconds(mLastTime)
                .setCallback(mOnDateSetListener)
                .build();

        if (dialog != null) {
            if (!dialog.isAdded()) {
                dialog.show(getFragmentManager(), "year_month_day");
            }
        }
    }

    // 数据的回调
    private OnDateSetListener mOnDateSetListener = new OnDateSetListener() {
        @Override
        public void onDateSet(DateScrollerDialog timePickerView, long milliseconds) {
            mLastTime = milliseconds;
            //String text = TimeUtil.getAuthTime(milliseconds);
            if (isStartTime) {

            } else {

            }
        }
    };

}
