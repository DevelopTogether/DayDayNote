package com.daydaynote.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daydaynote.R;
import com.daydaynote.entity.ConsumeBean;

/**
 * Created by ${王sir} on 2017/8/29.
 * application
 */

public class ConsumeTypeAdapter extends RecyclerView.Adapter<ConsumeTypeAdapter.ViewHolder> {

    private ConsumeTypeAdapterInterface consumeTypeClicked;
    String[] names = {"一般", "日用品", "住房", "用餐", "医疗", "交通", "娱乐", "人情", "学习", "旅游", "通讯"};
    Integer[] markers = {R.mipmap.type_yiban, R.mipmap.type_riyongpin, R.mipmap.type_fangzu, R.mipmap.type_yongcan, R.mipmap.type_yiliao,
            R.mipmap.type_chuxing, R.mipmap.type_yule, R.mipmap.type_renqing, R.mipmap.type_xuexi, R.mipmap.type_lvyou, R.mipmap.type_tongxun};
    Integer[] colors = {R.color.yiban, R.color.riyongpin, R.color.fangzu, R.color.yongcan, R.color.yiliao, R.color.chuxing,
            R.color.yule, R.color.renqing, R.color.xuexi, R.color.lvyou, R.color.tongxun};


    @Override
    public ConsumeTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type_layout,parent,false);
        ConsumeTypeAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ConsumeTypeAdapter.ViewHolder holder, int position) {
       final  ConsumeBean bean = new ConsumeBean(names[position],markers[position],colors[position]);
        holder.iv.setBackgroundResource(markers[position]);
        holder.tv.setText(names[position]);
        holder.consume_type_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (consumeTypeClicked!=null) {
                    consumeTypeClicked.consumeTypeClicked(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout consume_type_ll;
        ImageView iv;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.type_maker);
            tv = (TextView) itemView.findViewById(R.id.type_name);
            consume_type_ll = (LinearLayout) itemView.findViewById(R.id.consume_type_ll);
        }
    }

    public interface ConsumeTypeAdapterInterface{

        void consumeTypeClicked(ConsumeBean bean);
    }

    public void setConsumeTypeClickedCallBack(ConsumeTypeAdapterInterface consumeTypeClicked){
        this.consumeTypeClicked = consumeTypeClicked;

    }
}
