package com.dfst.core.listener;

import android.view.View;

/**
 * @author yanfei@supcon.com
 * view单击事件类
 * 注解 @AZListener 中参数 type 值之一。
 * @date 2016/3/15.
 */
public class OnClick extends OnListener implements View.OnClickListener {
    /**
     * 调用父类invoke(Object... args)方法
     * @param v 目标view
     */
    @Override
    public void onClick(View v) {
        invoke(v);
    }

    /**
     * 绑定点击事件
     *
     * @param view 目标View
     */
    @Override
    public void listener(View view) {
        view.setOnClickListener(this);
    }
}