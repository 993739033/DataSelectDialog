package com.bbny.qifengwlw.dataselectdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;
import com.bbny.qifengwlw.dataselectdialog.Base.SimpleItemBean;
import com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils.CustomPopWindow;
import com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils.PopupUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    CustomPopWindow window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DataSelectDialog.Builder(this).setItems(getLists()).build().show();
        window = PopupUtils.showDataSelectView(MainActivity.this, ViewGroup.LayoutParams.MATCH_PARENT,
                800, getLists(), new PopupUtils.CallBack() {
                    @Override
                    public void callBack(List<BaseItemBean> selectBeans) {
                        //第一次初始化也会回调
//                        if (window != null) {
//                            window.dissmiss();
//                        }
                        Log.d("tag", "callBack: " + selectBeans.size());
                        Log.d("tag", "callBack: " + selectBeans.size());
                    }
                });
        findViewById(R.id.tv_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.showAsDropDown(v);
            }
        });

    }

    public List<BaseItemBean> getLists() {
        List<BaseItemBean> beans = new ArrayList<>();
        beans.add(new SimpleItemBean<String>().setData("田家寨").setItemChecked(true));
        beans.add(new SimpleItemBean<String>().setData("李家村"));
        beans.add(new SimpleItemBean<String>().setData("庆东市"));
        beans.add(new SimpleItemBean<String>().setData("河西村"));
        beans.add(new SimpleItemBean<String>().setData("罗阳镇"));
        beans.add(new SimpleItemBean<String>().setData("卡座区"));
        return initLists(beans);
    }

    public List<BaseItemBean> initLists(List<BaseItemBean> beans) {
        List<BaseItemBean> bean2 = new ArrayList<>();
        bean2.add(new SimpleItemBean<String>().setData("田家寨_大门"));
        bean2.add(new SimpleItemBean<String>().setData("田家寨_内院"));
        bean2.add(new SimpleItemBean<String>().setData("田家寨_后门").setItemChecked(true));
        bean2.add(new SimpleItemBean<String>().setData("田家寨_大门1"));
        bean2.add(new SimpleItemBean<String>().setData("田家寨_内院1"));
        bean2.add(new SimpleItemBean<String>().setData("田家寨_后门1"));
        beans.get(0).setItems(bean2);
        List<BaseItemBean> bean3 = new ArrayList<>();
        bean3.add(new SimpleItemBean<String>().setData("李家村_大门"));
        bean3.add(new SimpleItemBean<String>().setData("李家村_内院"));
        bean3.add(new SimpleItemBean<String>().setData("李家村_后门"));
        bean3.add(new SimpleItemBean<String>().setData("李家村_大门2"));
        bean3.add(new SimpleItemBean<String>().setData("李家村_内院2").setItemChecked(true));
        bean3.add(new SimpleItemBean<String>().setData("李家村_后门2"));
        beans.get(1).setItems(bean3);
        beans.get(2).setItems(bean3);
        beans.get(3).setItems(bean3);
        beans.get(4).setItems(bean3);
        beans.get(5).setItems(bean3);
        return beans;
    }
}
