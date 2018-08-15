package com.bawei.recylerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.recylerview.R;
import com.bawei.recylerview.bean.BookBean;

import java.util.ArrayList;

/**
 * Created by _ヽ陌路离殇ゞ on 2018/8/15.
 */

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.SubViewHolder>{

    Context context;
    ArrayList<BookBean.DataBean> data;
    onItemclickLinsten onItemclickLinsten;

    public interface onItemclickLinsten{
        void onclick(int layoutPosition);
    }
    public void setonItemclickLinsten(onItemclickLinsten onItemclickLinsten){
        this.onItemclickLinsten=onItemclickLinsten;
    }
    public SubAdapter(Context context, ArrayList<BookBean.DataBean> data) {
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);

        SubViewHolder subViewHolder = new SubViewHolder(inflate);

        return subViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
            holder.tv_item.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class SubViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tv_item;

        public SubViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int layoutPosition = getLayoutPosition();
            onItemclickLinsten.onclick(layoutPosition);


        }
    }
}
