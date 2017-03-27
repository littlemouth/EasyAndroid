package com.dfst.core.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * @author yanfei@supcon.com
 * AdapterView 子项单击事件类
 * @date 2016/3/15.
 */
public class OnItemClick extends OnListener implements AdapterView.OnItemClickListener {
    /**
     * 调用父类invoke(Object... args)方法
     * @param parent 父AdapterVIew
     * @param view 单击的子view
     * @param position view 在 parent中的位置
     * @param id view 的行数
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        invoke(parent, view, position, id);
    }

    @Override
    public void listener(View view) {
        ((AdapterView) view).setOnItemClickListener(this);
    }
}