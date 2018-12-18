package com.bbny.qifengwlw.dataselectdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;
import com.bbny.qifengwlw.dataselectdialog.Base.SimpleItemBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DataSelectDialog.Builder(this).setItems(getLists()).build().show();
    }

    public List<BaseItemBean> getLists() {
        List<BaseItemBean> beans = new ArrayList<>();
        beans.add(new SimpleItemBean<String>().setData("田家寨"));
        beans.add(new SimpleItemBean<String>().setData("李家村"));
        return initLists(beans);
    }

    public List<BaseItemBean> initLists(List<BaseItemBean> beans) {
        List<BaseItemBean> bean2 = new ArrayList<>();
        bean2.add(new SimpleItemBean<String>().setData("田家寨_大门"));
        bean2.add(new SimpleItemBean<String>().setData("田家寨_内院"));
        bean2.add(new SimpleItemBean<String>().setData("田家寨_后门"));
        beans.get(0).setItems(bean2);
        List<BaseItemBean> bean3 = new ArrayList<>();
        bean3.add(new SimpleItemBean<String>().setData("李家村_大门"));
        bean3.add(new SimpleItemBean<String>().setData("李家村_内院"));
        bean3.add(new SimpleItemBean<String>().setData("李家村_后门"));
        beans.get(1).setItems(bean3);
        return beans;
    }
}
