package com.daydaynote.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daydaynote.R;

import java.util.List;

import com.daydaynote.entity.YearBean;

/**
 * Created by ${王sir} on 2017/7/2.
 * application
 */


public class BillConditionYearAdapter extends RecyclerView.Adapter<BillConditionYearAdapter.ViewHolder> {
    private List<YearBean> yearBeans;
    private TextView tv;

    public BillConditionYearAdapter(List<YearBean> yearBeans,TextView tv) {
        this.yearBeans = yearBeans;
        this.tv = tv;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_content_year_layout, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        YearBean bean = yearBeans.get(position);
        holder.tv1.setText(bean.getYear1());
        holder.tv2.setText(bean.getYear2());
        holder.tv3.setText(bean.getYear3());
        holder.tv4.setText(bean.getYear4());
        holder.tv5.setText(bean.getYear5());
        holder.tv6.setText(bean.getYear6());
        holder.tv7.setText(bean.getYear7());
        holder.tv8.setText(bean.getYear8());
        holder.tv9.setText(bean.getYear9());
        holder.tv10.setText(bean.getYear10());
        tv.setText(bean.getYear1()+"年-"+bean.getYear10()+"年");
    }

    @Override
    public int getItemCount() {
        return yearBeans.size();
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

        public ViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.year_tv1);
            tv2 = (TextView) itemView.findViewById(R.id.year_tv2);
            tv3 = (TextView) itemView.findViewById(R.id.year_tv3);
            tv4 = (TextView) itemView.findViewById(R.id.year_tv4);
            tv5 = (TextView) itemView.findViewById(R.id.year_tv5);
            tv6 = (TextView) itemView.findViewById(R.id.year_tv6);
            tv7 = (TextView) itemView.findViewById(R.id.year_tv7);
            tv8 = (TextView) itemView.findViewById(R.id.year_tv8);
            tv9 = (TextView) itemView.findViewById(R.id.year_tv9);
            tv10 = (TextView) itemView.findViewById(R.id.year_tv10);

        }
    }
}
