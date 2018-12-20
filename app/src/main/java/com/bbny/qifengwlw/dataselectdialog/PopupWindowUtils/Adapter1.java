package com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;
import com.bbny.qifengwlw.dataselectdialog.DataSelectAdapter;
import com.bbny.qifengwlw.dataselectdialog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWX on 2018/12/19.
 */
//第一选项
public class Adapter1  extends RecyclerView.Adapter<Adapter1.ItemView>{
    List<BaseItemBean> list = new ArrayList<>();
    CallBack callBack;
    Context context;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void setList(List<BaseItemBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Adapter1(Context context,List<BaseItemBean> beans) {
        this.list = beans;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_1, parent,false);
        return new ItemView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        final BaseItemBean bean = list.get(position);
        if (bean.isItemChecked()){
            ((TextView) holder.itemView.findViewById(R.id.tv_item)).setTextColor( ContextCompat.getColor(context,R.color.app_color_1));
            (holder.itemView.findViewById(R.id.tv_item)).setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.itemView.findViewById(R.id.line).setVisibility(View.GONE);
        }else{
            ((TextView) holder.itemView.findViewById(R.id.tv_item)).setTextColor(ContextCompat.getColor(context,R.color.black));
            (holder.itemView.findViewById(R.id.tv_item)).setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            holder.itemView.findViewById(R.id.line).setVisibility(View.VISIBLE);
        }
        ((TextView) holder.itemView.findViewById(R.id.tv_item)).setText(bean.getItemName());
        (holder.itemView.findViewById(R.id.tv_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.isItemChecked()) {
                    bean.setItemChecked(false);
                }else{
                    initItems(list);
                    bean.setItemChecked(true);
                }
                if (callBack != null) {
                    callBack.callBack(list);
                }
                notifyDataSetChanged();
            }
        });
    }

    private List<BaseItemBean> initItems(List<BaseItemBean> items) {
        for (BaseItemBean bean : items) {
            bean.setItemChecked(false);
        }
        return items;
    }
    public interface CallBack{
        void callBack(List<BaseItemBean> list);
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class ItemView extends RecyclerView.ViewHolder {
        public ItemView(View itemView) {
            super(itemView);
        }
    }
}
