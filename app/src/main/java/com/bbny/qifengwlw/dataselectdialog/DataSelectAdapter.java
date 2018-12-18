package com.bbny.qifengwlw.dataselectdialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;

import java.util.ArrayList;
import java.util.List;

import static com.bbny.qifengwlw.dataselectdialog.DataSelectDialog.Tag;

/**
 * Created by ZWX on 2018/12/18.
 */

public class DataSelectAdapter extends RecyclerView.Adapter<DataSelectAdapter.ItemView> {
    List<BaseItemBean> selectItems = new ArrayList<>();
    List<BaseItemBean> itemBeans = new ArrayList<>();//完整的list
    Context mContext;

    public DataSelectAdapter(Context context,List<BaseItemBean> itemBeans) {
        this.mContext = context;
        this.itemBeans = itemBeans;
        if (itemBeans == null || itemBeans.size() == 0) {
            Log.d(Tag, "设置的数据不能为空");
            return;
        }
        selectItems = getCheckBeans(itemBeans);
    }

    public void setItemBeans(List<BaseItemBean> itemBeans) {
        if (itemBeans == null || itemBeans.size() == 0) {
            Log.d(Tag, "设置的数据不能为空");
            return;
        }
        this.itemBeans = itemBeans;
        selectItems = getCheckBeans(itemBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent,false);
        return new ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemView holder, final int position) {
        BaseItemBean bean = selectItems.get(position);
        ((TextView) holder.itemView.findViewById(R.id.tv_item)).setText(bean.getItemName());
        ((RecyclerView) holder.itemView.findViewById(R.id.rv_item)).setLayoutManager(new LinearLayoutManager(mContext));
        if (position == 0) {
            ((RecyclerView) holder.itemView.findViewById(R.id.rv_item)).setAdapter(new ItemAdapter(mContext, itemBeans, new ItemAdapter.CallBack() {
                @Override
                public void callBack(List<BaseItemBean> items) {
                    itemBeans = items;
                    setItemBeans(itemBeans);
                }
            }));
        }else if (position>0){
            ((RecyclerView) holder.itemView.findViewById(R.id.rv_item))
                    .setAdapter(new ItemAdapter(mContext, selectItems.get(position - 1).getItems(), new ItemAdapter.CallBack() {
                        @Override
                        public void callBack(List<BaseItemBean> items) {
                            selectItems.get(position).setItems(items);
                            selectItems.get(position).setItemName(getCheckedBean(items).getItemName());
                            selectItems.get(position).setItemID(getCheckedBean(items).getItemID());
//                            notifyItemChanged(position);
                            notifyDataSetChanged();
                        }
                    }));
        }
    }

    private List<BaseItemBean> getCheckBeans(List<BaseItemBean> beans) {
        List<BaseItemBean> lists = new ArrayList<>();
        if (beans == null || beans.size() == 0) {
            Log.d(Tag, "设置的数据不能为空");
            return lists;
        }
        BaseItemBean bean = null;
        do {
            bean = null;
            bean = getCheckedBean(beans);
            beans = bean.getItems();
            if (bean != null) {
                lists.add(bean);
            }
        } while (bean != null && bean.getItems() != null && bean.getItems().size() > 0);
        return lists;
    }


    //获取选择的item bean
    private BaseItemBean getCheckedBean(List<BaseItemBean> beans) {
        for (BaseItemBean b : beans) {
            if (b.isItemChecked()) {
                return b;
            }
        }
        BaseItemBean bean = null;
        //没有设置默认选项则默认选中第一个
        if (beans.size() > 0) {
            bean = beans.get(0).setItemChecked(true);
        }
        return bean;
    }

    @Override
    public int getItemCount() {
        return selectItems ==null?0: selectItems.size();
    }

    public static class ItemView extends RecyclerView.ViewHolder {
        public ItemView(View itemView) {
            super(itemView);
        }
    }

}
