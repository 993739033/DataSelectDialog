package com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;
import com.bbny.qifengwlw.dataselectdialog.R;

import java.util.ArrayList;
import java.util.List;

import static com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils.PopupUtils.getSelectBeans;

/**
 * Created by ZWX on 2018/12/19.
 */
//第一选项
public class Adapter2 extends RecyclerView.Adapter<Adapter2.ItemView> {
    BaseItemBean bean;
    Boolean shouldInit;
    CallBack callBack;
    Context context;

    public void setBean(BaseItemBean bean) {
        this.bean = bean;
        notifyDataSetChanged();
    }

    public BaseItemBean getBean() {
        return bean;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


    public Adapter2(Context context, BaseItemBean bean) {
        this.bean = bean;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_2, parent, false);
        return new ItemView(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        final BaseItemBean bean1 = bean.getItems().get(position);
        if (bean1.isItemChecked()) {
            ((TextView) holder.itemView.findViewById(R.id.tv_item)).setTextColor(ContextCompat.getColor(context, R.color.app_color_1));
            (holder.itemView.findViewById(R.id.iv_tag)).setVisibility(View.VISIBLE);
            startAnim((ImageView) holder.itemView.findViewById(R.id.iv_tag));
        } else {
            ((TextView) holder.itemView.findViewById(R.id.tv_item)).setTextColor(ContextCompat.getColor(context, R.color.black));
            (holder.itemView.findViewById(R.id.iv_tag)).setVisibility(View.INVISIBLE);
        }
        ((TextView) holder.itemView.findViewById(R.id.tv_item)).setText(bean1.getItemName());
        (holder.itemView.findViewById(R.id.tv_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean1.isItemChecked()) {
                    bean1.setItemChecked(false);
                }else{
                    initItems(bean.getItems());
                    bean1.setItemChecked(true);
                }
                if (callBack != null) {
                    callBack.callBack(getBeans());
                }
                notifyDataSetChanged();
            }
        });
    }

    public void startAnim(ImageView view) {
        Drawable drawable = view.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }


    public List<BaseItemBean> getBeans() {
        List<BaseItemBean> list = new ArrayList<>();
        list.add(bean);
        list = getSelectBeans(list,false);
        return list;
    }


    private List<BaseItemBean> initItems(List<BaseItemBean> items) {
        for (BaseItemBean bean : items) {
            bean.setItemChecked(false);
        }
        return items;
    }

    @Override
    public int getItemCount() {
        return bean != null && bean.getItems() != null ? bean.getItems().size() : 0;
    }

    public interface CallBack {
        void callBack(List<BaseItemBean> beans);
    }


    public static class ItemView extends RecyclerView.ViewHolder {
        public ItemView(View itemView) {
            super(itemView);
        }
    }
}
