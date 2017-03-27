package com.dfst.core.listener;

import android.view.View;

/**
 * @author yanfei@supcon.com
 * view 长点击事件类
 * 注解 @AZListener 中参数 type 值之一。
 * @date 2016/3/15.
 */
public class OnLongClick extends OnListener implements View.OnLongClickListener {
    @Override
    public void listener(View view) {
        view.setOnLongClickListener(this);
    }

    /**
     * 调用父类invoke(Object... args)方法，缺省消耗事件。
     * @param v 目标view
     * @return 是否消耗事件
     */
    @Override
    public boolean onLongClick(View v) {
        Object obj = invoke(v);
        if (obj instanceof Boolean) {
            return (boolean) invoke(v);
        }
        return true;
    }
}