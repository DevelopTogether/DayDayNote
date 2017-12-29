package com.daydaynote.other;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daydaynote.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class FuiltAdapter extends RecyclerView.Adapter<FuiltAdapter.ViewHolder> {
    private Context context;
    private List<Fuilt> mList;

    public FuiltAdapter(List<Fuilt> mListx) {
        mList = mListx;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            textView = (TextView) itemView.findViewById(R.id.fuilt_text);
            imageView = (ImageView) itemView.findViewById(R.id.fuilt_image);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.fuilt_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posion = viewHolder.getAdapterPosition();
                Fuilt fuilt = mList.get(posion);
                Intent intent = new Intent(context, Xiangqing.class);
                intent.putExtra("name", fuilt.getName());
                intent.putExtra("image", fuilt.getImageID());
                context.startActivity(intent);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fuilt fuilt = mList.get(position);
        holder.textView.setText(fuilt.getName());
        Glide.with(context).load(fuilt.getImageID()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}


