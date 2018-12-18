package com.bbny.qifengwlw.dataselectdialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWX on 2018/12/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemView>{
    List<BaseItemBean> items = new ArrayList<>();
    Context mContext;
    CallBack callBack;


    public ItemAdapter( Context mContext,List<BaseItemBean> items,CallBack callBack) {
        this.items = items;
        this.mContext = mContext;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_item,parent,false);
        return new ItemView(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ItemView holder, final int position) {
        BaseItemBean bean = items.get(position);
        if (bean.isItemChecked()) {
            holder.itemView.findViewById(R.id.tv_item).setBackgroundColor(R.color.gray_5);
        }else{
            holder.itemView.findViewById(R.id.tv_item).setBackgroundColor(R.color.white);
        }
        ((TextView) holder.itemView.findViewById(R.id.tv_item)).setText(bean.getItemName());
        holder.itemView.findViewById(R.id.tv_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items = initItems(items);
                items.get(position).setItemChecked(true);
                if (callBack != null) {
                    callBack.callBack(items);
                }
            }
        });
    }

    private List<BaseItemBean> initItems(List<BaseItemBean> items) {
        for (BaseItemBean bean : items) {
            bean.setItemChecked(false);
        }
        return items;
    }


    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }

    public interface CallBack{
        void callBack(List<BaseItemBean> items);
    }

    public static class ItemView extends RecyclerView.ViewHolder {
        public ItemView(View itemView) {
            super(itemView);
        }
    }
}
