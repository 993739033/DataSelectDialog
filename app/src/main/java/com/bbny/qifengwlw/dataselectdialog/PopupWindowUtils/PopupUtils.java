package com.bbny.qifengwlw.dataselectdialog.PopupWindowUtils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bbny.qifengwlw.dataselectdialog.Base.BaseItemBean;
import com.bbny.qifengwlw.dataselectdialog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWX on 2018/12/19.
 */
//弹窗工具
public class PopupUtils {

    public static CustomPopWindow showDataSelectView(final Activity activity, View targetView, int width,
                                                     int height, List<BaseItemBean> beans, final CallBack callBack) {
        View view = activity.getLayoutInflater().inflate(R.layout.view_condition_select, null);
        RecyclerView rv_condition_1 = view.findViewById(R.id.rv_condition_1);
        final RecyclerView rv_condition_2 = view.findViewById(R.id.rv_condition_2);
        initRcy2(activity, rv_condition_2, beans, callBack);
        DividerItemDecoration divider = new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(activity.getResources().getDrawable(R.drawable.drawable_line));
        rv_condition_1.setAdapter(new Adapter1(activity, beans));
        rv_condition_1.setLayoutManager(new LinearLayoutManager(activity));
        rv_condition_1.addItemDecoration(divider);
        ((Adapter1) rv_condition_1.getAdapter()).setCallBack(new Adapter1.CallBack() {
            @Override
            public void callBack(List<BaseItemBean> list) {
                initRcy2(activity, rv_condition_2, list, callBack);
            }
        });
        //创建并显示popWindow
        CustomPopWindow popupWindow = new CustomPopWindow.PopupWindowBuilder(activity)
                .setView(view)
                .size(width, height)//显示大小
                .enableBackgroundDark(false)
                .create();
        return popupWindow;
    }

    private static void initRcy2(Context context, RecyclerView rcy2, List<BaseItemBean> list, final CallBack callBack) {
        BaseItemBean bean = getCheckedBean(list);
        if (bean==null) {
            rcy2.setAdapter(null);
            if (callBack != null) {
                callBack.callBack(new ArrayList<BaseItemBean>());
            }
            return;
        }
        getCheckedBean(bean.getItems());
        if (rcy2 != null && rcy2.getAdapter() != null) {
            ((Adapter2) rcy2.getAdapter()).setBean(bean);
        } else {
            rcy2.setAdapter(new Adapter2(context, bean));
            rcy2.setLayoutManager(new LinearLayoutManager(context));
            ((Adapter2) rcy2.getAdapter()).setCallBack(new Adapter2.CallBack() {
                @Override
                public void callBack(List<BaseItemBean> beans) {
                    if (callBack != null) {
                        callBack.callBack(beans);
                    }
                }
            });
        }
        if (callBack != null) {
            callBack.callBack(((Adapter2) rcy2.getAdapter()).getBeans());
        }
    }

    //获取选中的bean 按顺序排序 是否再一开始时初始化时当没有选中项选择第一个
    public static List<BaseItemBean> getSelectBeans(List<BaseItemBean> beans,Boolean shouldInit) {
        List<BaseItemBean> lists = new ArrayList<>();
        if (beans == null || beans.size() == 0) {
            Log.d("tag", "设置的数据不能为空");
            return lists;
        }
        BaseItemBean bean = getCheckedBean(beans,shouldInit);
        while (bean != null) {
            beans = bean.getItems();
//            bean.setItems(null);
            lists.add(bean);
            if (beans == null || beans.size() == 0) {
                break;
            }
            bean = getCheckedBean(beans,shouldInit);
        }
        return lists;
    }

    private static BaseItemBean getCheckedBean(List<BaseItemBean> beans) {
      return  getCheckedBean(beans, false);
    }

    //获取选择的item bean
    private static BaseItemBean getCheckedBean(List<BaseItemBean> beans,Boolean shouldInit) {
        for (BaseItemBean b : beans) {
            if (b.isItemChecked()) {
                return b;
            }
        }
        BaseItemBean bean = null;
        //没有设置默认选项则默认选中第一个
        if (beans.size() > 0&&shouldInit) {
            bean = beans.get(0).setItemChecked(true);
        }
        return bean;
    }

    //选择回调 为选中baseItemBean
    public interface CallBack {
        void callBack(List<BaseItemBean> selectBeans);
    }
}
