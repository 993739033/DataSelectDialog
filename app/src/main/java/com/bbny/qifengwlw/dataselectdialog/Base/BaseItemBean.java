package com.bbny.qifengwlw.dataselectdialog.Base;

import java.util.List;

/**
 * Created by ZWX on 2018/12/17.
 */
//item数据基类 items为空则为最底层
public abstract class BaseItemBean {
    String itemID;
    String itemName;
    boolean itemChecked = false;
    List<BaseItemBean> items;

    //获取列表显示名称
    public abstract String getItemName();
    //获取列表项对应的id
    public abstract String getItemID();

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isItemChecked() {
        return itemChecked;
    }

    public BaseItemBean setItemChecked(boolean itemChecked) {
        this.itemChecked = itemChecked;
        return this;
    }

    public List<BaseItemBean> getItems() {
        return items;
    }

    public void setItems(List<BaseItemBean> items) {
        this.items = items;
    }
}
