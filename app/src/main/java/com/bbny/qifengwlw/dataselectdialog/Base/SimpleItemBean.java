package com.bbny.qifengwlw.dataselectdialog.Base;

/**
 * Created by ZWX on 2018/12/17.
 */
//T泛型只用于基本类型 不包含自定义类 需使用请将自定义类继承BaseItemBean 实现方法
public class SimpleItemBean<T>  extends BaseItemBean{
    private T data;

    public T getData() {
        return data;
    }

    public SimpleItemBean<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String getItemName() {
        return (String) data;//名称
    }

    @Override
    public String getItemID() {
        return "";//基本类型暂不考虑id
    }

    @Override
    public void setItemName(String itemName) {
        data = (T) itemName;
    }
}
