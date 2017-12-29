package com.daydaynote.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daydaynote.R;

import java.util.List;

import com.daydaynote.entity.MonthBean;
import com.daydaynote.other.MonthDatesClickedCallBack;
import com.daydaynote.uitls.PublicUtil;

import static android.R.attr.width;

/**
 * Created by ${王sir} on 2017/6/30.
 * application
 */

public class BillConditionMonthAdapter extends RecyclerView.Adapter<BillConditionMonthAdapter.ViewHolder> implements View.OnClickListener {

//    private String[] months = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月",};

    private List<MonthBean> months;
    private MonthDatesClickedCallBack monthDatesCallBack;

    public BillConditionMonthAdapter(List<MonthBean> months) {
        this.months = months;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_content_month_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PublicUtil.month_position = position;
        int month = PublicUtil.getTheMonthOfTheYear();
        if (month == 8) {
            holder.tv8.setBackgroundResource(R.drawable.month_bg_shape);
        }
        MonthBean bean = months.get(position);
        holder.tv1.setText(bean.getJanuary());

        holder.tv2.setText(bean.getFebruary());
        holder.tv3.setText(bean.getMarch());
        holder.tv4.setText(bean.getApril());
        holder.tv5.setText(bean.getMay());
        holder.tv6.setText(bean.getJune());
        holder.tv7.setText(bean.getJuly());
        holder.tv8.setText(bean.getAugust());
        holder.tv9.setText(bean.getSeptember());
        holder.tv10.setText(bean.getOctober());
        holder.tv11.setText(bean.getNovember());
        holder.tv12.setText(bean.getDecember());
        holder.tv1.setOnClickListener(this);
        holder.tv2.setOnClickListener(this);
        holder.tv3.setOnClickListener(this);
        holder.tv4.setOnClickListener(this);
        holder.tv5.setOnClickListener(this);
        holder.tv6.setOnClickListener(this);
        holder.tv7.setOnClickListener(this);
        holder.tv8.setOnClickListener(this);
        holder.tv9.setOnClickListener(this);
        holder.tv10.setOnClickListener(this);
        holder.tv11.setOnClickListener(this);
        holder.tv12.setOnClickListener(this);

    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.January_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("1");
                }
                break;
            case R.id.February_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("2");
                }
                break;
            case R.id.March_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("3");
                }
                break;
            case R.id.April_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("4");
                }
                break;
            case R.id.May_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("5");
                }
                break;
            case R.id.June_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("6");
                }
                break;
            case R.id.July_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("7");
                }
                break;
            case R.id.August_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("8");
                }
                break;
            case R.id.September_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("9");
                }
                break;
            case R.id.October_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("10");
                }
                break;
            case R.id.November_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("11");
                }
                break;
            case R.id.December_tv:
                if (monthDatesCallBack != null) {
                    monthDatesCallBack.monthClicked("12");
                }
                break;
            default:
                break;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
        TextView tv7;
        TextView tv8;
        TextView tv9;
        TextView tv10;
        TextView tv11;
        TextView tv12;

        public ViewHolder(View itemView) {
            super(itemView);

            tv1 = (TextView) itemView.findViewById(R.id.January_tv);
            tv1.setHeight(width);
            tv2 = (TextView) itemView.findViewById(R.id.February_tv);
            tv3 = (TextView) itemView.findViewById(R.id.March_tv);
            tv4 = (TextView) itemView.findViewById(R.id.April_tv);
            tv5 = (TextView) itemView.findViewById(R.id.May_tv);
            tv6 = (TextView) itemView.findViewById(R.id.June_tv);
            tv7 = (TextView) itemView.findViewById(R.id.July_tv);
            tv8 = (TextView) itemView.findViewById(R.id.August_tv);
            tv9 = (TextView) itemView.findViewById(R.id.September_tv);
            tv10 = (TextView) itemView.findViewById(R.id.October_tv);
            tv11 = (TextView) itemView.findViewById(R.id.November_tv);
            tv12 = (TextView) itemView.findViewById(R.id.December_tv);
        }

    }

    public void setMonthClick(MonthDatesClickedCallBack monthDatesCallBack) {
        this.monthDatesCallBack = monthDatesCallBack;
    }
}
