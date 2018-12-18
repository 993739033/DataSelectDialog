package com.bbny.qifengwlw.dataselectdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWX on 2018/12/17.
 */

public class DataSelectDialog extends Dialog {
    private List<BaseItemBean> selectedItems = new ArrayList<>();//已选中的列表集合 保留了顺序
    private List<BaseItemBean> items;//列表集合
    private RecyclerView rv_content;
    public static final String Tag = "tag";

    public List<BaseItemBean> getItems() {
        return items;
    }

    public void setItems(List<BaseItemBean> items) {
        this.items = items;
        if (rv_content != null) {
            ((DataSelectAdapter) rv_content.getAdapter()).setItemBeans(items);
        }
    }

    public DataSelectDialog(@NonNull Context context) {
        super(context);
    }

    public DataSelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DataSelectDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv_content = this.findViewById(R.id.rv_content);
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(new DataSelectAdapter( getContext(),items));
    }



    public static class Builder {
        private View layout;
        private DataSelectDialog dialog;
        private List<BaseItemBean> items;

        public Builder(Context context) {
            dialog = new DataSelectDialog(context, R.style.MyDialogStyle);
            dialog.setItems(items);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.layout_data_select_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public Builder setItems(List<BaseItemBean> items) {
            this.items = items;
            return this;
        }

        public DataSelectDialog build() {
            if (this.items == null || dialog == null) {
                return null;
            }
            dialog.setItems(items);
            return dialog;
        }
    }
}
